export interface IEcole {
    id?: number;
    titreEcole?: string;
    presidentNom?: string;
    presidentId?: number;
}

export class Ecole implements IEcole {
    constructor(public id?: number, public titreEcole?: string, public presidentNom?: string, public presidentId?: number) {}
}
