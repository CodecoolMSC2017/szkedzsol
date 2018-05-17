function createTask(task) {
    const liEl = document.createElement('li');
    liEl.textContent = `${task.id} - ${task.name} - ${task.description}`;
    return liEl;
}

function onTaskLoad(task) {
    const taskIdSpandEl = document.getElementById('task-id');
    const taskNameSpanEl = document.getElementById('task-name');
    const taskDescriptionSpanEl = document.getElementById('task-description');

    taskIdSpandEl.textContent = task.id;
    taskNameSpanEl.textContent = task.name
    taskDescriptionSpanEl.textContent = task.description;
}

function onTaskResponse() {
    if (this.status === OK) {
        clearMessages();
        showContents(['task-content', 'logout-content']);
        onTaskLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(taskContentDivEl, this);
    }
}

function onTaskModifyClicked() {
    const taskFormEl = document.forms['modify-task-form'];

    const nameInputEl = taskFormEl.querySelector('input[name="name"]');
    const descriptionInputEl = taskFormEl.querySelector('input[name="description"]');

    const name = nameInputEl.value;
    const description = descriptionInputEl.value;

    const divEl = document.getElementById('task-content');
    const pEls = divEl.getElementsByTagName('p');
    const id = pEls[0].children[0].innerHTML;

    const scheduleTr = document.getElementById(this.value);

    const data = JSON.stringify({"taskId" : id, "taskName" : name, "taskDescription" : description});

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTaskResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('PUT', 'protected/task');
    xhr.setRequestHeader("Content-type","application/json");
    xhr.send(data);
}