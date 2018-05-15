let couponId;

function onCouponShopsAddResponse() {
    clearMessages();
    if (this.status === OK) {
        onCouponLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(couponContentDivEl, this);
    }
}

function onCouponShopsAddClicked() {
    const couponShopsForm = document.forms['coupon-shops-form'];

    const shopIdsSelectEl = couponShopsForm.querySelector('select[name="shopIds"]');

    const params = new URLSearchParams();
    params.append('id', couponId);
    for (let i = 0; i < shopIdsSelectEl.selectedOptions.length; i++) {
        params.append('shopIds', shopIdsSelectEl.selectedOptions[i].value);
    }

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onCouponShopsAddResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/coupon');
    xhr.send(params);
}

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

    addCouponShops(couponDto.couponShops);
    addAllShops(couponDto.allShops);
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
