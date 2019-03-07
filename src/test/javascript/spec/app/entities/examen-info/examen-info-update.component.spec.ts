/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AutoEcoleV01TestModule } from '../../../test.module';
import { ExamenInfoUpdateComponent } from 'app/entities/examen-info/examen-info-update.component';
import { ExamenInfoService } from 'app/entities/examen-info/examen-info.service';
import { ExamenInfo } from 'app/shared/model/examen-info.model';

describe('Component Tests', () => {
    describe('ExamenInfo Management Update Component', () => {
        let comp: ExamenInfoUpdateComponent;
        let fixture: ComponentFixture<ExamenInfoUpdateComponent>;
        let service: ExamenInfoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleV01TestModule],
                declarations: [ExamenInfoUpdateComponent]
            })
                .overrideTemplate(ExamenInfoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExamenInfoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExamenInfoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ExamenInfo(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.examenInfo = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ExamenInfo();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.examenInfo = entity;
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
