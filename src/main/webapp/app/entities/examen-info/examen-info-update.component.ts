import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IExamenInfo } from 'app/shared/model/examen-info.model';
import { ExamenInfoService } from './examen-info.service';
import { IExamen } from 'app/shared/model/examen.model';
import { ExamenService } from 'app/entities/examen';
import { ICandidat } from 'app/shared/model/candidat.model';
import { CandidatService } from 'app/entities/candidat';

@Component({
    selector: 'jhi-examen-info-update',
    templateUrl: './examen-info-update.component.html'
})
export class ExamenInfoUpdateComponent implements OnInit {
    examenInfo: IExamenInfo;
    isSaving: boolean;

    examen: IExamen[];

    candidats: ICandidat[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected examenInfoService: ExamenInfoService,
        protected examenService: ExamenService,
        protected candidatService: CandidatService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ examenInfo }) => {
            this.examenInfo = examenInfo;
        });
        this.examenService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IExamen[]>) => mayBeOk.ok),
                map((response: HttpResponse<IExamen[]>) => response.body)
            )
            .subscribe((res: IExamen[]) => (this.examen = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.candidatService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICandidat[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICandidat[]>) => response.body)
            )
            .subscribe((res: ICandidat[]) => (this.candidats = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.examenInfo.id !== undefined) {
            this.subscribeToSaveResponse(this.examenInfoService.update(this.examenInfo));
        } else {
            this.subscribeToSaveResponse(this.examenInfoService.create(this.examenInfo));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExamenInfo>>) {
        result.subscribe((res: HttpResponse<IExamenInfo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackExamenById(index: number, item: IExamen) {
        return item.id;
    }

    trackCandidatById(index: number, item: ICandidat) {
        return item.id;
    }
}
