import axios from 'axios';
import {API_URL, authBasic} from "@/_helpers";

export default class BaseEntityService {

    constructor(table) {
        this.table = table;
    }

    getAll(limit, offset) {
        return axios.get(API_URL + this.table + '/read', {
            headers: {
                'Authorization': authBasic(),
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Headers': '*',
                'Access-Control-Allow-Methods': '*',
                'Access-Control-Allow-Credentials': true,}
        }).catch(e => console.log(e));
    }

    getById(id) {
        return axios.get(API_URL + this.table + '/read',
            {
                params: {id: id},
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': authBasic(),
                    "Access-Control-Allow-Origin": "*"
                }
            }).catch(e => console.log(e));
    }

    createEntity(data) {
        return axios.post(API_URL + this.table + '/create', data, {
            headers: { 'Authorization': authBasic(), "Access-Control-Allow-Origin": "*"}
        }).catch(e => console.log(e));
    }

    updateEntity(id, data) {
        return axios.post(API_URL + this.table + '/update', data, {
            params: {id: id},
            headers: { 'Authorization': authBasic(), "Access-Control-Allow-Origin": "*"}
        }).catch(e => console.log(e));
    }

    deleteEntity(id) {
        return axios.delete(API_URL + this.table + '/delete', {
            params: {id: id},
            headers: { 'Authorization': authBasic(), "Access-Control-Allow-Origin": "*"}
        }).catch(e => console.log(e));
    }
}