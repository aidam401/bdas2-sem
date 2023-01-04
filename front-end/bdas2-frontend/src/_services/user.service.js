import BaseEntityService from "@/_services/base.entity.service";

class UserService extends BaseEntityService {
    constructor() {
        super('uzivatel');
    }
}

export default new UserService();