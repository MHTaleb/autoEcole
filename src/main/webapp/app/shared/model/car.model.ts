import { IExamen } from 'app/shared/model/examen.model';
import { ILesson } from 'app/shared/model/lesson.model';

export interface ICar {
    id?: number;
    matricule?: string;
    marque?: string;
    examen?: IExamen[];
    lessons?: ILesson[];
}

export class Car implements ICar {
    constructor(
        public id?: number,
        public matricule?: string,
        public marque?: string,
        public examen?: IExamen[],
        public lessons?: ILesson[]
    ) {}
}
