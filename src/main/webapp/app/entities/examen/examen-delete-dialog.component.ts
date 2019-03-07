import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExamen } from 'app/shared/model/examen.model';
import { ExamenService } from './examen.service';

@Component({
    selector: 'jhi-examen-delete-dialog',
    templateUrl: './examen-delete-dialog.component.html'
})
export class ExamenDeleteDialogComponent {
    examen: IExamen;

    constructor(protected examenService: ExamenService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.examenService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'examenListModification',
                content: 'Deleted an examen'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-examen-delete-popup',
    template: ''
})
export class ExamenDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ examen }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExamenDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.examen = examen;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/examen', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/examen', { outlets: { popup: null } }]);
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
