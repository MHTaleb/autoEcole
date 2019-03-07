import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExaminateur } from 'app/shared/model/examinateur.model';

@Component({
    selector: 'jhi-examinateur-detail',
    templateUrl: './examinateur-detail.component.html'
})
export class ExaminateurDetailComponent implements OnInit {
    examinateur: IExaminateur;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ examinateur }) => {
            this.examinateur = examinateur;
        });
    }

    previousState() {
        window.history.back();
    }
}
