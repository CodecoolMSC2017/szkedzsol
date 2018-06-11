function onLogoutResponse() {
    if (this.status === OK) {
        setUnauthorized();
        clearMessages();
        hideMenuContent();
        showContents(['login-content'])
    } else {
        onOtherResponse(logoutContentDivEl, this);
    }
}

function onLogoutButtonClicked(event) {
    clearMessages();
    const xhr = new XMLHttpRequest();
    signOut();
    xhr.addEventListener('load', onLogoutResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/logout');
    xhr.send();
}

  function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
  }

