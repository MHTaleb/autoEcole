import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExaminateur } from 'app/shared/model/examinateur.model';
import { ExaminateurService } from './examinateur.service';

@Component({
    selector: 'jhi-examinateur-delete-dialog',
    templateUrl: './examinateur-delete-dialog.component.html'
})
export class ExaminateurDeleteDialogComponent {
    examinateur: IExaminateur;

    constructor(
        protected examinateurService: ExaminateurService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.examinateurService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'examinateurListModification',
                content: 'Deleted an examinateur'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-examinateur-delete-popup',
    template: ''
})
export class ExaminateurDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ examinateur }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExaminateurDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.examinateur = examinateur;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/examinateur', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/examinateur', { outlets: { popup: null } }]);
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
