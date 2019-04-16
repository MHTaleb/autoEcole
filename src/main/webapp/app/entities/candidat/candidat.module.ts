import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NgPipesModule } from 'ngx-pipes';

import { AutoEcoleV01SharedModule } from 'app/shared';
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
    imports: [AutoEcoleV01SharedModule, RouterModule.forChild(ENTITY_STATES), NgPipesModule],
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
export class AutoEcoleV01CandidatModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
