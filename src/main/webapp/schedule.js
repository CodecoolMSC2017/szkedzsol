let tasksListTableEl;
let dropTableEl;


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
            showContents(['schedule-tasks-content', 'back-to-profile-content', 'logout-content']);
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

function createDropTableBody(){
    const scheduleFormEl = document.forms['how-many'];
    const nameInputEl = scheduleFormEl.querySelector('input[name="howMany"]');
    const number = nameInputEl.value;
    const tbodyEl = document.createElement('tbody');
    let counter = 0;
    while(counter < 3){
        const dropTrEl = document.createElement('tr');
        const dropTdEl = document.createElement('td');

        const dropDivEl = document.createElement('div');
        dropDivEl.id = "drop_zone";
        dropDivEl.setAttribute('ondragenter', 'drag_enter(event)');
        dropDivEl.setAttribute('ondrop', 'drag_drop(event)');
        dropDivEl.setAttribute('ondragover', 'return false');
        dropDivEl.setAttribute('ondragleave', 'drag_leave(event)');

        dropTdEl.appendChild(dropDivEl);
        dropTrEl.appendChild(dropTdEl);
        tbodyEl.appendChild(dropTrEl);
        counter++;
    }
    return tbodyEl;
}

function createDropZone(){
    const tableEl = document.createElement('table');
    tableEl.style.cssFloat = 'right';

    const divEl = document.getElementById('task-list-drop');

    divEl.appendChild(tableEl);
    divEl.appendChild(createDropTableBody());
    return tableEl;
}

function _(id){
   return document.getElementById(id);
}
var droppedIn = false;
let currDiv;
function drag_start(event) {
    currDiv = event.target;
    event.dataTransfer.dropEffect = "move";
    event.dataTransfer.setData("text", event.target.textContent );
}

function drag_drop(event) {
    event.preventDefault(); /* Prevent undesirable default behavior while dropping */
    var elem_id = event.dataTransfer.getData("text");
    event.target.appendChild(currDiv);
    currDiv.removeAttribute("draggable");
    currDiv.style.cursor = "default";
    droppedIn = true;
}
function drag_end(event) {
    if(droppedIn == false){
        _('app_status') = "You let the "+event.target.textContent+" go.";
    }
	droppedIn = false;
}
function readDropZone(){
    for(var i=0; i < _("drop_zone").children.length; i++){
        alert(_("drop_zone").children[i].textContent+" is in the drop zone");
    }
}
function clearDropZone(){
	let parent = _("drop_zone");
	for(var i=0; i < _("drop_zone").children.length; i++){
		let delObj =  _("drop_zone").children[0];
		if(parent.firstChild){
			delObj.parentNode.removeChild(delObj);
		}
	}
}

