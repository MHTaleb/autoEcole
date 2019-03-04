import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'ecole',
                loadChildren: './ecole/ecole.module#AutoEcoleEcoleModule'
            },
            {
                path: 'lesson',
                loadChildren: './lesson/lesson.module#AutoEcoleLessonModule'
            },
            {
                path: 'car',
                loadChildren: './car/car.module#AutoEcoleCarModule'
            },
            {
                path: 'entraineur',
                loadChildren: './entraineur/entraineur.module#AutoEcoleEntraineurModule'
            },
            {
                path: 'candidat',
                loadChildren: './candidat/candidat.module#AutoEcoleCandidatModule'
            },
            {
                path: 'virement',
                loadChildren: './virement/virement.module#AutoEcoleVirementModule'
            },
            {
                path: 'examinateur',
                loadChildren: './examinateur/examinateur.module#AutoEcoleExaminateurModule'
            },
            {
                path: 'examen',
                loadChildren: './examen/examen.module#AutoEcoleExamenModule'
            },
            {
                path: 'examen-info',
                loadChildren: './examen-info/examen-info.module#AutoEcoleExamenInfoModule'
            },
            {
                path: 'entraineur',
                loadChildren: './entraineur/entraineur.module#AutoEcoleEntraineurModule'
            },
            {
                path: 'car',
                loadChildren: './car/car.module#AutoEcoleCarModule'
            },
            {
                path: 'examen',
                loadChildren: './examen/examen.module#AutoEcoleExamenModule'
            },
            {
                path: 'lesson',
                loadChildren: './lesson/lesson.module#AutoEcoleLessonModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoEcoleEntityModule {}
