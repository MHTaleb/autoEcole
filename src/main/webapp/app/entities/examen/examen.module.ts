import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AutoEcoleV01SharedModule } from 'app/shared';
import {
    ExamenComponent,
    ExamenDetailComponent,
    ExamenUpdateComponent,
    ExamenDeletePopupComponent,
    ExamenDeleteDialogComponent,
    examenRoute,
    examenPopupRoute
} from './';

const ENTITY_STATES = [...examenRoute, ...examenPopupRoute];

@NgModule({
    imports: [AutoEcoleV01SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ExamenComponent, ExamenDetailComponent, ExamenUpdateComponent, ExamenDeleteDialogComponent, ExamenDeletePopupComponent],
    entryComponents: [ExamenComponent, ExamenUpdateComponent, ExamenDeleteDialogComponent, ExamenDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoEcoleV01ExamenModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
