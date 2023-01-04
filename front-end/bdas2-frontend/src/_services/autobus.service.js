import BaseEntityService from "@/_services/base.entity.service";

class AutobusService extends BaseEntityService {
    constructor() {
        super('AUTOBUS');
    }
}

export default new AutobusService();