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
    heurLesson?: Moment;
    etatLesson?: EtatLesson;
    observation?: any;
    candidatId?: number;
    voitureId?: number;
    entraineurId?: number;
}

export class Lesson implements ILesson {
    constructor(
        public id?: number,
        public typeLesson?: TypeLesson,
        public dateLesson?: Moment,
        public heurLesson?: Moment,
        public etatLesson?: EtatLesson,
        public observation?: any,
        public candidatId?: number,
        public voitureId?: number,
        public entraineurId?: number
    ) {}
}
