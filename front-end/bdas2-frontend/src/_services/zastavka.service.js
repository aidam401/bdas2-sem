import BaseEntityService from "@/_services/base.entity.service";

class ZastavkaService extends BaseEntityService {
    constructor() {
        super('zastavka');
    }
}

export default new ZastavkaService();
