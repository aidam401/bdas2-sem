import BaseEntityService from "@/_services/base.entity.service";

class ZastavkaLinkaService extends BaseEntityService {
    constructor() {
        super('ZASTAVKA_LINKA');
    }
}

export default new ZastavkaLinkaService();