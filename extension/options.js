  var siteContainer = document.getElementById('site-container');
  var addSiteBtn = document.getElementById('add-site-button');
  var ruleListContainer = document.getElementById('rule-list');
  
  
	document.addEventListener('click', function(event){
		if(event.target.matches('span.delete-rule')){
			var row = $(event.target).parents('tr');
			var ruleId = row.children('.rule-id').text();
			
			if(confirm('Do you really want to delete this rule?')){
				chrome.storage.sync.get('rules' , function(data){
					let rList = data.rules;
					rList = rList.filter(function(ele){return ele.id != ruleId});
					chrome.storage.sync.set({rules : rList}, function(){
						location.reload();
					});
				});
			}
			
		}
	});
	
	chrome.storage.sync.get('rules', function(rulesData){
		let rules = (rulesData.rules)? rulesData.rules :[];
		
		for(var ind in rules){
			let rule = rules[ind];
			let row = document.createElement("tr");
			row.setAttribute('id', rule.id);
			
			var idDiv = document.createElement('td');
			idDiv.setAttribute('class', 'rule-id');
			idDiv.setAttribute('style', 'font-size: 12px;');
				idDiv.appendChild(document.createTextNode(rule.id));
			row.appendChild(idDiv);
			
			var th = document.createElement("th");
				let aa = document.createElement("a");
					aa.setAttribute('href', rule.url);
					aa.setAttribute('target', '_blank');
					aa.appendChild(document.createTextNode(rule.matchName));
				th.appendChild(aa);
			row.appendChild(th);
			
			var th = document.createElement("th");
				th.appendChild(document.createTextNode(rule.marketName));
			row.appendChild(th);
			
			td = document.createElement('td');
				td.appendChild(document.createTextNode(rule.marketTitle));
			row.appendChild(td);
			
			td = document.createElement('td');
				if(rule.amount){
					td.appendChild(document.createTextNode(rule.amount));
				}
			row.appendChild(td);
			
			td = document.createElement('td');
				td.appendChild(document.createTextNode(rule.side));
			row.appendChild(td);
			
			td = document.createElement('td');
				if(!rule.delay){
					td.appendChild(document.createTextNode(""));
				}
				else if( rule.delay == 1 || rule.delay == 2 ){
					td.appendChild(document.createTextNode(rule.delay + " Ov."));
				}
				else{
					td.appendChild(document.createTextNode(rule.delay + " sec."));
				}
				
			row.appendChild(td);
			
			td = document.createElement('td');
				if(!rule.condition){
					td.appendChild(document.createTextNode("Recording"));
				}
				else{
					td.appendChild(document.createTextNode(
						rule.condition + " than " + rule.threshold
					));
				}
			row.appendChild(td);
			
			td = document.createElement('td');
			td.className = rule.side + " live-value";
				td.setAttribute('data-toggle', 'tooltip');
				td.setAttribute('data-placement', 'right');
				td.setAttribute('data-html', 'true');
				td.setAttribute('data-original-title', 'Lowest: '+ rule.lowest +
													'<br> Highest: ' + rule.highest);
				
				td.appendChild(document.createTextNode(""));
			row.appendChild(td);
			
			td = document.createElement('td');
			td.className = "last-updated";
			td.dataset.utime = 0;
				td.appendChild(document.createTextNode(""));
			row.appendChild(td);
			
			td = document.createElement('td');
			td.className = "status";
				td.appendChild(document.createTextNode(""));
			row.appendChild(td);
			
			td = document.createElement('td');
			td.className = "page-status";
				td.appendChild(document.createTextNode(""));
			row.appendChild(td);
			
			td = document.createElement('td');
				var delBtn = document.createElement('span');
				delBtn.setAttribute("class","delete-rule");
				delBtn.setAttribute("type","button");
					delBtn.appendChild(document.createTextNode(' \u2716'));
				td.appendChild(delBtn);
			row.appendChild(td);
			
			ruleListContainer.appendChild(row);
		}
	});

	chrome.storage.onChanged.addListener(function(changes, namespace) {
        for (var key in changes) {
			if(key === "rules"){
				location.reload();
			}
        }
      });





	var port = chrome.runtime.connect({name: "rule-status-stream"});
	port.onMessage.addListener(function(ruleStatus) {
		console.log('listening');
		for(ruleId in ruleStatus){
			ruleInfo = ruleStatus[ruleId];
			if(row = ruleListContainer.querySelector('tr[id="'+ ruleId + '"]')){
				let valueTD = row.querySelector('.live-value');
				let lastUpdatedTD = row.querySelector('.last-updated');
				let statusTD = row.querySelector('.status');
				let pageStatusTD = row.querySelector('.page-status');
				
				valueTD.textContent = ruleInfo.currValue;
				lastUpdatedTD.dataset.utime = ruleInfo.lastUpdated ? ruleInfo.lastUpdated:0;
				statusTD.textContent = ruleInfo.status;
				pageStatusTD.textContent = ruleInfo.pageStatus;
			}
		}
	});
	
	function timeSince(date) {
		if(date == 0){
			return "--";
		}

	  var seconds = Math.floor((new Date() - date) / 1000);

	  var interval = Math.floor(seconds / 31536000);

	  if (interval > 1) {
		return interval + " years";
	  }
	  interval = Math.floor(seconds / 2592000);
	  if (interval > 1) {
		return interval + " months";
	  }
	  interval = Math.floor(seconds / 86400);
	  if (interval > 1) {
		return interval + " days";
	  }
	  interval = Math.floor(seconds / 3600);
	  if (interval > 1) {
		return interval + " hours";
	  }
	  interval = Math.floor(seconds / 60);
	  if (interval > 1) {
		return interval + " minutes";
	  }
	  return Math.floor(seconds) + " seconds";
	}
	
	let run = async ()=>{
		while(true){
			let lutTDs = document.getElementsByClassName('last-updated');
			for(let ele of lutTDs){
				ele.textContent = timeSince(ele.dataset.utime);
			}
			$('[data-toggle="tooltip"]').tooltip()
			await delay(100);
		}
	}
	run();

	async function delay(ms) {
	  return await new Promise(resolve => setTimeout(resolve, ms));
	}
	