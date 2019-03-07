import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExamen } from 'app/shared/model/examen.model';

@Component({
    selector: 'jhi-examen-detail',
    templateUrl: './examen-detail.component.html'
})
export class ExamenDetailComponent implements OnInit {
    examen: IExamen;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ examen }) => {
            this.examen = examen;
        });
    }

    previousState() {
        window.history.back();
    }
}
