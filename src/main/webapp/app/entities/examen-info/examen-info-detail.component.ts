import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExamenInfo } from 'app/shared/model/examen-info.model';

@Component({
    selector: 'jhi-examen-info-detail',
    templateUrl: './examen-info-detail.component.html'
})
export class ExamenInfoDetailComponent implements OnInit {
    examenInfo: IExamenInfo;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ examenInfo }) => {
            this.examenInfo = examenInfo;
        });
    }

    previousState() {
        window.history.back();
    }
}
