import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth/'; //TODO upravit URL na BE

class AuthService {
    login (user) {
        return axios.post(
            API_URL + 'login', {
                username: user.username,
                password: user.password
            },{
                headers: { 'Content-Type': 'application/json' },
            }).then(this.handleResponse)
            .then( dataUser => {
                if (dataUser) {
                    dataUser.authdata = window.btoa(username + ':' + password);
                    localStorage.setItem('user', JSON.stringify(dataUser));
                }

                return dataUser;
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

    handleResponse (response) {
        return response.text().then(text => {
            const data = text && JSON.parse(text);
            if (!response.ok) {
                if (response.status === 401) {
                    this.logout();
                    location.reload();
                }

                const error = (data && data.message) || response.statusText;
                return Promise.reject(error);
            }

            return data;
        })
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