import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AutoEcoleV01SharedModule } from 'app/shared';
import {
    EntraineurComponent,
    EntraineurDetailComponent,
    EntraineurUpdateComponent,
    EntraineurDeletePopupComponent,
    EntraineurDeleteDialogComponent,
    entraineurRoute,
    entraineurPopupRoute
} from './';

const ENTITY_STATES = [...entraineurRoute, ...entraineurPopupRoute];

@NgModule({
    imports: [AutoEcoleV01SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EntraineurComponent,
        EntraineurDetailComponent,
        EntraineurUpdateComponent,
        EntraineurDeleteDialogComponent,
        EntraineurDeletePopupComponent
    ],
    entryComponents: [EntraineurComponent, EntraineurUpdateComponent, EntraineurDeleteDialogComponent, EntraineurDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoEcoleV01EntraineurModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
