import BaseEntityService from "@/_services/base.entity.service";

class ZastavkaService extends BaseEntityService {
    constructor() {
        super('Zastavka');
    }
}

export default new ZastavkaService();
