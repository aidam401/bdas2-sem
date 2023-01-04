import BaseEntityService from "@/_services/base.entity.service";

class VozidlaService extends BaseEntityService {
    constructor() {
        super('Vozidlo');
    }
}

export default new VozidlaService();