import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEcole } from 'app/shared/model/ecole.model';

@Component({
    selector: 'jhi-ecole-detail',
    templateUrl: './ecole-detail.component.html'
})
export class EcoleDetailComponent implements OnInit {
    ecole: IEcole;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ecole }) => {
            this.ecole = ecole;
        });
    }

    previousState() {
        window.history.back();
    }
}
