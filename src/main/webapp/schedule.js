let tasksListTableEl;
let dropTableEl;
let divEl;
let dropDivEl;
let droppedIn = false;
let currDiv;


function onScheduleLoad(schedule) {
    const scheduleIdSpandEl = document.getElementById('schedule-id');
    const scheduleNameSpanEl = document.getElementById('schedule-name');

    scheduleIdSpandEl.textContent = schedule.id;
    scheduleNameSpanEl.textContent = schedule.name;
}

function onScheduleResponse() {
    if (this.status === OK) {
        clearMessages();
        showContents(['schedule-content', 'back-to-profile-content', 'logout-content']);
        onScheduleLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this);
    }
}

function onHowManyResponse(){
    if (this.status === OK) {
            clearMessages();
            showContents(['back-to-profile-content', 'logout-content', 'schedule-tasks-content']);
            onTasksReceived(JSON.parse(this.responseText));
        } else {
            onOtherResponse(schedulesContentDivEl, this);
        }
}

function onHowManyColClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onHowManyResponse);
    xhr.addEventListener('load', createDropZone);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/tasks');
    xhr.send();
}

function createTasksTableBody(tasks) {
    const tbodyEl = document.createElement('tbody');

    for (let i = 0; i < tasks.length; i++) {
        const task = tasks[i];

        // creating objects
        const objTdEl = document.createElement('td');

        // creating name cell
        const dataUserIdAttr = document.createAttribute('data-task-id');
        dataUserIdAttr.value = task.id;


	const objDivEl = document.createElement('div');
	objDivEl.setAttribute('class', "objects");
    objDivEl.textContent = task.name;
    objDivEl.setAttributeNode(dataUserIdAttr);
	objDivEl.setAttribute('draggable', true);
	objDivEl.setAttribute('ondragstart', 'drag_start(event)');
	objDivEl.setAttribute('ondragend', 'drag_end(event)');
        objDivEl.addEventListener('click', onLoadTasks);



        // creating row
    const trEl = document.createElement('tr');
	objTdEl.appendChild(objDivEl);
	trEl.appendChild(objTdEl);
        tbodyEl.appendChild(trEl);
    }

    return tbodyEl;
}
function onTasksReceived(text) {
    let loadButtonEl = document.getElementById('load-tasks');
    loadButtonEl.remove();

    const tasks = text;

    const divEl = document.getElementById('task-list-drop');
    divEl.appendChild(createTasksTable(tasks));
}

function onLoadTasks() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTasksReceived);
    xhr.open('GET', 'protected/tasks' + params.toString());
    xhr.send();
}

function createTasksTableHeader() {

    const nameTdEl = document.createElement('td');
    nameTdEl.textContent = 'Tasks';

    const trEl = document.createElement('tr');
    trEl.appendChild(nameTdEl);

    const theadEl = document.createElement('thead');
    theadEl.appendChild(trEl);
    return theadEl;
}

function createTasksTable(tasks) {
    const tableEl = document.createElement('table');
    tableEl.style.cssFloat = 'left';
    tableEl.appendChild(createTasksTableHeader());
    tableEl.appendChild(createTasksTableBody(tasks));
    return tableEl;
}

function createDropTableBody(dropTableElId){
    const tbodyEl = document.createElement('tbody');
    const dropTableEl = document.getElementById('colTable');
    let counter = 0;
    while(counter < 24){
        const dropTableEl = document.getElementById('colTable');
        const dropTrEl = document.createElement('tr');
        const dropTdEl = document.createElement('td');

        // CREATE DROPZONE (SLOT) WITH IT'S ATTRIBUTES
        const dropDivEl = document.createElement('div');
        dropDivEl.id = dropTableElId+counter;
        dropDivEl.className = 'drop_zone';
        const dropDivElId = dropDivEl.id;
        dropDivEl.setAttribute('ondrop', 'drag_drop(event)');
        dropDivEl.setAttribute('ondragover', 'return false');


        //CREATE GET DATA AND CLEAR BUTTON
        const getDataButtonEl = document.createElement('button');
        getDataButtonEl.setAttribute('drop_zone', dropTableElId+counter);
        const clearButtonEl = document.createElement('button');
        clearButtonEl.setAttribute('drop_zone', dropTableElId+counter);

        getDataButtonEl.addEventListener('click', readDropZone);
        getDataButtonEl.textContent = 'Get Data';
        clearButtonEl.addEventListener('click', clearDropZone);
        clearButtonEl.textContent = 'Clear';

        dropTdEl.appendChild(dropDivEl);
        dropTdEl.appendChild(getDataButtonEl);
        dropTdEl.appendChild(clearButtonEl);
        dropTrEl.appendChild(dropTdEl);
        tbodyEl.appendChild(dropTrEl);
        counter++;
        }
    return tbodyEl;
}

function createDropZone(){
    const scheduleFormEl = document.forms['how-many'];
    const nameInputEl = scheduleFormEl.querySelector('input[name="howMany"]');
    const number = nameInputEl.value;
    let dayCounter = 0;

    const divEl = document.getElementById('task-list-drop');
    for(let i = 0; i < number; i++){
    const dropTableEl = document.createElement('table');
    dropTableEl.setAttribute("id", "colTable"+i);
    dropTableEl.style.cssFloat = 'right';
    const dropTableElId = dropTableEl.id;
    const dropTableBodyEl = createDropTableBody(dropTableElId);
    dropTableEl.appendChild(dropTableBodyEl);
    divEl.appendChild(dropTableEl);
    }

    return dropTableEl;
}

function _(id){
   return document.getElementById(id);
}

function returnFalse() {
    return false;
}

function drag_start(event) {
    currDiv = event.target;
    event.dataTransfer.dropEffect = "move";
    event.dataTransfer.setData("text", event.target.textContent );
}

function drag_drop(event) {
    event.preventDefault();
    event.target.appendChild(currDiv);
    currDiv.removeAttribute("draggable");
    currDiv.style.cursor = "default";
    droppedIn = true;
}

function drag_end(event) {
	droppedIn = false;
}
function readDropZone(){
    let parent = _(this.getAttribute('drop_zone'));
    for(var i=0; i < parent.children.length; i++){
        alert(parent.children[i].textContent+" is in the drop zone");
    }
}
function clearDropZone(){
    let parent = _(this.getAttribute('drop_zone'));
	for(var i=0; i < parent.children.length; i++){
		let delObj = parent.children[0];
		if(parent.firstChild){
			delObj.parentNode.removeChild(delObj);
		}
	}
}

