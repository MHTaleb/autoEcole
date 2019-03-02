/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AutoEcoleTestModule } from '../../../test.module';
import { ExamenInfoDeleteDialogComponent } from 'app/entities/examen-info/examen-info-delete-dialog.component';
import { ExamenInfoService } from 'app/entities/examen-info/examen-info.service';

describe('Component Tests', () => {
    describe('ExamenInfo Management Delete Component', () => {
        let comp: ExamenInfoDeleteDialogComponent;
        let fixture: ComponentFixture<ExamenInfoDeleteDialogComponent>;
        let service: ExamenInfoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleTestModule],
                declarations: [ExamenInfoDeleteDialogComponent]
            })
                .overrideTemplate(ExamenInfoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExamenInfoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExamenInfoService);
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
