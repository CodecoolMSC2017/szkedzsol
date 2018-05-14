function onLoginResponse() {
    if (this.status === OK) {
        const user = JSON.parse(this.responseText);
        setAuthorization(user);
        onProfileLoad(user);
    } else {
        onOtherResponse(loginContentDivEl, this);
    }
}

function onLoginButtonClicked() {
    const loginFormEl = document.forms['login-form'];

    const emailInputEl = loginFormEl.querySelector('input[name="email"]');
    const passwordInputEl = loginFormEl.querySelector('input[name="name"]');

    const email = emailInputEl.value;
    const password = passwordInputEl.value;

    const params = new URLSearchParams();
    params.append('email', email);
    params.append('name', name);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLoginResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'login');
    xhr.send(params);
}
