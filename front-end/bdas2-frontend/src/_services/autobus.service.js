import BaseEntityService from "@/_services/base.entity.service";

class AutobusService extends BaseEntityService {
    constructor() {
        super('autobus');
    }
}

export default new AutobusService();