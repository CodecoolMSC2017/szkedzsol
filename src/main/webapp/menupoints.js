
function createMenu() {
    const divEl = document.getElementById("menu");
    const ulEl = document.createElement('ul');
    const liEl = document.createElement('li');
    const liEl2 = document.createElement('li');
    const liEl3 = document.createElement('li');
    const liEl5 = document.createElement('li');
    liEl5.setAttribute("style", "float:right");
    let aEl = document.createElement('a');
    let bEl = document.createElement('a');
    let cEl = document.createElement('a');
    let eEl = document.createElement('a');
    eEl.classList.add("active");
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
    eEl.textContent = "Logout";
    eEl.href = 'javascript:void(0)';
    eEl.addEventListener('click', onLogoutButtonClicked);


    if (role == 'ADMIN') {
        const liEl4 = document.createElement('li');
        const dEl = document.createElement('a');
        dEl.textContent = 'Registered Users';
        dEl.href = 'javascript:void(0)';
        dEl.addEventListener('click', onUsersClicked);
        liEl.appendChild(aEl);
        liEl2.appendChild(bEl)
        liEl3.appendChild(cEl);
        liEl4.appendChild(dEl);
        liEl5.appendChild(eEl);
        ulEl.appendChild(liEl);
        ulEl.appendChild(liEl2);
        ulEl.appendChild(liEl3);
        ulEl.appendChild(liEl4);
        ulEl.appendChild(liEl5);
        divEl.appendChild(ulEl);
    } else {
        liEl.appendChild(aEl);
        liEl2.appendChild(bEl)
        liEl3.appendChild(cEl);
        liEl5.appendChild(eEl);
        ulEl.appendChild(liEl);
        ulEl.appendChild(liEl2);
        ulEl.appendChild(liEl3);
        ulEl.appendChild(liEl5);
        divEl.appendChild(ulEl);
    }
}
