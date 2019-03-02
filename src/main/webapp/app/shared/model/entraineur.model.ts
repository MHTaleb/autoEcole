import { ILesson } from 'app/shared/model/lesson.model';

export interface IEntraineur {
    id?: number;
    photoContentType?: string;
    photo?: any;
    nom?: string;
    prenom?: string;
    telephone?: string;
    compteLogin?: string;
    compteId?: number;
    lessons?: ILesson[];
}

export class Entraineur implements IEntraineur {
    constructor(
        public id?: number,
        public photoContentType?: string,
        public photo?: any,
        public nom?: string,
        public prenom?: string,
        public telephone?: string,
        public compteLogin?: string,
        public compteId?: number,
        public lessons?: ILesson[]
    ) {}
}
