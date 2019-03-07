import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEntraineur } from 'app/shared/model/entraineur.model';

@Component({
    selector: 'jhi-entraineur-detail',
    templateUrl: './entraineur-detail.component.html'
})
export class EntraineurDetailComponent implements OnInit {
    entraineur: IEntraineur;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ entraineur }) => {
            this.entraineur = entraineur;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
