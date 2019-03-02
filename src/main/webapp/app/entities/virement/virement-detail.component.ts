import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVirement } from 'app/shared/model/virement.model';

@Component({
    selector: 'jhi-virement-detail',
    templateUrl: './virement-detail.component.html'
})
export class VirementDetailComponent implements OnInit {
    virement: IVirement;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ virement }) => {
            this.virement = virement;
        });
    }

    previousState() {
        window.history.back();
    }
}
