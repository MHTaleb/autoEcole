/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AutoEcoleTestModule } from '../../../test.module';
import { EcoleUpdateComponent } from 'app/entities/ecole/ecole-update.component';
import { EcoleService } from 'app/entities/ecole/ecole.service';
import { Ecole } from 'app/shared/model/ecole.model';

describe('Component Tests', () => {
    describe('Ecole Management Update Component', () => {
        let comp: EcoleUpdateComponent;
        let fixture: ComponentFixture<EcoleUpdateComponent>;
        let service: EcoleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleTestModule],
                declarations: [EcoleUpdateComponent]
            })
                .overrideTemplate(EcoleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EcoleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EcoleService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Ecole(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.ecole = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Ecole();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.ecole = entity;
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
