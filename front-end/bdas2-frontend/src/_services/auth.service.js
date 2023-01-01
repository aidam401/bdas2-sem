import axios from 'axios';
import {API_URL} from "@/_helpers";


class AuthService {
    login (user) {
        return axios.get(
            API_URL + 'login', {
                params: {
                    name: user.LOGIN,
                    password: user.HESLO
                },
                headers: { 'Content-Type': 'application/json'}
            }).then(response => {
                if (response?.data) {
                    const userStore = {...user};
                    userStore.authData = window.btoa(user.LOGIN + ':' + user.HESLO);
                    localStorage.setItem('user', JSON.stringify(userStore));
                    return Promise.resolve(userStore);
                }
                return Promise.reject("Nesprávné heslo nebo login!");
            }).catch((e) => {
                return Promise.reject(e);
            })
    }

    register(user) {
        return axios.post(API_URL + 'signup', user);
    }

    logout () {
        localStorage.removeItem('user');
    }

    refreshAuthData (user) {
        const userStore = {...user};
        userStore.authData = window.btoa(user.LOGIN + ':' + user.HESLO);
        localStorage.setItem('user', JSON.stringify(userStore));
        return Promise.resolve(userStore);
    }
}

export default new AuthService();


/* TODO EXAMPLE OF REQUIRED ENDPOINT */
function getAll() {
    const requestOptions = {
        method: 'GET',
        headers: authHeader()
    };

    return fetch(`${config.apiUrl}/users`, requestOptions).then(handleResponse);
}