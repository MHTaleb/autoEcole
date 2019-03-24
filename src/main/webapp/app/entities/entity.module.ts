import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'ecole',
                loadChildren: './ecole/ecole.module#AutoEcoleV01EcoleModule'
            },
            {
                path: 'lesson',
                loadChildren: './lesson/lesson.module#AutoEcoleV01LessonModule'
            },
            {
                path: 'car',
                loadChildren: './car/car.module#AutoEcoleV01CarModule'
            },
            {
                path: 'entraineur',
                loadChildren: './entraineur/entraineur.module#AutoEcoleV01EntraineurModule'
            },
            {
                path: 'candidat',
                loadChildren: './candidat/candidat.module#AutoEcoleV01CandidatModule'
            },
            {
                path: 'virement',
                loadChildren: './virement/virement.module#AutoEcoleV01VirementModule'
            },
            {
                path: 'examinateur',
                loadChildren: './examinateur/examinateur.module#AutoEcoleV01ExaminateurModule'
            },
            {
                path: 'examen',
                loadChildren: './examen/examen.module#AutoEcoleV01ExamenModule'
            },
            {
                path: 'examen-info',
                loadChildren: './examen-info/examen-info.module#AutoEcoleV01ExamenInfoModule'
            },
            {
                path: 'ecole',
                loadChildren: './ecole/ecole.module#AutoEcoleV01EcoleModule'
            },
            {
                path: 'ecole',
                loadChildren: './ecole/ecole.module#AutoEcoleV01EcoleModule'
            },
            {
                path: 'ecole',
                loadChildren: './ecole/ecole.module#AutoEcoleV01EcoleModule'
            },
            {
                path: 'ecole',
                loadChildren: './ecole/ecole.module#AutoEcoleV01EcoleModule'
            },
            {
                path: 'entraineur',
                loadChildren: './entraineur/entraineur.module#AutoEcoleV01EntraineurModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoEcoleV01EntityModule {}
