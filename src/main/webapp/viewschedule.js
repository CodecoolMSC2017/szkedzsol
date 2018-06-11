let tableDivEl;
let schedDivEl;
let tableEl;

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
    const schedules = [];
    const cols = [];
    const slots = [];
    const tasks = [];
    const slotTask = [];
    const sT = [];

    for (let i = 0; i < scheduleDtoList.length; i++) {
        let scheduleDto = scheduleDtoList[i];
        let schedule = scheduleDto.schedule;
        let col = scheduleDto.col;
        let slot = scheduleDto.slot;
        let task = scheduleDto.task;

        //FILL SCHEDULES LIST
        schedules.push(schedule);
        //console.log(schedules[i].name);

        //FILL COLS LIST
        cols.push(col);
       // console.log(cols[i].name);

        //FILL SLOTS LIST
        slots.push(slot);
       // console.log(slots[i].id);
        //console.log("From "+ slots[i].start);

        //FILL TASKS LIST
        tasks.push(task);
        //console.log(tasks[i].name);

        //SLOT WITH TASK
        slotTask[i] = [slot,task];



    }
        createTableHead(cols[0]);

        for(let j = 0; j < tasks.length; j++){

        if(cols[j].name === cols[0].name){

        createViewTable(slotTask[j],schedDivEl);
        }
    }
}

function createViewTable(slotTask, schedDivEl) {
    const tableEl = document.getElementById('vTable');

    const tBodyEl = document.createElement('tbody');
    const tdEl = document.createElement('td');

    tdEl.textContent = slotTask[0].start;
    tdEl.textContent = slotTask[1].name;
    tBodyEl.appendChild(tdEl);
    tableEl.appendChild(tBodyEl);
    schedDivEl.appendChild(tableEl);

}

function createTableHead(col) {
    const tableDivEl = document.createElement('table');
    tableDivEl.setAttribute('id', 'vTable');
    const tHeadEl = document.createElement('thead');
    const colName = document.createElement('p');
    colName.textContent = col.name;
    tHeadEl.appendChild(colName);
    tableDivEl.appendChild(tHeadEl);
}
