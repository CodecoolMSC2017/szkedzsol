let tasksTableEl;
let tasksTableBodyEl;

function onTaskClicked() {
    const taskId = this.dataset.taskId;

    const params = new URLSearchParams();
    params.append('id', taskId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTaskResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/task?' + params.toString());
    xhr.send();
}

function onTaskAddResponse() {
    clearMessages();
    if (this.status === OK) {
        appendTask(JSON.parse(this.responseText));
    } else {
        onOtherResponse(tasksContentDivEl, this);
    }
}

function onTaskAddClicked() {
    const taskFormEl = document.forms['task-form'];

    const nameInputEl = taskFormEl.querySelector('input[name="name"]');
    const descriptionInputEl = taskFormEl.querySelector('input[name="description"]');

    const name = nameInputEl.value;
    const description = descriptionInputEl.value;

    const params = new URLSearchParams();
    params.append('name', name);
    params.append('description', description);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTaskAddResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/tasks', false);  // with this 'false' it works but maybe it's not the best solution.
    xhr.send(params);
}

function appendTask(task) {
    const idTdEl = document.createElement('td');
    idTdEl.textContent = task.id;

    const aEl = document.createElement('a');
    aEl.textContent = task.name;
    aEl.href = 'javascript:void(0);';
    aEl.dataset.taskId = task.id;
    aEl.addEventListener('click', onTaskClicked);

    const nameTdEl = document.createElement('td');
    nameTdEl.appendChild(aEl);

    const descriptionTdEl = document.createElement('td');
    descriptionTdEl.textContent = task.description;
    descriptionTdEl.setAttribute('class','zoom');
    const deletebuttonEl = document.createElement('button');
    deletebuttonEl.textContent = 'X';
    deletebuttonEl.value = task.id;
    deletebuttonEl.addEventListener('click', onTaskDeleteClicked);

    const deleteTdEl = document.createElement('td');
    deleteTdEl.appendChild(deletebuttonEl);

    const trEl = document.createElement('tr');
    trEl.id = task.id;
    trEl.appendChild(idTdEl);
    trEl.appendChild(nameTdEl);
    trEl.appendChild(descriptionTdEl);
    trEl.appendChild(deleteTdEl);
    tasksTableBodyEl.appendChild(trEl);
}

function appendTasks(tasks) {
    removeAllChildren(tasksTableBodyEl);

    for (let i = 0; i < tasks.length; i++) {
        const task = tasks[i];
        appendTask(task);
    }
}

function onTasksLoad(tasks) {
    tasksTableEl = document.getElementById('tasks');
    tasksTableBodyEl = tasksTableEl.querySelector('tbody');

    appendTasks(tasks);
}

function onTasksResponse() {
    if (this.status === OK) {
        showContents(['tasks-content', 'logout-content']);
        onTasksLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(tasksContentDivEl, this);
    }
}

function onTaskDeleteClicked() {
    const taskTr = document.getElementById(this.value);

    const data = JSON.stringify({"taskId": taskTr.id});

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTasksResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/tasks');
    xhr.setRequestHeader("Content-type","application/json");
    xhr.send(data);
}
