var port = chrome.runtime.connect({name: "rule-live-stream"});
var ruleList = [];
var ruleStatus = {};
var ruleRefs = {};
var tabId = "-1";
var ongoingBet = false;
var lastErrorReport = 0;
var betPlacedAudio = new Audio(chrome.runtime.getURL("res/alert.wav"));
console.log("cs connected");

chrome.runtime.sendMessage({from: "content", subject: "get-tab-id"}, function (response) {
    tabId = response.tabId;
    chrome.storage.local.get('rules', function (rulesData) {
        let rules = (rulesData.rules) ? rulesData.rules : [];
        ruleList = rules.filter(function (ele) {
            return ele.tabId == tabId;
        });
    });
});

chrome.storage.onChanged.addListener(function (changes, namespace) {
    for (let key in changes) {
        if (key === 'rules') {
            let rules = changes[key].newValue;
            rules = rules.filter(function (ele) {
                return ((ele.tabId == tabId) && (!ele.isDone));
            });
            if (JSON.stringify(ruleList) != JSON.stringify(rules)) {
                ruleList = rules;
            }
        }
    }
});

async function observeRules() {

    try {
        //check and click on renew session here
        if (renewButt = document.querySelector('.session-timeout-dialog button')) {
            renewButt.click();
        }
        let eventList = document.querySelectorAll('div.event-name');
        if (eventList.length == 0) {
            //alert('Invalid page - xxx');
            return;
        }
        let marketList = [];
        for (let i = 0; i < eventList.length; i++) {
            let marketTitle = "";
            if (eventList[i].closest('table').querySelector('.market-name.title>span')) {
                marketTitle = eventList[i].closest('table').querySelector('.market-name.title>span').textContent.trim();
            }
            marketList.push({
                element: eventList[i],
                name: eventList[i].textContent.trim(),
                title: marketTitle
            });
        }
        for (let i in ruleList) {
            let rule = ruleList[i];
            let oddValue = undefined;
            let ruleDone = false;
            let pageStatus = "N/A";

            console.log(ruleStatus);

            if (rule.isActive) {
                if (rule.isDone) {
                    ruleDone = true;
                }
                if (renewButt = document.querySelector('.session-timeout-dialog button')) {
                    pageStatus = "Need to Renew Session manually";
                } else {
                    let marketFound = false;
                    for (let j in marketList) {
                        let market = marketList[j];

                        if ((rule.marketName == market.name)
                            && (rule.marketTitle == market.title)) {
                            marketFound = true;
                            if (row = market.element.closest('tr')) {
                                if (valueTD = row.querySelector('td.' + rule.side + ':not(.unhighlighted)')) {
                                    if (valueEle = valueTD.querySelector('strong.odds')) {
                                        oddValue = parseFloat(valueEle.textContent.trim());

                                        if (!rule.isPaused) {
                                            if(rule.orderType !== "record") {
                                                if (!ruleDone) {
                                                    let conditionSatisfied = false;


                                                    if (rule.condition == "market-price") {
                                                        conditionSatisfied = true;
                                                    } else {
                                                        if (rule.orderType != "trigger" && (rule.delay == 1 || rule.delay == 2)) {
                                                            if ((rule.condition == "greater" && oddValue >= (parseFloat(rule.threshold) * 0.9))
                                                                || (rule.condition == "lesser" && oddValue <= (parseFloat(rule.threshold) * 1.1))) {
                                                                conditionSatisfied = true;
                                                            }
                                                        } else {
                                                            if ((rule.condition == "greater" && oddValue >= rule.threshold)
                                                                || (rule.condition == "lesser" && oddValue <= rule.threshold)) {
                                                                conditionSatisfied = true
                                                            }
                                                        }
                                                    }


                                                    if (conditionSatisfied) {
                                                        let delaySatisfied = false;


                                                        if (rule.condition == "market-price" || rule.orderType == "trigger") {
                                                            delaySatisfied = true;
                                                        } else {
                                                            if (rule.delay == 1 || rule.delay == 2) {
                                                                if (overDiv = document.querySelector('.toss h2')) {
                                                                    let currentOv = parseInt(overDiv.textContent.trim().match(/\((.*) (.*)\)/m)[1]);

                                                                    // update over status
                                                                    if (scorecard = document.querySelector('.overs-summary')) {
                                                                        let overs = [].slice.call(scorecard.querySelectorAll('.container'), 0).reverse();
                                                                        if (overs.length > 0) {
                                                                            for (over of overs) {
                                                                                if (overNoDiv = over.querySelector('.info')) {
                                                                                    if (overNo = parseInt(overNoDiv.textContent.trim().match(/Ov (\d*)/m)[1])) {
                                                                                        let balls = [].slice.call(over.querySelectorAll('.balls .ball'), 0).map(m => m.textContent.trim());

                                                                                        if (balls.includes("W") || balls.includes("1,W") || balls.includes("2,W") || balls.includes("3,W") || balls.includes("4,W") || balls.includes("5,W")) {
                                                                                            if (!ruleStatus[rule.id] || ruleStatus[rule.id]?.startOver < overNo) {
                                                                                                ruleStatus[rule.id] = {
                                                                                                    startOver: overNo
                                                                                                };
                                                                                            }
                                                                                        }
                                                                                    } else {
                                                                                        pageStatus = "Unable to parse overNo";
                                                                                    }
                                                                                } else {
                                                                                    pageStatus = "Unable to find overNoDiv";
                                                                                }
                                                                            }
                                                                        } else {
                                                                            pageStatus = "Unable to get ball info";
                                                                        }
                                                                    } else {
                                                                        if (scoreCollapseButt = document.querySelector('.match-stats-body-button')) {
                                                                            scoreCollapseButt.click();
                                                                        }
                                                                        pageStatus = "Unable to find scorecard";
                                                                    }

                                                                    if (ruleStatus[rule.id]?.startOver >= 0) {
                                                                        let overThreshold = (ruleStatus[rule.id].startOver + parseInt(rule.delay));
                                                                        if (currentOv >= overThreshold) {
                                                                            ruleStatus[rule.id] = {
                                                                                startTime: (new Date()).getTime()
                                                                            };
                                                                        } else {
                                                                            pageStatus = "Wicket fell on " + ruleStatus[rule.id].startOver + "Ov. - Will bet on over:" + overThreshold;
                                                                        }
                                                                    }
                                                                } else {
                                                                    pageStatus = "Unable to read Over info.";
                                                                }
                                                            }

                                                            // Init RuleStatus for time delay bets  ==OR==  No wickets to be seen, over delay satisfied
                                                            if (!ruleStatus[rule.id]) {
                                                                ruleStatus[rule.id] = {
                                                                    startTime: (new Date()).getTime()
                                                                };
                                                            }

                                                            let delaySeconds = rule.delay;
                                                            if (rule.delay == 1 || rule.delay == 2) {	//Default 5 sec. delay for over delays
                                                                delaySeconds = 5;
                                                            }
                                                            if ((ruleStatus[rule.id].startTime + (delaySeconds * 1000)) <= (new Date()).getTime()) {
                                                                delaySatisfied = true;
                                                            } else if (ruleStatus[rule.id].startTime) {
                                                                pageStatus = "Betting in " + ((ruleStatus[rule.id].startTime + (delaySeconds * 1000) - (new Date()).getTime()) / 1000) + " second(s)";
                                                            }
                                                        }

                                                        if (delaySatisfied) {

                                                            if (sizeEle = valueTD.querySelector('div.size')) {
                                                                let sizeInt = parseInt(sizeEle.textContent.trim());

                                                                //trigger
                                                                if (rule.orderType == "trigger") {
                                                                    ruleDone = true;
                                                                    console.log("XXXXXXXXXXXXXXXXXXXXXX ---- Placing Trigger  -----  XXXXXXXXXXXXXXXXXXXXXXX rule:" + rule);
                                                                    pageStatus = "Trigger placed";
                                                                    await betPlacedAudio.play();
                                                                } else if (rule.marketTitle !== "" || (sizeInt >= 90 && sizeInt <= 110)) {

                                                                    if (!valueTD.classList.contains('betting-disabled')) {
                                                                        let itr = 1;
                                                                        do {
                                                                            if (document.querySelectorAll('div.bet').length >= 1) {
                                                                                await delay(500);
                                                                                continue;
                                                                            }
                                                                            ongoingBet = true;
                                                                            valueEle.click();
                                                                            let delayIt = 1;
                                                                            do {
                                                                                await delay(100);
                                                                                if (document.querySelectorAll('div.bet').length == 1) {
                                                                                    break;
                                                                                }
                                                                            } while (delayIt++ < 20);  // Delay to wait for bet card rendering after click

                                                                            if (document.querySelectorAll('div.bet').length == 1) {
                                                                                if (betCard = document.querySelector('div.bet')) {
                                                                                    if (amountInput = betCard.querySelector('input.-qa-bet-stake-input')) {
                                                                                        let changeEvent = new Event('change');
                                                                                        amountInput.value = rule.amount;
                                                                                        amountInput.dispatchEvent(changeEvent);
                                                                                        await delay(500);
                                                                                        if (placeBetsButt = document.querySelector('.summary-buttons > .apl-btn-primary')) {
                                                                                            if (!placeBetsButt.disabled) {
                                                                                                if (document.querySelectorAll('div.bet').length == 1) {
                                                                                                    console.log("XXXXXXXXXXXXXXXXXXXXXX ---- Placing bet  -----  XXXXXXXXXXXXXXXXXXXXXXX rule:" + rule);
                                                                                                    pageStatus = "Bet placed";
                                                                                                    //Place bet here :
                                                                                                    placeBetsButt.click();
                                                                                                    ruleDone = true;

                                                                                                    await betPlacedAudio.play();
                                                                                                } else {
                                                                                                    pageStatus = "Other bets interfering in last operation";
                                                                                                    console.log(pageStatus);
                                                                                                    if (rmButt = document.querySelector('.summary-buttons > .apl-btn-remove:not(.apl-btn-mini)')) {
                                                                                                        rmButt.click();
                                                                                                    }
                                                                                                }
                                                                                            } else {
                                                                                                pageStatus = "Place bets button disabled";
                                                                                                console.log(pageStatus);
                                                                                                if (rmButt = document.querySelector('.summary-buttons > .apl-btn-remove:not(.apl-btn-mini)')) {
                                                                                                    rmButt.click();
                                                                                                }
                                                                                            }
                                                                                        } else {
                                                                                            pageStatus = "Place bets button not found";
                                                                                            console.log(pageStatus);
                                                                                            if (rmButt = document.querySelector('.summary-buttons > .apl-btn-remove:not(.apl-btn-mini)')) {
                                                                                                rmButt.click();
                                                                                            }
                                                                                        }
                                                                                    } else {
                                                                                        pageStatus = "Unable to find Amount input field";
                                                                                        console.log(pageStatus);
                                                                                        if (rmButt = document.querySelector('.summary-buttons > .apl-btn-remove:not(.apl-btn-mini)')) {
                                                                                            rmButt.click();
                                                                                        }
                                                                                    }
                                                                                } else {
                                                                                    pageStatus = "Unable to find bet card";
                                                                                    console.log(pageStatus);
                                                                                    if (rmButt = document.querySelector('.summary-buttons > .apl-btn-remove:not(.apl-btn-mini)')) {
                                                                                        rmButt.click();
                                                                                    }
                                                                                }
                                                                                break;
                                                                            } else if (rmButt = document.querySelector('.summary-buttons > .apl-btn-remove:not(.apl-btn-mini)')) {
                                                                                rmButt.click();
                                                                                pageStatus = "Other bets interfering";
                                                                                console.log(pageStatus);
                                                                            }
                                                                            await delay(1000);
                                                                        } while (itr++ < 10);
                                                                        ongoingBet = false;
                                                                    } else {
                                                                        pageStatus = "Bet disabled";
                                                                    }
                                                                } else {
                                                                    pageStatus = "Bet size not inbetween 90 & 110";
                                                                }
                                                            } else {
                                                                pageStatus = "Bet size element not found";
                                                            }
                                                        }
                                                    } else {
                                                        pageStatus = "Condition not satisfied";
                                                    }
                                                } else {
                                                    pageStatus = "Rule done";
                                                }
                                            } else {
                                                pageStatus = "Recording";
                                            }
                                        } else {
                                            pageStatus = "Paused";
                                        }
                                    } else {
                                        pageStatus = "Bet disabled";
                                    }
                                } else {
                                    pageStatus = "Bet disabled";
                                }
                            } else {
                                pageStatus = "Error finding market row";
                            }
                        }
                    }
                    if (!marketFound) {
                        pageStatus = "Market not found";
                    }
                }

            } else {
                pageStatus = "Inactive, Waiting for parent rule";
            }
            port.postMessage({
                id: rule.id,
                value: oddValue,
                pageStatus: pageStatus,
                isDone: ruleDone
            });
        }
    } catch (e) {
        if (((new Date()).getTime() - lastErrorReport) > 20000) {
            lastErrorReport = (new Date()).getTime();
            console.log(e);
        }
    }
}

