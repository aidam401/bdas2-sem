import BaseEntityService from "@/_services/base.entity.service";

class VozidloViewService extends BaseEntityService {
    constructor() {
        super('VOZIDLO_VIEW');
    }
}

export default new VozidloViewService();