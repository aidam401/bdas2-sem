import BaseEntityService from "@/_services/base.entity.service";

class SpojService extends BaseEntityService {
    constructor() {
        super('SPOJ');
    }
}

export default new SpojService();