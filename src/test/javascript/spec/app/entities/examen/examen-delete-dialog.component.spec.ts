/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AutoEcoleV01TestModule } from '../../../test.module';
import { ExamenDeleteDialogComponent } from 'app/entities/examen/examen-delete-dialog.component';
import { ExamenService } from 'app/entities/examen/examen.service';

describe('Component Tests', () => {
    describe('Examen Management Delete Component', () => {
        let comp: ExamenDeleteDialogComponent;
        let fixture: ComponentFixture<ExamenDeleteDialogComponent>;
        let service: ExamenService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleV01TestModule],
                declarations: [ExamenDeleteDialogComponent]
            })
                .overrideTemplate(ExamenDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExamenDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExamenService);
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
