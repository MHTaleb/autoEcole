import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEcole } from 'app/shared/model/ecole.model';
import { EcoleService } from './ecole.service';
import { IEntraineur } from 'app/shared/model/entraineur.model';
import { EntraineurService } from 'app/entities/entraineur';

@Component({
    selector: 'jhi-ecole-update',
    templateUrl: './ecole-update.component.html'
})
export class EcoleUpdateComponent implements OnInit {
    ecole: IEcole;
    isSaving: boolean;

    entraineurs: IEntraineur[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected ecoleService: EcoleService,
        protected entraineurService: EntraineurService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ecole }) => {
            this.ecole = ecole;
        });
        this.entraineurService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEntraineur[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEntraineur[]>) => response.body)
            )
            .subscribe((res: IEntraineur[]) => (this.entraineurs = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackEntraineurById(index: number, item: IEntraineur) {
        return item.id;
    }
}
