import BaseEntityService from "@/_services/base.entity.service";

class ZastavkaLinkaService extends BaseEntityService {
    constructor() {
        super('zastavkalinka');
    }
}

export default new ZastavkaLinkaService();