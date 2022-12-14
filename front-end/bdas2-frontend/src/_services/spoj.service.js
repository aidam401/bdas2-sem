import BaseEntityService from "@/_services/base.entity.service";
import axios from "axios";
import {API_URL, authBasic} from "@/_helpers";

class SpojService extends BaseEntityService {
    constructor() {
        super('spoj');
    }

    getSpoj(id) {
        return axios.get(API_URL + this.table + 'e', {
            params: {
                id: id
            },
            headers: {
                'Authorization': authBasic(),
                'Access-Control-Allow-Origin': '*',}
        }).catch(e => console.log(e));
    }

    getZastavkySpoje(id) {
        return axios.get(API_URL + this.table + 'eDetail', {
            params: {
                id: id
            },
            headers: {
                'Authorization': authBasic(),
                'Access-Control-Allow-Origin': '*',}
        }).catch(e => console.log(e));
    }
}

export default new SpojService();