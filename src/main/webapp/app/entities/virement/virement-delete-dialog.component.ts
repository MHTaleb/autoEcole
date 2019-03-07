import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVirement } from 'app/shared/model/virement.model';
import { VirementService } from './virement.service';

@Component({
    selector: 'jhi-virement-delete-dialog',
    templateUrl: './virement-delete-dialog.component.html'
})
export class VirementDeleteDialogComponent {
    virement: IVirement;

    constructor(protected virementService: VirementService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.virementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'virementListModification',
                content: 'Deleted an virement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-virement-delete-popup',
    template: ''
})
export class VirementDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ virement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VirementDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.virement = virement;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/virement', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/virement', { outlets: { popup: null } }]);
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
