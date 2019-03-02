import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEntraineur } from 'app/shared/model/entraineur.model';
import { EntraineurService } from './entraineur.service';

@Component({
    selector: 'jhi-entraineur-delete-dialog',
    templateUrl: './entraineur-delete-dialog.component.html'
})
export class EntraineurDeleteDialogComponent {
    entraineur: IEntraineur;

    constructor(
        protected entraineurService: EntraineurService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.entraineurService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'entraineurListModification',
                content: 'Deleted an entraineur'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-entraineur-delete-popup',
    template: ''
})
export class EntraineurDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ entraineur }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EntraineurDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.entraineur = entraineur;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/entraineur', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/entraineur', { outlets: { popup: null } }]);
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
