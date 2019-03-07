/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AutoEcoleV01TestModule } from '../../../test.module';
import { VirementUpdateComponent } from 'app/entities/virement/virement-update.component';
import { VirementService } from 'app/entities/virement/virement.service';
import { Virement } from 'app/shared/model/virement.model';

describe('Component Tests', () => {
    describe('Virement Management Update Component', () => {
        let comp: VirementUpdateComponent;
        let fixture: ComponentFixture<VirementUpdateComponent>;
        let service: VirementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleV01TestModule],
                declarations: [VirementUpdateComponent]
            })
                .overrideTemplate(VirementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VirementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VirementService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Virement(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.virement = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Virement();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.virement = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
