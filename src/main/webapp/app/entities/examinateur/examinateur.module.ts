import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AutoEcoleV01SharedModule } from 'app/shared';
import {
    ExaminateurComponent,
    ExaminateurDetailComponent,
    ExaminateurUpdateComponent,
    ExaminateurDeletePopupComponent,
    ExaminateurDeleteDialogComponent,
    examinateurRoute,
    examinateurPopupRoute
} from './';

const ENTITY_STATES = [...examinateurRoute, ...examinateurPopupRoute];

@NgModule({
    imports: [AutoEcoleV01SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ExaminateurComponent,
        ExaminateurDetailComponent,
        ExaminateurUpdateComponent,
        ExaminateurDeleteDialogComponent,
        ExaminateurDeletePopupComponent
    ],
    entryComponents: [ExaminateurComponent, ExaminateurUpdateComponent, ExaminateurDeleteDialogComponent, ExaminateurDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoEcoleV01ExaminateurModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
