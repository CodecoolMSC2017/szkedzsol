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
        showContents(['schedule-content']);
        onScheduleLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this);
    }
}

function onHowManyResponse(){
    if (this.status === OK) {
            clearMessages();
            showContents(['schedule-tasks-content']);
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

    const getDataButtonEl = document.createElement('button');
    const clearButtonEl = document.createElement('button');

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
        //const getDataButtonEl = document.createElement('button');
        //getDataButtonEl.setAttribute('drop_zone', dropTableElId+(counter+1));
        //const clearButtonEl = document.createElement('button');
        //clearButtonEl.setAttribute('drop_zone', dropTableElId+(counter+2));



        dropTdEl.appendChild(dropDivEl);

        dropTrEl.appendChild(dropTdEl);
        tbodyEl.appendChild(dropTrEl);
        counter++;
        }

        getDataButtonEl.addEventListener('click', function() {
            readDropZone(dropTableElId);
        });
        getDataButtonEl.textContent = 'Get Data';
        clearButtonEl.addEventListener('click', function() {
            clearDropZone(dropTableElId);
        });
        clearButtonEl.textContent = 'Clear';

        tbodyEl.appendChild(getDataButtonEl);
        tbodyEl.appendChild(clearButtonEl);

    return tbodyEl;
}

function createDropTableHead(dropTableElId) {
    const theadEl = document.createElement('thead');
    const dropTableEl = document.getElementById('colTable');
    const dropTrEl = document.createElement('tr');
    const dropTdEl = document.createElement('td');

    const tnameEl = document.createElement('input');
    tnameEl.id = dropTableElId+'nameIn';
    tnameEl.className = "schedule-name-input";

    dropTdEl.appendChild(tnameEl);
    dropTrEl.appendChild(dropTdEl);
    theadEl.appendChild(dropTrEl);

    return theadEl;
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
    const dropTableHeadEl = createDropTableHead(dropTableElId);
    dropTableEl.appendChild(dropTableHeadEl);
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
function readDropZone(dropTableElId){
    let queryForAll;
    let queryForDropZone;
    switch(dropTableElId){
    case 'colTable0' :
        queryForAll = '[id^=colTable0]';
        break;
    case 'colTable1' :
        queryForAll = '[id^=colTable1]';
        break;
    case 'colTable2' :
        queryForAll = '[id^=colTable2]';
        break;
    case 'colTable3' :
        queryForAll = '[id^=colTable3]';
        break;
    case 'colTable4' :
        queryForAll = '[id^=colTable4]';
        break;
    case 'colTable5' :
        queryForAll = '[id^=colTable5]';
        break;
    case 'colTable6' :
        queryForAll = '[id^=colTable6]';
        break;
    }

    let bigParent = document.querySelectorAll(queryForAll);
    for(var j=0; j < bigParent.length; j++){
        for(var i=0; i < bigParent[j].children.length; i++){
            alert(bigParent[j].children[i].textContent+" is in the drop zone");
        }
    }
}
function clearDropZone(dropTableElId){
    let queryForAll;
    let queryForDropZone;
    switch(dropTableElId){
    case 'colTable0' :
        queryForAll = '[id^=colTable0]';
        break;
    case 'colTable1' :
        queryForAll = '[id^=colTable1]';
        break;
    case 'colTable2' :
        queryForAll = '[id^=colTable2]';
        break;
    case 'colTable3' :
        queryForAll = '[id^=colTable3]';
        break;
    case 'colTable4' :
        queryForAll = '[id^=colTable4]';
        break;
    case 'colTable5' :
        queryForAll = '[id^=colTable5]';
        break;
    case 'colTable6' :
        queryForAll = '[id^=colTable6]';
        break;
    }
    let bigParent = document.querySelectorAll(queryForAll);
    for(var j=0; j < bigParent.length; j++){
        for(var i=0; i < bigParent[j].children.length; i++){
		    let delObj = bigParent[j].children[0];
		    if(bigParent[j].firstChild){
			    delObj.parentNode.removeChild(delObj);
			}
		}
	}
}

