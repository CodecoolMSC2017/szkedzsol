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

    if (this.status === 202) {
        link = this.responseURL;
        onSharedScheduleLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(sharedSchedulesContentDivEl, this);
    }
}
function appendShareSchedule(schedule) {
    const aEl = document.createElement('p');
    let node = document.createTextNode(link);
    const btn = document.getElementById('shareButton');
    btn.addEventListener('click',linkPop);
    aEl.appendChild(node);

    schedulesShareTableEl.appendChild(aEl);


}
function onSharedScheduleLoad(schedule) {
    schedulesShareTableEl = document.getElementById('shared-schedule');
    appendShareSchedule(schedule);
   }

function linkPop() {
    var person = prompt("Here's a link for your Schedule:",link);
}
