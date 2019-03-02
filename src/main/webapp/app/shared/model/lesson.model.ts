import { Moment } from 'moment';

export const enum TypeLesson {
    CODE = 'CODE',
    CRENO = 'CRENO',
    CIRCULATION = 'CIRCULATION',
    AUTRE = 'AUTRE'
}

export interface ILesson {
    id?: number;
    typeLesson?: TypeLesson;
    dateLesson?: Moment;
    candidatId?: number;
    voitureId?: number;
    entraineurId?: number;
}

export class Lesson implements ILesson {
    constructor(
        public id?: number,
        public typeLesson?: TypeLesson,
        public dateLesson?: Moment,
        public candidatId?: number,
        public voitureId?: number,
        public entraineurId?: number
    ) {}
}
