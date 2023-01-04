import BaseEntityService from "@/_services/base.entity.service";

class SpojService extends BaseEntityService {
    constructor() {
        super('spoj');
    }
}

export default new SpojService();