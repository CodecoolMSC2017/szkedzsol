function onSchedulesClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSchedulesResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedules');
    xhr.send();
}

function onTasksClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTasksResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/tasks');
    xhr.send();
}

function onProfileLoad() {
    clearMessages();

    const userEmailSpandEl = document.getElementById('user-email');
    const userNameSpanEl = document.getElementById('user-name');

    userEmailSpandEl.textContent = getAuthorization().email;
    userNameSpanEl.textContent = getAuthorization().name;
    showContents(['profile-content']);
}
