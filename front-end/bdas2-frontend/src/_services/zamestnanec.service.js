import BaseEntityService from "@/_services/base.entity.service";

class ZamestnanecService extends BaseEntityService {
    constructor() {
        super('Zamestnanec');
    }
}

export default new ZamestnanecService();