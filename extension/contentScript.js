var port = chrome.runtime.connect({name: "rule-live-stream"});
var ruleList = [];
var ruleStatus = {};
var ruleRefs = {};
var tabId = "-1";
var ongoingBet = false;
var lastErrorReport = 0;
console.log("cs connected");

chrome.runtime.sendMessage({from:"content", subject: "get-tab-id"}, function(response) {
	tabId = response.tabId;
	chrome.storage.sync.get('rules', function(rulesData){
		let rules = (rulesData.rules)? rulesData.rules :[];
		ruleList = rules.filter(function(ele){ return ele.tabId ==  tabId;});
	});
});

chrome.storage.onChanged.addListener(function(changes, namespace) {
	for (let key in changes) {
		if(key === 'rules'){
			let rules = changes[key].newValue;
			rules = rules.filter(function(ele){ return ((ele.tabId ==  tabId) && (!ele.isDone)); });
			if(JSON.stringify(ruleList) != JSON.stringify(rules)){
				ruleList = rules;
			}
		}
	}
});

async function observeRules(){
	try{
		//check and click on renew session here
		if(renewButt = document.querySelector('.session-timeout-dialog button')){
			renewButt.click();
		}
		let eventList = document.querySelectorAll('div.event-name');
		if(eventList.length == 0){
			//alert('Invalid page - xxx');
			return;
		}
		let marketList = [];
		for(let i = 0; i < eventList.length; i++){
			let marketTitle = "";
			if(eventList[i].closest('table').querySelector('.market-name.title>span')){
				marketTitle = eventList[i].closest('table').querySelector('.market-name.title>span').textContent.trim();
			}
			marketList.push({
				element: eventList[i],
				name: eventList[i].textContent.trim(),
				title: marketTitle
				});
		}
		for(let i in ruleList){
			let rule = ruleList[i];
			let oddValue = undefined;
			let ruleDone = false;
			let pageStatus = "N/A";
				
			if(rule.isActive){
				if(rule.isDone){
					ruleDone = true;
				}
				if(ruleStatus[rule.id]){
					if( (rule.delay == 1 || rule.delay == 2) && (ruleStatus[rule.id].startOver != -5) ){
						if(scorecard = document.querySelector('.match-centre-body')){
							let overs = scorecard.querySelectorAll('.match-centre-body .overs .over');
							for(over of overs){
								if(overNoDiv = over.querySelector('div.over-detail>div.ng-binding')){
									if(overNo = parseInt(overNoDiv.textContent.trim().match(/Ov(.*)\:/m)[1])){
										if(overNo > ruleStatus[rule.id].startOver){
											let balls = [].slice.call(over.querySelectorAll('.match-centre-body .overs .over:first-of-type .balls .ball'), 0)
																	.map( m => m.textContent.trim());
											
											if(balls.includes("W")){
												ruleStatus[rule.id].startOver = overNo;
											}
										}
									}
								}
							}
						}
						else{
							if(scoreCollapseButt = document.querySelector('.match-stats-body-button')){
								scoreCollapseButt.click()
							}
						}
					}
					if(ruleStatus[rule.id].done){
						ruleDone = true;
					}
				}
				if(renewButt = document.querySelector('.session-timeout-dialog button')){
					pageStatus = "Need to Renew Session manually";
				}
				else{
					let marketFound = false;
					for(let j in marketList){
						let market = marketList[j];
						
						if( (rule.marketName == market.name)
							&& (rule.marketTitle == market.title) ){
								marketFound = true;
								if(row = market.element.closest('tr')){
									if(valueTD = row.querySelector('td.'+ rule.side +':not(.unhighlighted)')){
										if(valueEle = valueTD.querySelector('strong.odds')){
											oddValue = parseFloat(valueEle.textContent.trim());
											
											if(!ruleDone){
												if(!ruleStatus[rule.id]){
													if(rule.condition == "market-price"){
														ruleStatus[rule.id] = {
															betNow: true
														};
													}
													else if( (rule.condition == "greater" && oddValue >= rule.threshold)
															|| (rule.condition == "lesser" && oddValue <= rule.threshold) )
													{
														if(rule.delay == 1 || rule.delay == 2){
															
															if(scorecard = document.querySelector('.match-centre-body')){
																let overs = [].slice.call(scorecard.querySelectorAll('.match-centre-body .overs .over'), 0).reverse();
																if(overs.length > 0){
																	let checkedForWickets = false;
																	for(over of overs){
																		if(overNoDiv = over.querySelector('div.over-detail>div.ng-binding')){
																			if(overNo = parseInt(overNoDiv.textContent.trim().match(/Ov(.*)\:/m)[1])){
																				let balls = [].slice.call(over.querySelectorAll('.match-centre-body .overs .over:first-of-type .balls .ball'), 0)
																										.map( m => m.textContent.trim());
																				
																				if(balls.includes("W")){
																					ruleStatus[rule.id] = {
																						startOver: overNo,
																						betNow: false
																					};
																				}
																				checkedForWickets = true;
																			}
																			else{
																				pageStatus = "Unable to parse overNo";
																			}
																		}
																		else{
																			pageStatus = "Unable to find overNoDiv";
																		}
																	}
																	if(checkedForWickets && !ruleStatus[rule.id]){
																		ruleStatus[rule.id] = {
																			startOver: -5,
																			betNow: true
																		};
																	}
																}
																else{
																	pageStatus = "Unable to get ball info";
																}
																
																
																
																
																/*
																// TO only see last 2 balls
																let balls = [].slice.call(document.querySelectorAll('.match-centre-body .overs .over:first-of-type .balls .ball'), 0).reverse().concat([].slice.call(document.querySelectorAll('.match-centre-body .overs .over:nth-of-type(2) .balls .ball')).reverse());
																if(balls.length > 0){
																	if(balls[0].textContent.trim() == "W" ||
																		balls[1].textContent.trim() == "W"){
																			if(overDiv = document.querySelector('div.header .toss h2')){
																				let startOv = parseInt(overDiv.textContent.trim().match(/\((.*) (.*)\)/m)[1]);
																				ruleStatus[rule.id] = {
																					startOver: startOv,
																					betNow: false
																				};
																			}
																	}
																	else{
																		ruleStatus[rule.id] = {
																			startOver: -5,
																			betNow: true
																		};
																		
																	}
																}
																else{
																	pageStatus = "Unable to ball info";
																}*/
															}
															else{
																if(scoreCollapseButt = document.querySelector('.match-stats-body-button')){
																	scoreCollapseButt.click();
																}
																pageStatus = "Unable to find scorecard";
															}
														}
														else{
															ruleStatus[rule.id] = {
																startTime: (new Date()).getTime(),
																betNow: false
															};
														}
													}
													else{
														pageStatus = "waiting";
													}
												}
												else{
													let delaySatisfied = false;
													if(ruleStatus[rule.id].betNow){
														delaySatisfied = true;
													}
													else if(rule.delay == 1 || rule.delay == 2){
														if(overDiv = document.querySelector('div.header .toss h2')){
															let currentOv = parseInt(overDiv.textContent.trim().match(/\((.*) (.*)\)/m)[1]);
															
															if(currentOv < ruleStatus[rule.id].startOver-1){
																console.log("curr over:" + currentOv );
																console.log("start ovr:" + ruleStatus[rule.id].startOver);
																ruleStatus[rule.id].done = true;
															}
															else if( currentOv >= (ruleStatus[rule.id].startOver + parseInt(rule.delay)) ){
																delaySatisfied = true;
															}
															else{
																pageStatus = "Betting in " + (ruleStatus[rule.id].startOver + parseInt(rule.delay) - currentOv) +" Ov";
															}
														}
														else{
															pageStatus = "Unable to read Over info.";
														}
													}
													else {
														if( (ruleStatus[rule.id].startTime + (parseInt(rule.delay)*1000)) <= (new Date()).getTime() ){
															delaySatisfied = true;
														}
														else{
															pageStatus = "Betting in " + ((ruleStatus[rule.id].startTime + (parseInt(rule.delay)*1000) - (new Date()).getTime())/1000) +" second(s)";
														}
													}
														
													if(delaySatisfied){
														let conditionSatisfied = false;
														
														if( rule.delay == 1 || rule.delay == 2 ){
															if((rule.condition == "greater" && oddValue >= (parseFloat(rule.threshold) * 0.9))
																|| (rule.condition == "lesser" && oddValue <= (parseFloat(rule.threshold) * 1.1))){
																conditionSatisfied = true;
															}
														}
														else{
															if((rule.condition == "greater" && oddValue >= rule.threshold)
																|| (rule.condition == "lesser" && oddValue <= rule.threshold)){
																conditionSatisfied = true
															}
														}
														if(rule.condition == "market-price"){
															conditionSatisfied = true;
														}
														
														if(conditionSatisfied)
														{
															if(sizeEle = valueTD.querySelector('div.size')){
																let sizeInt = parseInt(sizeEle.textContent.trim());
																
																if(rule.marketTitle !== "" || (sizeInt >= 90 && sizeInt <= 110) ){
															
																	ruleStatus[rule.id].betNow = true;
																	if(!valueTD.classList.contains('betting-disabled')){
																		let itr = 1;
																		do{
																			if(document.querySelectorAll('div.bet').length == 1){
																				ongoingBet = true;
																			}
																			if(ongoingBet){
																				await delay(1000);
																				if(document.querySelectorAll('div.bet').length == 0){
																					ongoingBet = false;
																				}
																				continue;
																			}
																			ongoingBet = true;
																			valueEle.click();
																			let delayIt = 1;
																			do{
																				await delay(100);
																				if(document.querySelectorAll('div.bet').length == 1){
																					break;
																				}
																			}while(delayIt++ < 15);
																			
																			if(document.querySelectorAll('div.bet').length == 1){
																				if(betCard = document.querySelector('div.bet')){
																					if(amountInput = betCard.querySelector('input.-qa-bet-stake-input')){
																						let changeEvent = new Event('change');
																						amountInput.value = rule.amount;
																						amountInput.dispatchEvent(changeEvent);
																						await delay(1000);
																						if(placeBetsButt = document.querySelector('.summary-buttons > .apl-btn-primary')){
																							if(!placeBetsButt.disabled){
																								if(document.querySelectorAll('div.bet').length == 1){
																									console.log("xxxxxx ---- Placing bet");
																									pageStatus = "Bet placed";
																									//Place bet here :
																									placeBetsButt.click();
																									ruleStatus[rule.id].done = true;
																								}
																								else{
																									pageStatus = "Other bets interfering in last operation";
																									console.log(pageStatus);
																									if(rmButt = document.querySelector('.summary-buttons > .apl-btn-remove:not(.apl-btn-mini)')){
																										rmButt.click();
																									}
																								}
																							}
																							else{
																								pageStatus = "Place bets button disabled";
																								console.log(pageStatus);
																								if(rmButt = document.querySelector('.summary-buttons > .apl-btn-remove:not(.apl-btn-mini)')){
																									rmButt.click();
																								}
																							}
																						}
																						else{
																							pageStatus = "Place bets button not found";
																							console.log(pageStatus);
																							if(rmButt = document.querySelector('.summary-buttons > .apl-btn-remove:not(.apl-btn-mini)')){
																								rmButt.click();
																							}
																						}
																					}
																					else{
																						pageStatus = "Unable to find Amount input field";
																						console.log(pageStatus);
																						if(rmButt = document.querySelector('.summary-buttons > .apl-btn-remove:not(.apl-btn-mini)')){
																							rmButt.click();
																						}
																					}
																				}
																				else{
																					pageStatus = "Unable to find bet card";
																					console.log(pageStatus);
																					if(rmButt = document.querySelector('.summary-buttons > .apl-btn-remove:not(.apl-btn-mini)')){
																						rmButt.click();
																					}
																				}
																				break;
																			}
																			else if(rmButt = document.querySelector('.summary-buttons > .apl-btn-remove:not(.apl-btn-mini)')){
																				rmButt.click();
																				pageStatus = "Other bets interfering";
																				console.log(pageStatus);
																			}
																			await delay(1000);
																		}while(itr++ < 10);
																		ongoingBet = false;
																	}
																	else{
																		pageStatus = "Bet disabled";
																	}
																}
																else{
																	pageStatus = "Bet size not inbetween 90 & 110";
																}
															}
															else{
																pageStatus = "Bet size element not found";
															}
														}
														else{
															delete ruleStatus[rule.id];
															pageStatus = "Condition not satisfied";
														}
													}
												}
											}
											else{
												pageStatus = "Rule done";
											}
										}
										else{
											pageStatus = "Bet disabled";
										}
									}
									else{
										pageStatus = "Bet disabled";
									}
								}
								else{
									pageStatus = "Error finding market row";
								}
							}
					}
					if(!marketFound){
						pageStatus = "Market not found";
					}
				}
				
			}
			else{
				pageStatus = "Inactive, Waiting for parent rule";
			}
			port.postMessage({
				id: rule.id,
				value: oddValue,
				pageStatus: pageStatus,
				isDone: ruleDone
			});
		}
	}
	catch(e){
		if( ((new Date()).getTime() - lastErrorReport) > 20000){
			lastErrorReport = (new Date()).getTime();
			console.log(e);
		}
	}
}

