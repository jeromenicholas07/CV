	document.addEventListener('DOMContentLoaded', function(){
		chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
			chrome.tabs.sendMessage(tabs[0].id, {from: "popup", subject: "market-list"}, function(response) {
				if(chrome.runtime.lastError || !response){
					return;
				}
				
				if(response.matchName){
					document.querySelector("#match-name").value = response.matchName;
				}
				if(response.hasBallDelay){
					document.querySelector("#delay-dropdown").value = "2";
					document.querySelector("#ball-option-1").classList.remove('unavailable-option');
					document.querySelector("#ball-option-1").disabled = false;
					document.querySelector("#ball-option-2").classList.remove('unavailable-option');
					document.querySelector("#ball-option-2").disabled = false;
				}
				document.querySelector("#add-rule-card").style.display = "block";
				document.querySelector("#invalid-page-alert").style.display = "none";
				var marketDropdown = document.getElementById('market-dropdown');
				for(i = 0; i < response.message.length; i++){
					let marketName = response.message[i].name;
					let marketTitle = response.message[i].title;
					
					let opt = document.createElement("option");
					opt.textContent = marketName + (marketTitle ? " ("+marketTitle+")":"" );
					opt.value = marketName + ":::" + marketTitle;
					marketDropdown.appendChild(opt);
				}
			});
			
			chrome.tabs.sendMessage(tabs[0].id, {from: "popup", subject: "available-parents"}, function(response) {
				
				if(chrome.runtime.lastError || !response){
					return;
				}
				
				var parentDropdown = document.getElementById('chained-parent-dropdown');
				for(i = 0; i < response.length; i++){
					
					let marketName = response[i].marketName;
					let marketTitle = response[i].marketTitle;
					let ruleId = response[i].id;
					
					let optn = document.createElement("option");
					optn.textContent = marketName + (marketTitle ? " ("+marketTitle+")":"" ) + " ["+ruleId+"]";
					optn.value = ruleId;
					parentDropdown.appendChild(optn);
				}
			});
			
		});
	});
	
	var addRuleBtn = document.getElementById('add-rule-btn');
	addRuleBtn.addEventListener('click', function(eve){
		eve.preventDefault();
		var ruleForm = document.getElementById("rule-form");
		if (ruleForm.checkValidity()) {
			var newRule = {};
			data = $(ruleForm).serializeArray();
			
			for(i = 0; i < data.length; i++){
				switch(data[i].name){
					case "market-dropdown":
						newRule.marketName = data[i].value.split(':::')[0];
						newRule.marketTitle = data[i].value.split(':::')[1];
					break;
					case "back-or-lay-dropdown":
						newRule.side = data[i].value;
					break;
					case "delay-dropdown":
						newRule.delay = data[i].value;
					break;
					case "condition-dropdown":
						newRule.condition = data[i].value;
					break;
					case "threshold":
						newRule.threshold = data[i].value;
					break;
					case "amount":
						newRule.amount = data[i].value;
					break;
					case "match-name":
						newRule.matchName = data[i].value;
					break;
					case "chained-parent-dropdown":
						newRule.parentId = data[i].value;
					break;
					
				}
			}
			
			chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
				chrome.storage.sync.get('rules', function(rulesData){
						var rules = (rulesData.rules)? rulesData.rules :[];
						
						newRule.tabId = tabs[0].id;
						if(tabs[0].url === ""){
							alert("Invalid page status");
						}
						newRule.url = tabs[0].url;
						newRule.id = (new Date()).getTime();
						
						newRule.highest = "N/A";
						newRule.lowest = "N/A";
						
						if(newRule.parentId == "-1"){
							newRule.isActive = true;
						}
						else{
							newRule.isActive = false;
						}
						
						alert(JSON.stringify(newRule));
						
						rules.push(newRule);
						chrome.storage.sync.set({rules : rules}, async function(){
							document.querySelector("#add-rule-card").style.display = "none";
							document.querySelector("#rule-added-alert").style.display = "block";
							await delay(2000);
							location.reload();
						});
				});
				
			});
		}
		else{
			ruleForm.reportValidity();
		}
	});
	
	var recorderSwitch = document.getElementById('record-switch');
	recorderSwitch.addEventListener('click', function(eve){
		if(recorderSwitch.checked){
			document.querySelectorAll('.no-record').forEach(function(ele){
				ele.disabled = true;
				ele.style.display = "none";
			});
		}
		else{
			document.querySelectorAll('.no-record').forEach(function(ele){
				ele.disabled = false;
				ele.style.display = "block";
			});
		}
	});
	
	var conditionDropdown = document.getElementById('condition-dropdown');
	var delayDropdown = document.getElementById('delay-dropdown');
	var thresholdInput = document.getElementById('threshold');
	conditionDropdown.addEventListener('change', function(eve){
		if(conditionDropdown.value == "market-price"){
			delayDropdown.disabled = true;
			thresholdInput.value = "";
			thresholdInput.disabled = true;
		}
		else{
			delayDropdown.disabled = false;
			thresholdInput.disabled = false;
		}
	});
	
	
	var amountInput = document.getElementById('amount');
	var profitDiv = document.getElementById('profit');
	amountInput.addEventListener('input', updateProfit);
	thresholdInput.addEventListener('input', updateProfit);
	
	function updateProfit(eve){
		let thresh = parseFloat(thresholdInput.value);
		let amount = parseInt(amountInput.value);
		let netProfit = (thresh - 1) * amount;
		
		profitDiv.textContent = 'Profit : ' + parseInt(netProfit);
	}
	async function delay(ms) {
	  return await new Promise(resolve => setTimeout(resolve, ms));
	}