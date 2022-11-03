var ruleList = [];

var siteContainer = document.getElementById('site-container');
var addSiteBtn = document.getElementById('add-site-button');
var ruleListContainer = document.getElementById('rule-list');
var hostExtract = /^(?:https?:\/\/)?(?:[^@\/\n]+@)?(?:www\.)?([^:\/?\n]+)/;
var editModal = new bootstrap.Modal(document.getElementById('edit-modal'));
var modal_ruleID = document.getElementById('rule-id');
var modal_threshold = document.getElementById('threshold');
var modal_amount = document.getElementById('amount');
var modal_delay = document.getElementById('delay-dropdown');
var modal_save = document.getElementById('save-edit');

document.querySelector('span[id="ext-version"]').textContent = chrome.runtime.getManifest().version;

document.addEventListener('click', function (event) {

    if (event.target == modal_save) {
        let ruleId = modal_ruleID.value;
        let threshold = modal_threshold.value;
        let amount = modal_amount.value;
        let delay = modal_delay.value;

        chrome.storage.local.get('rules', function (data) {
            let rList = data.rules;
            for (rule of rList) {
                if (rule.id == ruleId) {
                    rule.threshold = threshold;
                    rule.amount = amount;
                    rule.delay = delay;
                }
            }

            chrome.storage.local.set({rules: rList}, function () {
                location.reload();
            });
        });
    }


    if (event.target.matches('button.pause-rule')) {
        let row = $(event.target).parents('tr')[0];
        let ruleId = row.id;

        chrome.storage.local.get('rules', function (data) {
            let rList = data.rules;
            for (rule of rList) {
                if (rule.id == ruleId) {
                    rule.isPaused = !rule.isPaused;
                }
            }

            chrome.storage.local.set({rules: rList}, function () {
                location.reload();
            });
        });

    }

    if (event.target.matches('span.edit-rule')) {
        let row = $(event.target).parents('tr')[0];
        let ruleId = row.id;

        let currRule = ruleList.find(r => r.id == ruleId);
        modal_ruleID.value = ruleId;
        modal_threshold.value = currRule.threshold;
        modal_amount.value = currRule.amount;
        modal_delay.value = currRule.delay;
        editModal.show();
    }


    if (event.target.matches('span.delete-rule')) {
        let row = $(event.target).parents('tr')[0];
        let ruleId = row.id;

        if (confirm('Do you really want to delete this rule?')) {
            chrome.storage.local.get('rules', function (data) {
                let rList = data.rules;
                rList = rList.filter(function (ele) {
                    return ele.id != ruleId
                });
                chrome.storage.local.set({rules: rList}, function () {
                    location.reload();
                });
            });
        }

    }

    if (event.target.matches('.close-modal')) {
        editModal.hide();
    }
});

