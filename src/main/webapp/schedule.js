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
