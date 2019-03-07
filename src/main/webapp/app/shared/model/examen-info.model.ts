export const enum EtatExamen {
    ENCOURS = 'ENCOURS',
    REUSSI = 'REUSSI',
    PERDU = 'PERDU',
    ABSENT = 'ABSENT',
    INVALIDE = 'INVALIDE'
}

export const enum TypeExamen {
    CODE = 'CODE',
    CRENO = 'CRENO',
    CIRCULATION = 'CIRCULATION'
}

export interface IExamenInfo {
    id?: number;
    etat?: EtatExamen;
    type?: TypeExamen;
    examenDateExamen?: string;
    examenId?: number;
    candidatId?: number;
}

export class ExamenInfo implements IExamenInfo {
    constructor(
        public id?: number,
        public etat?: EtatExamen,
        public type?: TypeExamen,
        public examenDateExamen?: string,
        public examenId?: number,
        public candidatId?: number
    ) {}
}
