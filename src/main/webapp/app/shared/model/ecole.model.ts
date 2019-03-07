export interface IEcole {
    id?: number;
    nomEcole?: string;
    president?: string;
}

export class Ecole implements IEcole {
    constructor(public id?: number, public nomEcole?: string, public president?: string) {}
}
