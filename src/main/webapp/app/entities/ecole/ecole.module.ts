import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AutoEcoleSharedModule } from 'app/shared';
import {
    EcoleComponent,
    EcoleDetailComponent,
    EcoleUpdateComponent,
    EcoleDeletePopupComponent,
    EcoleDeleteDialogComponent,
    ecoleRoute,
    ecolePopupRoute
} from './';

const ENTITY_STATES = [...ecoleRoute, ...ecolePopupRoute];

@NgModule({
    imports: [AutoEcoleSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [EcoleComponent, EcoleDetailComponent, EcoleUpdateComponent, EcoleDeleteDialogComponent, EcoleDeletePopupComponent],
    entryComponents: [EcoleComponent, EcoleUpdateComponent, EcoleDeleteDialogComponent, EcoleDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoEcoleEcoleModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
