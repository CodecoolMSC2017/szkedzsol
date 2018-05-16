let usersTableEl;
let usersTableBodyEl;

function onUsersClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onUsersAddResponse);
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
    for (let i=0; i < user.length; i++) {
        let usr = user[i];
        const idTdEl = document.createElement('td');
        idTdEl.textContent = usr.id;

        const nameTdEl = document.createElement('td');
        nameTdEl.textContent = usr.name;

        const emailTdEl = document.createElement('td');
        emailTdEl.textContent = usr.email;

        const roleTdEl = document.createElement('td');
        roleTdEl.textContent = usr.role;


        const trEl = document.createElement('tr');
        trEl.appendChild(idTdEl);
        trEl.appendChild(nameTdEl);
        trEl.appendChild(emailTdEl);
        trEl.appendChild(roleTdEl);
        usersTableBodyEl.appendChild(trEl);
    }
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
        showContents(['users-content', 'back-to-profile-content', 'logout-content']);
        onUsersLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(tasksContentDivEl, this);
    }
}
