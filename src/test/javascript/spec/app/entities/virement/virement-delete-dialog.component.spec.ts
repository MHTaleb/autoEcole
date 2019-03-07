/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AutoEcoleV01TestModule } from '../../../test.module';
import { VirementDeleteDialogComponent } from 'app/entities/virement/virement-delete-dialog.component';
import { VirementService } from 'app/entities/virement/virement.service';

describe('Component Tests', () => {
    describe('Virement Management Delete Component', () => {
        let comp: VirementDeleteDialogComponent;
        let fixture: ComponentFixture<VirementDeleteDialogComponent>;
        let service: VirementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleV01TestModule],
                declarations: [VirementDeleteDialogComponent]
            })
                .overrideTemplate(VirementDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VirementDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VirementService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
