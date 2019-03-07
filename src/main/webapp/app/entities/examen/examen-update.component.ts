import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IExamen } from 'app/shared/model/examen.model';
import { ExamenService } from './examen.service';
import { ICar } from 'app/shared/model/car.model';
import { CarService } from 'app/entities/car';
import { IExaminateur } from 'app/shared/model/examinateur.model';
import { ExaminateurService } from 'app/entities/examinateur';

@Component({
    selector: 'jhi-examen-update',
    templateUrl: './examen-update.component.html'
})
export class ExamenUpdateComponent implements OnInit {
    examen: IExamen;
    isSaving: boolean;

    cars: ICar[];

    examinateurs: IExaminateur[];
    dateExamenDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected examenService: ExamenService,
        protected carService: CarService,
        protected examinateurService: ExaminateurService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ examen }) => {
            this.examen = examen;
        });
        this.carService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICar[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICar[]>) => response.body)
            )
            .subscribe((res: ICar[]) => (this.cars = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.examinateurService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IExaminateur[]>) => mayBeOk.ok),
                map((response: HttpResponse<IExaminateur[]>) => response.body)
            )
            .subscribe((res: IExaminateur[]) => (this.examinateurs = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.examen.id !== undefined) {
            this.subscribeToSaveResponse(this.examenService.update(this.examen));
        } else {
            this.subscribeToSaveResponse(this.examenService.create(this.examen));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExamen>>) {
        result.subscribe((res: HttpResponse<IExamen>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCarById(index: number, item: ICar) {
        return item.id;
    }

    trackExaminateurById(index: number, item: IExaminateur) {
        return item.id;
    }
}
