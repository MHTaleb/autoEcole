import { IExamen } from 'app/shared/model/examen.model';

export interface IExaminateur {
    id?: number;
    nom?: string;
    prenom?: string;
    telephone?: string;
    examen?: IExamen[];
}

export class Examinateur implements IExaminateur {
    constructor(public id?: number, public nom?: string, public prenom?: string, public telephone?: string, public examen?: IExamen[]) {}
}
