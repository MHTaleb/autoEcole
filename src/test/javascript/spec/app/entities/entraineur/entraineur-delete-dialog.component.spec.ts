/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AutoEcoleV01TestModule } from '../../../test.module';
import { EntraineurDeleteDialogComponent } from 'app/entities/entraineur/entraineur-delete-dialog.component';
import { EntraineurService } from 'app/entities/entraineur/entraineur.service';

describe('Component Tests', () => {
    describe('Entraineur Management Delete Component', () => {
        let comp: EntraineurDeleteDialogComponent;
        let fixture: ComponentFixture<EntraineurDeleteDialogComponent>;
        let service: EntraineurService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleV01TestModule],
                declarations: [EntraineurDeleteDialogComponent]
            })
                .overrideTemplate(EntraineurDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EntraineurDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntraineurService);
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