// Listen for messages from the popup.
chrome.runtime.onMessage.addListener((msg, sender, response) => {
    if ((msg.from === 'popup') && (msg.subject === 'market-list')) {
        let eventList = document.querySelectorAll('div.event-name');

        if (eventList.length == 0) {
            response(undefined);
            return;
        }

        let matchName = "N/A";
        if (matchNameElement = document.querySelector('div.header h1')) {
            matchName = matchNameElement.textContent.trim();
        }

        let hasBallDelay = false;
        if (ballDelayInfo = document.querySelector('.match-stats-body-button')) {
            hasBallDelay = true;
        }
        let marketList = [];
        for (let i = 0; i < eventList.length; i++) {
            let marketTitle = "";
            if (eventList[i].closest('table').querySelector('.market-name.title>span')) {
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

let run = async () => {
    let lastCheckedSession = (new Date()).getTime();
    await delay(2000);
    while (true) {
        await observeRules();
        await delay(100);
        console.log(JSON.stringify(ruleList));
        if(lastCheckedSession + (5 * 60 * 1000) <= (new Date()).getTime()) {
            lastCheckedSession = (new Date()).getTime();
            await keepActive();
        }
    }
}
run();

let keepActive = async () => {
    if (anyBet = document.querySelector('td.back:not(.betting-disabled):not(.unhighlighted)')) {
        anyBet.click();
        await delay(1000);
        if (rmButt = document.querySelector('.summary-buttons > .apl-btn-remove:not(.apl-btn-mini)')) {
            rmButt.click();
        } else {
            await delay(2000);
            if (rmButt = document.querySelector('.summary-buttons > .apl-btn-remove:not(.apl-btn-mini)')) {
                rmButt.click();
            } else {
                console.log("Keep page active function failed!!!");
            }
        }
    } else {
        console.log("Keep page active function failed!!!");
    }

    await delay(10 * 1000);
}

//keepActive();

async function delay(ms) {
    return await new Promise(resolve => setTimeout(resolve, ms));
}