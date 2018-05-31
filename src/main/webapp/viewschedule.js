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
    const schedules = [];
    const cols = [];
    const slots = [];
    const tasks = [];

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

    }

    //CREATE TABLE ELEMENTS
    const tableDivEl = document.createElement('table');
    tableDivEl.setAttribute('id', 'vTable');

    const tHeadEl = document.createElement('thead');
    tHeadEl.setAttribute('id', 'viewTabHead');
    const colName = document.createElement('p');
    colName.setAttribute('id', 'vtheadName');
    const tBodyEl = document.createElement('tbody');


    for(let j = 0; j < tasks.length; j++){
    const trEl = document.createElement('tr');
    const tdEl = document.createElement('td');


    const slotContent = document.createElement('p');
    const slotTime = document.createElement('p');

        if(cols[j].name === cols[0].name){

            colName.innerHTML = cols[0].name


            console.log(tasks[j].name);
            slotTime.innerHTML = 'From '+slots[j].start;
            slotContent.innerHTML = tasks[j].name;

            //DISPLAY ONE DAY

                    tdEl.appendChild(slotTime);
                    tdEl.appendChild(slotContent);
                    trEl.appendChild(tdEl);
                    tBodyEl.appendChild(trEl);

                    tableDivEl.appendChild(tBodyEl);
        }

    }

        tHeadEl.appendChild(colName);
        tableDivEl.appendChild(tHeadEl);
    schedDivEl.appendChild(tableDivEl);
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
