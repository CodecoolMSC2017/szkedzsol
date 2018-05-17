let usersTableEl;
let usersTableBodyEl;

function onUsersClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onUsersResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/users');
    xhr.send();
}

function onUsersAddResponse() {
    clearMessages();
    if (this.status === OK) {
        appendUser(JSON.parse(this.responseText));
    } else {
        onOtherResponse(usersContentDivEl, this);
    }
}

function appendUser(user) {
        const idTdEl = document.createElement('td');
        idTdEl.textContent = user.id;

        const nameTdEl = document.createElement('td');
        nameTdEl.textContent = user.name;

        const emailTdEl = document.createElement('td');
        emailTdEl.setAttribute('class','zoom');        
        emailTdEl.textContent = user.email;

        const roleTdEl = document.createElement('td');
        roleTdEl.textContent = user.role;


        const trEl = document.createElement('tr');
        trEl.appendChild(idTdEl);
        trEl.appendChild(nameTdEl);
        trEl.appendChild(emailTdEl);
        trEl.appendChild(roleTdEl);
        usersTableBodyEl.appendChild(trEl);
}

function appendUsers(users) {
    removeAllChildren(usersTableBodyEl);

    for (let i = 0; i < users.length; i++) {
        const user = users[i];
        appendUser(user);
    }
}

function onUsersLoad(users) {
    usersTableEl = document.getElementById('users');
    usersTableBodyEl = usersTableEl.querySelector('tbody');

    appendUsers(users);
}

function onUsersResponse() {
    if (this.status === OK) {
        showContents(['users-content', 'logout-content']);
        onUsersLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(tasksContentDivEl, this);
    }
}
