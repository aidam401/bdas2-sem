import axios from 'axios';
import {API_URL} from "@/_helpers";

class UserService {
    getAll() {
        return axios.get(API_URL + '')
    }
}