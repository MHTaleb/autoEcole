import { Moment } from 'moment';

export const enum TypeLesson {
    CODE = 'CODE',
    CRENO = 'CRENO',
    CIRCULATION = 'CIRCULATION',
    AUTRE = 'AUTRE'
}

export const enum EtatLesson {
    PLANIFIER = 'PLANIFIER',
    VALIDER = 'VALIDER',
    ANNULER = 'ANNULER',
    ABSENT = 'ABSENT'
}

export interface ILesson {
    id?: number;
    typeLesson?: TypeLesson;
    dateLesson?: Moment;
    etatLesson?: EtatLesson;
    observation?: any;
    candidatId?: number;
    voitureMarque?: string;
    voitureId?: number;
    entraineurNom?: string;
    entraineurId?: number;
}

export class Lesson implements ILesson {
    constructor(
        public id?: number,
        public typeLesson?: TypeLesson,
        public dateLesson?: Moment,
        public etatLesson?: EtatLesson,
        public observation?: any,
        public candidatId?: number,
        public voitureMarque?: string,
        public voitureId?: number,
        public entraineurNom?: string,
        public entraineurId?: number
    ) {}
}
