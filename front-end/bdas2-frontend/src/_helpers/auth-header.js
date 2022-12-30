export function authBasic() {
    let user = JSON.parse(localStorage.getItem('user'));

    if (user && user.authData) {
        return 'Basic ' + user.authData;
    } else {
        return '';
    }
}