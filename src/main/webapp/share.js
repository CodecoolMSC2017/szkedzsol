

function onScheduleShareClicked() {
    const currentId = document.getElementById('schedule-id').textContent;
    const params = new URLSearchParams();
    params.append('currentId', currentId);

    const xhr = new XMLHttpRequest();
  //  xhr.addEventListener('load', shareTasks, false);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'share' + "?id=" + currentId);
    xhr.send();
}
