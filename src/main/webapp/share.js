let schedulesShareTableEl;
let link;

function onScheduleShareClicked() {
    const currentId = document.getElementById('schedule-id').textContent;
    const params = new URLSearchParams();
    params.append('currentId', currentId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleShareResponse, false);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'share' + "?id=" + currentId);
    xhr.send();
}
function onScheduleShareResponse() {
    clearMessages();

    if (this.status === 200) {
        link = this.responseURL;
        onSharedScheduleLoad();
    } else {
        onOtherResponse(sharedSchedulesContentDivEl, this);
    }
}
function appendShareSchedule() {
    const aEl = document.createElement('p');
    removeAllChildren(aEl);
    let node = document.createTextNode(link);
    aEl.appendChild(node);
    schedulesShareTableEl.appendChild(aEl);
    linkPop();
}

function onSharedScheduleLoad() {
    schedulesShareTableEl = document.getElementById('shared-schedule');
    appendShareSchedule();
}

function linkPop() {
    var person = prompt("Here's a link for your Schedule:", link);
}
