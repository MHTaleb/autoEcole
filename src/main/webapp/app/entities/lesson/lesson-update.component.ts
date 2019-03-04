import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ILesson } from 'app/shared/model/lesson.model';
import { LessonService } from './lesson.service';
import { ICandidat } from 'app/shared/model/candidat.model';
import { CandidatService } from 'app/entities/candidat';
import { ICar } from 'app/shared/model/car.model';
import { CarService } from 'app/entities/car';
import { IEntraineur } from 'app/shared/model/entraineur.model';
import { EntraineurService } from 'app/entities/entraineur';

@Component({
    selector: 'jhi-lesson-update',
    templateUrl: './lesson-update.component.html'
})
export class LessonUpdateComponent implements OnInit {
    lesson: ILesson;
    isSaving: boolean;

    candidats: ICandidat[];

    cars: ICar[];

    entraineurs: IEntraineur[];
    dateLessonDp: any;
    heurLesson: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected lessonService: LessonService,
        protected candidatService: CandidatService,
        protected carService: CarService,
        protected entraineurService: EntraineurService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lesson }) => {
            this.lesson = lesson;
            this.heurLesson = this.lesson.heurLesson != null ? this.lesson.heurLesson.format(DATE_TIME_FORMAT) : null;
        });
        this.candidatService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICandidat[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICandidat[]>) => response.body)
            )
            .subscribe((res: ICandidat[]) => (this.candidats = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.carService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICar[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICar[]>) => response.body)
            )
            .subscribe((res: ICar[]) => (this.cars = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.lesson.heurLesson = this.heurLesson != null ? moment(this.heurLesson, DATE_TIME_FORMAT) : null;
        if (this.lesson.id !== undefined) {
            this.subscribeToSaveResponse(this.lessonService.update(this.lesson));
        } else {
            this.subscribeToSaveResponse(this.lessonService.create(this.lesson));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILesson>>) {
        result.subscribe((res: HttpResponse<ILesson>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCandidatById(index: number, item: ICandidat) {
        return item.id;
    }

    trackCarById(index: number, item: ICar) {
        return item.id;
    }

    trackEntraineurById(index: number, item: IEntraineur) {
        return item.id;
    }
}
