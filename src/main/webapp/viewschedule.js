let tableDivEl;

function onViewClicked() {
    const scheduleEl = document.getElementById("schedule-id");
    const scheduleId = scheduleEl.textContent;

    const params = new URLSearchParams();
    params.append('scheduleId', scheduleId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onViewResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/viewschedule?' + params.toString());
    xhr.send();
}

function onViewResponse() {
    clearMessages();
    if (this.status === OK) {
        showContents(["view-schedule"]);
        onViewRecieve(JSON.parse(this.responseText));
    } else {
        onOtherResponse(tasksContentDivEl, this);
    }
}

function onViewRecieve(text) {
    const scheduleDtoList = text;
    const schedDivEl = document.getElementById("view-schedule");

    for (let i = 0; i < scheduleDtoList.length; i++) {
        let scheduleDto = scheduleDtoList[i];
        let schedule = scheduleDto.schedule;
        let col = scheduleDto.col;
        let slot = scheduleDto.slot;
        let task = scheduleDto.task;
        console.log(schedule);
        console.log(col);
        console.log(slot);
        console.log(task);

    }
}

function createViewTable(schedule, col, slot, task, colNums) {
    const tableDivEl = document.createElement('table');
    tableDivEl.setAttribute('id', 'vTable');

    const tHeadEl = document.createElement('thead');
    const colName = document.createElement('p');
    colName.textContent = col.name;
    tHeadEl.appendChild(colName);

    const tBodyEl = document.createElement('tbody');
    const trEl = document.createElement('tr');
    const tdEl = document.createElement('td');




}