function onRegisterResponse() {
    if (this.status === OK) {
        clearMessages();
        showContents(['login-content']);
    } else {
        onOtherResponse(registerContentDivEl, this);
    }
}

function onRegisterButtonClick() {
    const registerFormEl = document.forms['register-form'];

    const emailInputEl = registerFormEl.querySelector('input[name="email"]');
    const nameInputEl = registerFormEl.querySelector('input[name="name"]');

    const email = emailInputEl.value;
    const name = nameInputEl.value;

    const params = new URLSearchParams();
    params.append('email', email);
    params.append('name', name);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onRegisterResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'register');
    xhr.send(params);
}