chrome.storage.local.get('rules', function (rulesData) {
    ruleList = (rulesData.rules) ? rulesData.rules : [];
    const groupedRules = ruleList.reduce((hash, obj) => ({
        ...hash,
        [obj['matchName']]: (hash[obj['matchName']] || []).concat(obj)
    }), {});

    for (let matchName in groupedRules) {
        let tempRule = groupedRules[matchName][0];


        let gRow = document.createElement("tr");
        var titleTH = document.createElement('th');
        titleTH.setAttribute('class', 'market-title');
        titleTH.setAttribute('colspan', '30');
        let tempHost = tempRule.url.match(hostExtract);
        let hostName = '';
        console.log(tempHost);
        if (tempHost && tempHost.length >= 2) {
            hostName = tempHost[1];
        }
        let aa = document.createElement("a");
        aa.setAttribute('href', tempRule.url);
        aa.setAttribute('target', '_blank');
        aa.appendChild(document.createTextNode(tempRule.matchName + ' - ' + hostName));
        titleTH.appendChild(aa);
        gRow.appendChild(titleTH);

        ruleListContainer.appendChild(gRow);

        for (var rule of groupedRules[matchName]) {
            let row = document.createElement("tr");
            row.setAttribute('id', rule.id);
            switch (rule.orderType) {
                case 'order':
                    row.setAttribute('class', 'order-rule');
                    break;
                case 'record':
                    row.setAttribute('class', 'record-rule');
                    break;
                case 'trigger':
                    row.setAttribute('class', 'trigger-rule');
                    break;
            }

            if (rule.isPaused) {
                row.classList.add('paused-rule');
            }

            var th = document.createElement("th");
            th.appendChild(document.createTextNode(rule.marketName));
            row.appendChild(th);

            td = document.createElement('td');
            td.appendChild(document.createTextNode(rule.marketTitle));
            row.appendChild(td);

            td = document.createElement('td');
            if (rule.amount) {
                td.appendChild(document.createTextNode(rule.amount));
            }
            row.appendChild(td);

            td = document.createElement('td');
            td.appendChild(document.createTextNode(rule.side));
            row.appendChild(td);

            td = document.createElement('td');
            if (!rule.delay) {
                td.appendChild(document.createTextNode(""));
            } else if (rule.delay == 1 || rule.delay == 2) {
                td.appendChild(document.createTextNode(rule.delay + " Ov."));
            } else {
                td.appendChild(document.createTextNode(rule.delay + " sec."));
            }

            row.appendChild(td);

            td = document.createElement('td');
            if (!rule.condition) {
                td.appendChild(document.createTextNode("Recording"));
            } else {
                if (rule.condition === 'market-price') {
                    td.appendChild(document.createTextNode(
                        rule.condition
                    ));
                } else {
                    td.appendChild(document.createTextNode(
                        rule.condition + " than " + rule.threshold
                    ));
                }
            }
            row.appendChild(td);

            td = document.createElement('td');
            td.className = "low";
            td.appendChild(document.createTextNode(rule.lowest));
            row.appendChild(td);

            td = document.createElement('td');
            td.className = rule.side + " live-value";
            //td.setAttribute('data-toggle', 'tooltip');
            //td.setAttribute('data-placement', 'top');
            //td.setAttribute('data-html', 'true');
            //td.setAttribute('data-original-title', 'high: '+ rule.highest + '<br> low: ' + rule.lowest);

            td.appendChild(document.createTextNode(""));
            row.appendChild(td);

            td = document.createElement('td');
            td.className = "high";
            td.appendChild(document.createTextNode(rule.highest));
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
            var pauseBtn = document.createElement('button');
            pauseBtn.setAttribute("class", "pause-rule");
            if (rule.isDone) {
                pauseBtn.setAttribute("disabled", "true");
            }
            if (rule.isPaused) {
                pauseBtn.appendChild(document.createTextNode('\u25B6'));
            } else {
                pauseBtn.appendChild(document.createTextNode('\u23F8'));
            }
            td.appendChild(pauseBtn);
            row.appendChild(td);

            td = document.createElement('td');
            var editBtn = document.createElement('span');
            editBtn.setAttribute("class", "edit-rule");
            editBtn.setAttribute("type", "button");
            editBtn.appendChild(document.createTextNode(' \u270E'));
            td.appendChild(editBtn);
            row.appendChild(td);

            td = document.createElement('td');
            var delBtn = document.createElement('span');
            delBtn.setAttribute("class", "delete-rule");
            delBtn.setAttribute("type", "button");
            delBtn.appendChild(document.createTextNode(' \u2716'));
            td.appendChild(delBtn);
            row.appendChild(td);

            ruleListContainer.appendChild(row);
        }


        let lRow = document.createElement("tr");
        var blankTH = document.createElement('th');
        blankTH.setAttribute('class', 'market-footer');
        blankTH.setAttribute('colspan', '30');
        lRow.appendChild(blankTH);
        ruleListContainer.appendChild(lRow);
    }
});

chrome.storage.onChanged.addListener(function (changes, namespace) {
    for (var key in changes) {
        if (key === "rules") {

            let currRuleSize = ruleList.length;
            ruleList = changes[key].newValue;
            if (ruleList.length != currRuleSize) {
                location.reload();
            }
        }
    }
});


var port = chrome.runtime.connect({name: "rule-status-stream"});
port.onMessage.addListener(function (ruleStatus) {
    console.log('listening');
    for (ruleId in ruleStatus) {
        ruleInfo = ruleStatus[ruleId];
        if (row = ruleListContainer.querySelector('tr[id="' + ruleId + '"]')) {
            let valueTD = row.querySelector('.live-value');
            let lastUpdatedTD = row.querySelector('.last-updated');
            let statusTD = row.querySelector('.status');
            let pageStatusTD = row.querySelector('.page-status');
            let lowTD = row.querySelector('.low');
            let highTD = row.querySelector('.high');

            valueTD.textContent = ruleInfo.currValue;
            lastUpdatedTD.dataset.utime = ruleInfo.lastUpdated ? ruleInfo.lastUpdated : 0;
            statusTD.textContent = ruleInfo.status;
            pageStatusTD.textContent = ruleInfo.pageStatus;
            if (ruleInfo.lowest)
                lowTD.textContent = ruleInfo.lowest;
            if (ruleInfo.highest)
                highTD.textContent = ruleInfo.highest;
        }
    }
});

function timeSince(date) {
    if (date == 0) {
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

let run = async () => {
    while (true) {
        let lutTDs = document.getElementsByClassName('last-updated');
        for (let ele of lutTDs) {
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
	