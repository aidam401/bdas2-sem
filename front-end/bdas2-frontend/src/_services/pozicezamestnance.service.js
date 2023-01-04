import BaseEntityService from "@/_services/base.entity.service";

class PoziceZamestnanceService extends BaseEntityService {
    constructor() {
        super('POZICE_ZAMESTNANCE');
    }
}

export default new PoziceZamestnanceService();