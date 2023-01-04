import BaseEntityService from "@/_services/base.entity.service";

class ZamestnanecService extends BaseEntityService {
    constructor() {
        super('zamestnance');
    }
}

export default new ZamestnanecService();