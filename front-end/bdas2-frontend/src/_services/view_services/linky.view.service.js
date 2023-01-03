import BaseEntityService from "@/_services/base.entity.service";
import axios from "axios";
import {API_URL, authBasic} from "@/_helpers";

class LinkyViewService extends BaseEntityService {
    constructor() {
        super('Zastavka_linka_view');
    }

    getLinkaDetail(id) {
        return axios.get(API_URL + 'linkyDetail', {
            params: {
                id: id
            },
            headers: {
                'Authorization': authBasic(),
                'Access-Control-Allow-Origin': '*'
            }
        });
    }
}

export default new LinkyViewService();