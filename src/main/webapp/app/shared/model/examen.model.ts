import { Moment } from 'moment';
import { IExamenInfo } from 'app/shared/model/examen-info.model';

export interface IExamen {
    id?: number;
    dateExamen?: Moment;
    examenInfos?: IExamenInfo[];
    voitureId?: number;
    examinateurId?: number;
}

export class Examen implements IExamen {
    constructor(
        public id?: number,
        public dateExamen?: Moment,
        public examenInfos?: IExamenInfo[],
        public voitureId?: number,
        public examinateurId?: number
    ) {}
}
