import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AutoEcoleSharedModule } from 'app/shared';
import {
    ExamenInfoComponent,
    ExamenInfoDetailComponent,
    ExamenInfoUpdateComponent,
    ExamenInfoDeletePopupComponent,
    ExamenInfoDeleteDialogComponent,
    examenInfoRoute,
    examenInfoPopupRoute
} from './';

const ENTITY_STATES = [...examenInfoRoute, ...examenInfoPopupRoute];

@NgModule({
    imports: [AutoEcoleSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ExamenInfoComponent,
        ExamenInfoDetailComponent,
        ExamenInfoUpdateComponent,
        ExamenInfoDeleteDialogComponent,
        ExamenInfoDeletePopupComponent
    ],
    entryComponents: [ExamenInfoComponent, ExamenInfoUpdateComponent, ExamenInfoDeleteDialogComponent, ExamenInfoDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoEcoleExamenInfoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
