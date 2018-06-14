let tableDivEl;
let schedDivEl;
let colNames;

function onViewClicked() {
    const sharedScheduleId = document.getElementById("shareId");
    if (sharedScheduleId.textContent === '') {
        const scheduleEl = document.getElementById("schedule-id");
        const scheduleId = scheduleEl.textContent;

        const params = new URLSearchParams();
        params.append('scheduleId', scheduleId);

        const xhr = new XMLHttpRequest();
        xhr.addEventListener('load', onViewResponse);
        xhr.addEventListener('error', onNetworkError);
        xhr.open('GET', 'protected/viewschedule?' + params.toString());
        xhr.send();
    } else {
        const scheduleId = sharedScheduleId.textContent;

        const params = new URLSearchParams();
        params.append('scheduleId', scheduleId);

        const xhr = new XMLHttpRequest();
        xhr.addEventListener('load', onViewResponse);
        xhr.addEventListener('error', onNetworkError);
        xhr.open('POST', 'share?id=' + scheduleId);
        xhr.send(params);
        // sharedScheduleId.textContent = null;
    }
}

function onViewResponse() {
    clearMessages();
    const sharedScheduleId = document.getElementById("shareId");
    if (this.status === OK && sharedScheduleId.textContent === '') {
        showContents(["view-schedule", "temp-share-button"]);
        onViewRecieve(JSON.parse(this.responseText));
    } else if (this.status === OK && sharedScheduleId.textContent !== '') {
        showContents(["view-schedule"]);
        onViewRecieve(JSON.parse(this.responseText));
    } else {
        onOtherResponse(tasksContentDivEl, this);
    }
}

function onViewRecieve(text) {
    const scheduleDtoList = text;
    schedDivEl = document.getElementById("view-schedule");
    schedDivEl.textContent = null;

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

        //FILL SLOTS LIST
        slots.push(slot);
        // console.log(slots[i].id);
        //console.log("From "+ slots[i].start);

        //FILL TASKS LIST
        tasks.push(task);
        //console.log(tasks[i].name);

        //SLOT WITH TASK
        slotTask[i] = [slot, task, col];

    }

    let colNames = separateCols(cols);
    let uniqueNames = new Set(colNames);

    createViewTable(uniqueNames, slotTask);
}

function separateCols(cols) {
    const colNames = [];
    for (let k = 0; k < cols.length; k++) {
        if (cols[k].name === cols[0].name) {
            colNames.push(cols[0].name);
        }
        else if (cols[k] !== cols[0].name) {
            colNames.push(cols[k].name);
        }
    }
    return colNames;
}

function createViewTableBody(tableDivElId, slotTask) {
    const tbodyEl = document.createElement('tbody');
    const viewTableEl = document.getElementById(tableDivElId);

    slotTask = slotTask.sort(compare);
    for (let l = 0; l < slotTask.length; l++) {
        const trEl = document.createElement('tr');
        const tdEl = document.createElement('td');
        if (tableDivElId == slotTask[l][2].name) {
            tdEl.textContent = 'From ' + slotTask[l][0].start + ':00 ' + slotTask[l][1].name;
            trEl.appendChild(tdEl);
            tbodyEl.appendChild(trEl);
        }
    }

    return tbodyEl;
}

function createViewTableHead(tableDivElId, uniqueNames) {

    const theadEl = document.createElement('thead');
    const tableDivEl = document.getElementById('viewTable');
    const viewTdEl = document.createElement('td');

    viewTdEl.textContent = uniqueNames;

    theadEl.appendChild(viewTdEl);

    return theadEl;
}

function createViewTable(uniqueNames, slotTask) {
    uniqueNames.forEach((key, value) => {
        const tableDivEl = document.createElement('table');
        tableDivEl.setAttribute("id", value);
        tableDivEl.style.cssFloat = 'left';
        const tableDivElId = tableDivEl.id;
        const viewTableBodyEl = createViewTableBody(tableDivElId, slotTask);
        const viewTableHeadEl = createViewTableHead(tableDivElId, value);
        tableDivEl.appendChild(viewTableHeadEl);
        tableDivEl.appendChild(viewTableBodyEl);
        schedDivEl.appendChild(tableDivEl);
    });

    return tableDivEl;
}

function compare(a, b) {
    if (a[0].start < b[0].start)
        return -1;
    if (a[0].start > b[0].start)
        return 1;
    return 0;
}
