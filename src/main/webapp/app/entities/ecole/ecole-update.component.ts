import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IEcole } from 'app/shared/model/ecole.model';
import { EcoleService } from './ecole.service';

@Component({
    selector: 'jhi-ecole-update',
    templateUrl: './ecole-update.component.html'
})
export class EcoleUpdateComponent implements OnInit {
    ecole: IEcole;
    isSaving: boolean;

    constructor(protected ecoleService: EcoleService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ecole }) => {
            this.ecole = ecole;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ecole.id !== undefined) {
            this.subscribeToSaveResponse(this.ecoleService.update(this.ecole));
        } else {
            this.subscribeToSaveResponse(this.ecoleService.create(this.ecole));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEcole>>) {
        result.subscribe((res: HttpResponse<IEcole>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
