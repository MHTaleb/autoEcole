import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICandidat } from 'app/shared/model/candidat.model';
import { CandidatService } from './candidat.service';

@Component({
    selector: 'jhi-candidat-delete-dialog',
    templateUrl: './candidat-delete-dialog.component.html'
})
export class CandidatDeleteDialogComponent {
    candidat: ICandidat;

    constructor(protected candidatService: CandidatService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.candidatService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'candidatListModification',
                content: 'Deleted an candidat'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-candidat-delete-popup',
    template: ''
})
export class CandidatDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ candidat }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CandidatDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.candidat = candidat;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/candidat', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/candidat', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
