import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AutoEcoleSharedModule } from 'app/shared';
import {
    CandidatComponent,
    CandidatDetailComponent,
    CandidatUpdateComponent,
    CandidatDeletePopupComponent,
    CandidatDeleteDialogComponent,
    candidatRoute,
    candidatPopupRoute
} from './';

const ENTITY_STATES = [...candidatRoute, ...candidatPopupRoute];

@NgModule({
    imports: [AutoEcoleSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CandidatComponent,
        CandidatDetailComponent,
        CandidatUpdateComponent,
        CandidatDeleteDialogComponent,
        CandidatDeletePopupComponent
    ],
    entryComponents: [CandidatComponent, CandidatUpdateComponent, CandidatDeleteDialogComponent, CandidatDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoEcoleCandidatModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
