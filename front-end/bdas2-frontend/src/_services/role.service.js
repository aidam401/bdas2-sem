import BaseEntityService from "@/_services/base.entity.service";

class RoleService extends BaseEntityService {
    constructor() {
        super('Role');
    }
}

export default new RoleService();