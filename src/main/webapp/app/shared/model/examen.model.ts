import { Moment } from 'moment';
import { IExamenInfo } from 'app/shared/model/examen-info.model';

export interface IExamen {
    id?: number;
    dateExamen?: Moment;
    examenInfos?: IExamenInfo[];
    voitureMarque?: string;
    voitureId?: number;
    examinateurNom?: string;
    examinateurId?: number;
}

export class Examen implements IExamen {
    constructor(
        public id?: number,
        public dateExamen?: Moment,
        public examenInfos?: IExamenInfo[],
        public voitureMarque?: string,
        public voitureId?: number,
        public examinateurNom?: string,
        public examinateurId?: number
    ) {}
}
