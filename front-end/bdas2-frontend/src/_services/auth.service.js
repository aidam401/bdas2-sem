import axios from 'axios';
import {API_URL} from "@/_helpers";


class AuthService {
    login (user) {
        var params = {};
        if (user?.LOGIN.includes("@")) {
             var parts = user?.LOGIN.split("@");
             params.name = parts[0];
             params.anotherUser = parts[1];
        }
        else
        {
            params.name = user.LOGIN;
        }
        params.password = user.HESLO;
        return axios.get(
            API_URL + 'login', {
                params,
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
        return axios.post(API_URL + 'signup',
            {
                LOGIN: user.LOGIN,
                HESLO: user.HESLO
            });
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
