
function createMenu(){
const divEl = document.getElementById("menu");
const ulEl = document.createElement('ul');
const liEl = document.createElement('li');
const liEl2 = document.createElement('li');
const liEl3 = document.createElement('li');
let aEl = document.createElement('a');
let bEl = document.createElement('a');
let cEl = document.createElement('a');
const role = getAuthorization().role;
aEl.textContent = 'Profile';
aEl.href = 'javascript:void(0)';
aEl.addEventListener('click', onProfileLoad);
bEl.textContent = 'Schedules'
bEl.href = 'javascript:void(0)';
bEl.addEventListener('click', onSchedulesClicked);
cEl.textContent = 'Tasks';
cEl.href = 'javascript:void(0)';
cEl.addEventListener('click', onTasksClicked);
if(role=='ADMIN'){
    const liEl4 = document.createElement('li');
    const dEl = document.createElement('a');
    dEl.textContent = 'Registered Users';
    dEl.href = 'javascript:void(0)';
    dEl.addEventListener('click', onUsersClicked);
    liEl.appendChild(aEl);
    liEl2.appendChild(bEl)
    liEl3.appendChild(cEl);
    liEl4.appendChild(dEl);
    ulEl.appendChild(liEl);
    ulEl.appendChild(liEl2);
    ulEl.appendChild(liEl3);
    ulEl.appendChild(liEl4);
    divEl.appendChild(ulEl);
}else{
    liEl.appendChild(aEl);
    liEl2.appendChild(bEl)
    liEl3.appendChild(cEl);
    ulEl.appendChild(liEl);
    ulEl.appendChild(liEl2);
    ulEl.appendChild(liEl3);
    divEl.appendChild(ulEl);
}
}
