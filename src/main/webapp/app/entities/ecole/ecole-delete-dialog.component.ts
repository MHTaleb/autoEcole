import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEcole } from 'app/shared/model/ecole.model';
import { EcoleService } from './ecole.service';

@Component({
    selector: 'jhi-ecole-delete-dialog',
    templateUrl: './ecole-delete-dialog.component.html'
})
export class EcoleDeleteDialogComponent {
    ecole: IEcole;

    constructor(protected ecoleService: EcoleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ecoleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'ecoleListModification',
                content: 'Deleted an ecole'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ecole-delete-popup',
    template: ''
})
export class EcoleDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ecole }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EcoleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.ecole = ecole;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/ecole', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/ecole', { outlets: { popup: null } }]);
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
