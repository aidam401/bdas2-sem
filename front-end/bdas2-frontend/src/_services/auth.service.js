import axios from 'axios';

const API_URL = 'http://localhost:8080/';

class AuthService {
    login (user) {
        return axios.get(
            API_URL + 'login', {
                params: {
                    name: user.username,
                    password: user.password
                },
                headers: { 'Content-Type': 'application/json'}
            }).then(response => {
                if (response?.data) {
                    const userStore = {
                        username: user.username,
                        password: user.password,
                        authData: window.btoa(user.username + ':' + user.password)
                    }
                    localStorage.setItem('user', JSON.stringify(userStore));
                    return Promise.resolve();
                }
                return Promise.reject("Nesprávné heslo nebo login!");
            }).catch((e) => {
                return Promise.reject(e);
            })
    }

    register(user) {
        return axios.post(API_URL + 'signup', {
            username: user.username, // TODO přidat file uživatele
            password: user.password
        });
    }

    logout () {
        localStorage.removeItem('user');
    }
}

export default new AuthService();


/* TODO EXAMPLE OF REQUIRED ENDPOINT
function getAll() {
    const requestOptions = {
        method: 'GET',
        headers: authHeader()
    };

    return fetch(`${config.apiUrl}/users`, requestOptions).then(handleResponse);
}
 */