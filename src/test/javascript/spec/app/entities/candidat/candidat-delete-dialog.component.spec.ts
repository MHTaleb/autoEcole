/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AutoEcoleV01TestModule } from '../../../test.module';
import { CandidatDeleteDialogComponent } from 'app/entities/candidat/candidat-delete-dialog.component';
import { CandidatService } from 'app/entities/candidat/candidat.service';

describe('Component Tests', () => {
    describe('Candidat Management Delete Component', () => {
        let comp: CandidatDeleteDialogComponent;
        let fixture: ComponentFixture<CandidatDeleteDialogComponent>;
        let service: CandidatService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleV01TestModule],
                declarations: [CandidatDeleteDialogComponent]
            })
                .overrideTemplate(CandidatDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CandidatDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidatService);
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
