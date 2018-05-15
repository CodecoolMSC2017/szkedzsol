let couponId;



function createTask(task) {
    const liEl = document.createElement('li');
    liEl.textContent = `${task.id} - ${task.name} - ${task.description}`;
    return liEl;
}

function showTaskContent(tasks) {
    const couponShopsSpanEl = document.getElementById('coupon-shops');

    removeAllChildren(couponShopsSpanEl);

    if (shops.length === 0) {
        couponShopsSpanEl.textContent = 'No associated shops';
    } else {
        const ulEl = document.createElement('ul');
        for (let i= 0; i < shops.length; i++) {
            const shop = shops[i];
            ulEl.appendChild(createTask(task));
        }
        couponShopsSpanEl.appendChild(ulEl);
    }
}

function addAllShops(shops) {
    const selectEl = document.querySelector('#coupon-shops-form > select');

    removeAllChildren(selectEl);

    for (let i= 0; i < shops.length; i++) {
        const shop = shops[i];

        const optionEl = document.createElement('option');
        optionEl.value = shop.id;
        optionEl.textContent = `${shop.id} - ${shop.name}`;

        selectEl.appendChild(optionEl);
    }
}

function onTaskLoad(task) {
    const taskIdSpandEl = document.getElementById('task-id');
    const taskNameSpanEl = document.getElementById('task-name');
    const taskDescriptionSpanEl = document.getElementById('task-description');

    taskIdSpandEl.textContent = task.id;
    taskNameSpanEl.textContent = task.name
    taskDescriptionSpanEl.textContent = task.description;
}

function onTaskResponse() {
    if (this.status === OK) {
        clearMessages();
        showContents(['task-content', 'back-to-profile-content', 'logout-content']);
        onTaskLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(couponsContentDivEl, this);
    }
}