// Listen for messages from the popup.
chrome.runtime.onMessage.addListener((msg, sender, response) => {
	if ((msg.from === 'popup') && (msg.subject === 'market-list')) {
		let eventList = document.querySelectorAll('div.event-name');
		
		if(eventList.length == 0){
			response(undefined);
			return;
		}
		
		let matchName = "N/A";
		if(matchNameElement = document.querySelector('div.header h1')){
			matchName = matchNameElement.textContent.trim();
		}
		
		let hasBallDelay = false;
		if(ballDelayInfo = document.querySelector('.match-stats-body-button')){
			hasBallDelay = true;
		}
		let marketList = [];
		for(let i = 0; i < eventList.length; i++){
			let marketTitle = "";
			if(eventList[i].closest('table').querySelector('.market-name.title>span')){
				marketTitle = eventList[i].closest('table').querySelector('.market-name.title>span').textContent.trim();
			}
			marketList.push({
				name: eventList[i].textContent.trim(),
				title: marketTitle
				});
		}
		marketInfo = {
			from: 'content',
			subject: 'market-list',
			message: marketList,
			matchName: matchName,
			hasBallDelay: hasBallDelay
		}
		
		response(marketInfo);
	}
	
	if ((msg.from === 'popup') && (msg.subject === 'available-parents')) {
		response(ruleList);
	}
});

let run = async ()=>{
	await delay(2000);
	while(true){
		await observeRules();
		await delay(100);
		console.log(JSON.stringify(ruleList));
	}
}
run();

let keepActive = async ()=>{
	await delay(10000);
	while(true){
		if(!ongoingBet && (anyBet = document.querySelector('td.back:not(.unhighlighted)')) ){
			ongoingBet = true;
			anyBet.click();
			await delay(1000);
			if(rmButt = document.querySelector('.summary-buttons > .apl-btn-remove:not(.apl-btn-mini)')){
				rmButt.click();
			}
			else{
				await delay(2000);
				if(rmButt = document.querySelector('.summary-buttons > .apl-btn-remove:not(.apl-btn-mini)')){
					rmButt.click();
				}
				else{
					console.log("Keep page active function failed!!!");
				}
			}
			
			ongoingBet = false;
		}
		
		await delay(60000);
	}
}
//keepActive();

async function delay(ms) {
  return await new Promise(resolve => setTimeout(resolve, ms));
}