import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExamenInfo } from 'app/shared/model/examen-info.model';
import { ExamenInfoService } from './examen-info.service';

@Component({
    selector: 'jhi-examen-info-delete-dialog',
    templateUrl: './examen-info-delete-dialog.component.html'
})
export class ExamenInfoDeleteDialogComponent {
    examenInfo: IExamenInfo;

    constructor(
        protected examenInfoService: ExamenInfoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.examenInfoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'examenInfoListModification',
                content: 'Deleted an examenInfo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-examen-info-delete-popup',
    template: ''
})
export class ExamenInfoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ examenInfo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExamenInfoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.examenInfo = examenInfo;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/examen-info', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/examen-info', { outlets: { popup: null } }]);
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
