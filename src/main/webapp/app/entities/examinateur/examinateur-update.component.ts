import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IExaminateur } from 'app/shared/model/examinateur.model';
import { ExaminateurService } from './examinateur.service';

@Component({
    selector: 'jhi-examinateur-update',
    templateUrl: './examinateur-update.component.html'
})
export class ExaminateurUpdateComponent implements OnInit {
    examinateur: IExaminateur;
    isSaving: boolean;

    constructor(protected examinateurService: ExaminateurService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ examinateur }) => {
            this.examinateur = examinateur;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.examinateur.id !== undefined) {
            this.subscribeToSaveResponse(this.examinateurService.update(this.examinateur));
        } else {
            this.subscribeToSaveResponse(this.examinateurService.create(this.examinateur));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExaminateur>>) {
        result.subscribe((res: HttpResponse<IExaminateur>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
