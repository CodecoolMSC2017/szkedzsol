function onLoginResponse() {
    if (this.status === OK) {
        const user = JSON.parse(this.responseText);
        setAuthorization(user);
        createMenu();
        onWelcomeLoad();

    } else {
        onOtherResponse(loginContentDivEl, this);
    }
}

function onLoginButtonClicked() {
    const loginFormEl = document.forms['login-form'];

    const emailInputEl = loginFormEl.querySelector('input[name="email"]');
    const nameInputEl = loginFormEl.querySelector('input[name="name"]');

    const email = emailInputEl.value;
    const name = nameInputEl.value;

    const params = new URLSearchParams();
    params.append('email', email);
    params.append('name', name);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLoginResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'login');
    xhr.send(params);
}

function toRegisterButtonClick() {
    clearMessages();
    showContents(['register-content']);
}

function onSignIn(googleUser) {
    const id_token = googleUser.getAuthResponse().id_token;
    const usrPic = googleUser.w3.Paa;
    const pic = document.createElement("img");
    const picDivEl = document.getElementById("profilePicture");
    removeAllChildren(picDivEl);
    picDivEl.appendChild(pic);
    pic.src = usrPic;

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onGoogleLoginResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'googlelogin');
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send('idtoken=' + id_token);
}

function onGoogleLoginResponse() {
    if (this.status === OK) {
        const user = JSON.parse(this.responseText);
        setAuthorization(user);
        createMenu();
        onWelcomeLoad();

    } else {
        onOtherResponse(loginGoogleContentDivEl, this);
    }
}

