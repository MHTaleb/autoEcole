import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AutoEcoleSharedModule } from 'app/shared';
import {
    VirementComponent,
    VirementDetailComponent,
    VirementUpdateComponent,
    VirementDeletePopupComponent,
    VirementDeleteDialogComponent,
    virementRoute,
    virementPopupRoute
} from './';

const ENTITY_STATES = [...virementRoute, ...virementPopupRoute];

@NgModule({
    imports: [AutoEcoleSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VirementComponent,
        VirementDetailComponent,
        VirementUpdateComponent,
        VirementDeleteDialogComponent,
        VirementDeletePopupComponent
    ],
    entryComponents: [VirementComponent, VirementUpdateComponent, VirementDeleteDialogComponent, VirementDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoEcoleVirementModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
