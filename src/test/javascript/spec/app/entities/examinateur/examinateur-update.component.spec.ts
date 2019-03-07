/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AutoEcoleV01TestModule } from '../../../test.module';
import { ExaminateurUpdateComponent } from 'app/entities/examinateur/examinateur-update.component';
import { ExaminateurService } from 'app/entities/examinateur/examinateur.service';
import { Examinateur } from 'app/shared/model/examinateur.model';

describe('Component Tests', () => {
    describe('Examinateur Management Update Component', () => {
        let comp: ExaminateurUpdateComponent;
        let fixture: ComponentFixture<ExaminateurUpdateComponent>;
        let service: ExaminateurService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleV01TestModule],
                declarations: [ExaminateurUpdateComponent]
            })
                .overrideTemplate(ExaminateurUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExaminateurUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExaminateurService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Examinateur(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.examinateur = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Examinateur();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.examinateur = entity;
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
