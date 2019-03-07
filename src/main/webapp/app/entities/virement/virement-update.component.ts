import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IVirement } from 'app/shared/model/virement.model';
import { VirementService } from './virement.service';
import { ICandidat } from 'app/shared/model/candidat.model';
import { CandidatService } from 'app/entities/candidat';

@Component({
    selector: 'jhi-virement-update',
    templateUrl: './virement-update.component.html'
})
export class VirementUpdateComponent implements OnInit {
    virement: IVirement;
    isSaving: boolean;

    candidats: ICandidat[];
    dateVirement: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected virementService: VirementService,
        protected candidatService: CandidatService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ virement }) => {
            this.virement = virement;
            this.dateVirement = this.virement.dateVirement != null ? this.virement.dateVirement.format(DATE_TIME_FORMAT) : null;
        });
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
        this.virement.dateVirement = this.dateVirement != null ? moment(this.dateVirement, DATE_TIME_FORMAT) : null;
        if (this.virement.id !== undefined) {
            this.subscribeToSaveResponse(this.virementService.update(this.virement));
        } else {
            this.subscribeToSaveResponse(this.virementService.create(this.virement));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IVirement>>) {
        result.subscribe((res: HttpResponse<IVirement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
