/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AutoEcoleV01TestModule } from '../../../test.module';
import { ExaminateurDeleteDialogComponent } from 'app/entities/examinateur/examinateur-delete-dialog.component';
import { ExaminateurService } from 'app/entities/examinateur/examinateur.service';

describe('Component Tests', () => {
    describe('Examinateur Management Delete Component', () => {
        let comp: ExaminateurDeleteDialogComponent;
        let fixture: ComponentFixture<ExaminateurDeleteDialogComponent>;
        let service: ExaminateurService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleV01TestModule],
                declarations: [ExaminateurDeleteDialogComponent]
            })
                .overrideTemplate(ExaminateurDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExaminateurDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExaminateurService);
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
