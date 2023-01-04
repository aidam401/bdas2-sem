import BaseEntityService from "@/_services/base.entity.service";

class UserService extends BaseEntityService {
    constructor() {
        super('Uzivatel');
    }
}

export default new UserService();