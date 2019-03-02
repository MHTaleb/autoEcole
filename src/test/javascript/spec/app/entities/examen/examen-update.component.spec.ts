/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AutoEcoleTestModule } from '../../../test.module';
import { ExamenUpdateComponent } from 'app/entities/examen/examen-update.component';
import { ExamenService } from 'app/entities/examen/examen.service';
import { Examen } from 'app/shared/model/examen.model';

describe('Component Tests', () => {
    describe('Examen Management Update Component', () => {
        let comp: ExamenUpdateComponent;
        let fixture: ComponentFixture<ExamenUpdateComponent>;
        let service: ExamenService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleTestModule],
                declarations: [ExamenUpdateComponent]
            })
                .overrideTemplate(ExamenUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExamenUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExamenService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Examen(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.examen = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Examen();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.examen = entity;
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
