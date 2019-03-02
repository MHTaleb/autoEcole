/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AutoEcoleTestModule } from '../../../test.module';
import { EcoleDeleteDialogComponent } from 'app/entities/ecole/ecole-delete-dialog.component';
import { EcoleService } from 'app/entities/ecole/ecole.service';

describe('Component Tests', () => {
    describe('Ecole Management Delete Component', () => {
        let comp: EcoleDeleteDialogComponent;
        let fixture: ComponentFixture<EcoleDeleteDialogComponent>;
        let service: EcoleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleTestModule],
                declarations: [EcoleDeleteDialogComponent]
            })
                .overrideTemplate(EcoleDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EcoleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EcoleService);
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
