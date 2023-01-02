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
                if (response?.data[0]) {
                    const userStore = {...response.data[1]};
                    userStore.authData = window.btoa(userStore.LOGIN + ':' + userStore.HESLO);
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
