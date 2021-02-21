  var ruleList = [];
  var ruleStatus = {};
  var statusStreamPort = undefined;
  
chrome.storage.sync.get('rules', function(ruleData){
	ruleList = (ruleData.rules)? ruleData.rules :[];
});
  
  
	chrome.runtime.onConnect.addListener(port => {
        console.log('connected ', port);

        if (port.name === 'rule-live-stream') {
            port.onMessage.addListener(function(sRule) {
				
				if(sRule.isDone){
					if(rule = ruleList.find(r => r.id == sRule.id)){
						if(!rule.isDone){
							rule.isDone = true;
							if(childRule = ruleList.find(r => r.parentId == rule.id)){
								childRule.isActive = true;
							}
							
							chrome.storage.sync.set({rules : ruleList}, function(){
								console.log(rule.id + ' - Rule completed');
							});
						}
					}
				}
				
				if(ruleStatus[sRule.id]){
						ruleStatus[sRule.id].currValue = sRule.value;
						ruleStatus[sRule.id].lastUpdated = (new Date()).getTime();
						ruleStatus[sRule.id].pageStatus = sRule.pageStatus;
						ruleStatus[sRule.id].isDone = sRule.isDone;
				}
				else{
					ruleStatus[sRule.id] = {
						currValue: sRule.value,
						lastUpdated: (new Date()).getTime(),
						pageStatus: sRule.pageStatus,
						isDone: sRule.isDone
					};
				}
				
				for(rule of ruleList){
					if(rule.id == sRule.id && sRule.value){
						if(rule.highest < sRule.value || rule.highest == "N/A"){
							rule.highest = sRule.value;
							chrome.storage.sync.set({rules : ruleList}, function(){
								console.log(rule.id + ' - highest updated');
							});
						}
						if(rule.lowest > sRule.value || rule.lowest == "N/A"){
							rule.lowest = sRule.value;
							chrome.storage.sync.set({rules : ruleList}, function(){
								console.log(rule.id + ' - lowest updated');
							});
						}
					}
				}
					
			});
        }
		if(port.name === 'rule-status-stream'){
			statusStreamPort = port;
			statusStreamPort.onDisconnect.addListener(port => {
				if(port.name === 'rule-status-stream'){
					statusStreamPort = undefined;
				}
			});
		}
	});
	

	chrome.storage.onChanged.addListener(function(changes, namespace) {
        for (let key in changes) {
			if(key === 'rules'){
				ruleList = changes[key].newValue;
			}
		}
    });
	
	chrome.runtime.onMessage.addListener(
	    function(msg, sender, sendResponse) {
			if ((msg.from === 'content') && (msg.subject === 'get-tab-id')) {
				sendResponse({tabId: sender.tab.id});
			}
	});
	
	async function updateStatus(){
		for(let i in ruleList){
			let rule = ruleList[i];
			let currentStatus = ruleStatus[rule.id]
			if(currentStatus){
				if(currentStatus.isDone || rule.isDone){
					currentStatus.status = "Rule done";
				}
				else{
					chrome.tabs.get(rule.tabId, function(tabInfo){
						if(!chrome.runtime.lastError || tabInfo){
							if(tabInfo.url == rule.url){
								
								let msSinceUpdate = (new Date()).getTime() - currentStatus.lastUpdated;
								if(msSinceUpdate <= 20000){
									if(currentStatus.currValue){
										currentStatus.status = "Active";
									}
									else{
										currentStatus.status = "Unable to read value";
									}
								}
								else{
									currentStatus.status = "Rule disconnected";
								}
								
							}
							else{
								currentStatus.status = "URL changed";
							}
						}
						else{
							currentStatus.status = "Tab not found";
						}
					});
				}
				
				//Switching tabs if required
				if(currentStatus.status){
					if(currentStatus.status == "Tab not found" ||
							currentStatus.status == "Rule Inactive" ||
							currentStatus.status == "URL changed"){
					
						chrome.tabs.query({}, function(tabs){
							for(tab of tabs){
								if(tab.url == rule.url && tab.status == "complete"){
									rule.tabId = tab.id;
									
									chrome.storage.sync.set({rules : ruleList}, function(){
										console.log("Tab switched for url:" + rule.url + " ."); 
									});
									
								}
							}
						});
					}
				}
			}
			else{
				ruleStatus[rule.id] = {
					status: "Rule Inactive"
				};
			}
			
		}
		
		if(statusStreamPort){
			statusStreamPort.postMessage(ruleStatus);
		}
	}
	
	function applyChainedBets(betList){
		
	}
	
	
	
	//async loop - status updates
	let run = async ()=>{
		await delay(5000);
		while(true){
			console.log(JSON.stringify(ruleList));
			await updateStatus();
			await delay(100);
		}
	}
	run();

	async function delay(ms) {
	  return await new Promise(resolve => setTimeout(resolve, ms));
	}