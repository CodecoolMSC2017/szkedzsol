let schedulesTableEl;
let schedulesTableBodyEl;

function onScheduleClicked() {
    const scheduleId = this.dataset.scheduleId;

    const params = new URLSearchParams();
    params.append('id', scheduleId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedule?' + params.toString());
    xhr.send();
}

function onScheduleAddResponse() {
    clearMessages();
    if (this.status === OK) {
        appendSchedule(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this);
    }
}

function onScheduleAddClicked() {
    const scheduleFormEl = document.forms['add-schedule-form'];

    const nameInputEl = scheduleFormEl.querySelector('input[name="scheduleTitle"]');

    const name = nameInputEl.value;

    const params = new URLSearchParams();
    params.append('scheduleTitle', name);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleAddResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/schedules', false);
    xhr.send(params);
}

function onScheduleDeleteClicked() {
    const scheduleTr = document.getElementById(this.value);

    const data = JSON.stringify({"scheduleId": scheduleTr.id});

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSchedulesResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/schedules');
    xhr.setRequestHeader("Content-type","application/json");
    xhr.send(data);
}

function appendSchedule(schedule) {
    const idTdEl = document.createElement('td');
    idTdEl.textContent = schedule.id;

    const aEl = document.createElement('a');
    aEl.setAttribute("id", schedule.id);
    aEl.textContent = schedule.name;
    aEl.href = 'javascript:void(0);';
    aEl.dataset.scheduleId = schedule.id;
    aEl.addEventListener('click', onScheduleClicked);

    const nameTdEl = document.createElement('td');
    nameTdEl.appendChild(aEl);

    const deletebuttonEl = document.createElement('button');
    deletebuttonEl.textContent = 'X';
    deletebuttonEl.value = schedule.id;
    deletebuttonEl.addEventListener('click', onScheduleDeleteClicked);

    const deleteTdEl = document.createElement('td');
    deleteTdEl.appendChild(deletebuttonEl);

    const trEl = document.createElement('tr');
    trEl.id = schedule.id;
    trEl.appendChild(idTdEl);
    trEl.appendChild(nameTdEl);
    trEl.appendChild(deleteTdEl);
    schedulesTableBodyEl.appendChild(trEl);

}

function appendSchedules(schedules) {
    removeAllChildren(schedulesTableBodyEl);

    for (let i = 0; i < schedules.length; i++) {
        const schedule = schedules[i];
        appendSchedule(schedule);
    }
}

function onSchedulesLoad(schedules) {
    schedulesTableEl = document.getElementById('schedules');
    schedulesTableBodyEl = schedulesTableEl.querySelector('tbody');

    appendSchedules(schedules);
}

function onSchedulesResponse() {
    if (this.status === OK) {
        showContents(['schedules-content', 'back-to-profile-content', 'logout-content']);
        onSchedulesLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this);
    }
}
