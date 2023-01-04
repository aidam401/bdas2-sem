import BaseEntityService from "@/_services/base.entity.service";

class VozidlaService extends BaseEntityService {
    constructor() {
        super('vozidlo');
    }
}

export default new VozidlaService();