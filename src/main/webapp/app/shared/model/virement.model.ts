import { Moment } from 'moment';

export interface IVirement {
    id?: number;
    montant?: number;
    dateVirement?: Moment;
    candidatNom?: string;
    candidatId?: number;
}

export class Virement implements IVirement {
    constructor(
        public id?: number,
        public montant?: number,
        public dateVirement?: Moment,
        public candidatNom?: string,
        public candidatId?: number
    ) {}
}
