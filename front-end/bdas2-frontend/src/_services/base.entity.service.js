import axios from 'axios';
import {API_URL, authBasic} from "@/_helpers";

export default class BaseEntityService {

    constructor(table) {
        this.table = table;
    }

    getAll(limit, offset) {
        return axios.get(API_URL + 'all' + this.table, {
            headers: {
                'Authorization': authBasic(),
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Headers': '*',
                'Access-Control-Allow-Methods': '*',
                'Access-Control-Allow-Credentials': true,}
        });
    }

    getById(id) {
        return axios.get(API_URL + 'detail' + this.table,
            {
                params: {id: id},
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': authBasic(),
                    "Access-Control-Allow-Origin": "*"
                }
            });
    }

    createEntity(data) {
        return axios.post(API_URL + 'create' + this.table, data, {
            headers: { 'Authorization': authBasic(), "Access-Control-Allow-Origin": "*"}
        });
    }

    updateEntity(data) {
        console.log(data);
        return axios.post(API_URL + 'update' + this.table, data, {
            headers: { 'Authorization': authBasic(), "Access-Control-Allow-Origin": "*"}
        });
    }
}