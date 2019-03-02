import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiDataUtils } from 'ng-jhipster';
import { ICandidat } from 'app/shared/model/candidat.model';
import { CandidatService } from './candidat.service';

@Component({
    selector: 'jhi-candidat-update',
    templateUrl: './candidat-update.component.html'
})
export class CandidatUpdateComponent implements OnInit {
    candidat: ICandidat;
    isSaving: boolean;
    dateInscriptionDp: any;
    dateNaissanceDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected candidatService: CandidatService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ candidat }) => {
            this.candidat = candidat;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.candidat, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.candidat.id !== undefined) {
            this.subscribeToSaveResponse(this.candidatService.update(this.candidat));
        } else {
            this.subscribeToSaveResponse(this.candidatService.create(this.candidat));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidat>>) {
        result.subscribe((res: HttpResponse<ICandidat>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
