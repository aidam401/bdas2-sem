import BaseEntityService from "@/_services/base.entity.service";

class RoleService extends BaseEntityService {
    constructor() {
        super('role');
    }
}

export default new RoleService();