import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICandidat } from 'app/shared/model/candidat.model';

@Component({
    selector: 'jhi-candidat-detail',
    templateUrl: './candidat-detail.component.html'
})
export class CandidatDetailComponent implements OnInit {
    candidat: ICandidat;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ candidat }) => {
            this.candidat = candidat;
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
