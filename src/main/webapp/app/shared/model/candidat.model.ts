import { Moment } from 'moment';
import { IExamenInfo } from 'app/shared/model/examen-info.model';
import { IVirement } from 'app/shared/model/virement.model';
import { ILesson } from 'app/shared/model/lesson.model';

export const enum Nationalite {
    ALGERIE = 'ALGERIE',
    MARROC = 'MARROC',
    TUNISIE = 'TUNISIE',
    FRANCE = 'FRANCE',
    AUTRE = 'AUTRE'
}

export interface ICandidat {
    id?: number;
    photoContentType?: string;
    photo?: any;
    nom?: string;
    prenom?: string;
    pere?: string;
    mere?: string;
    telephone?: string;
    dateInscription?: Moment;
    dateNaissance?: Moment;
    lieuNaissance?: string;
    nationalite?: Nationalite;
    adresse?: string;
    examenInfos?: IExamenInfo[];
    virements?: IVirement[];
    lessons?: ILesson[];
}

export class Candidat implements ICandidat {
    constructor(
        public id?: number,
        public photoContentType?: string,
        public photo?: any,
        public nom?: string,
        public prenom?: string,
        public pere?: string,
        public mere?: string,
        public telephone?: string,
        public dateInscription?: Moment,
        public dateNaissance?: Moment,
        public lieuNaissance?: string,
        public nationalite?: Nationalite,
        public adresse?: string,
        public examenInfos?: IExamenInfo[],
        public virements?: IVirement[],
        public lessons?: ILesson[]
    ) {}
}
