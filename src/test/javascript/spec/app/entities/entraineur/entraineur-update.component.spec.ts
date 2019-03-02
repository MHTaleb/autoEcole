/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AutoEcoleTestModule } from '../../../test.module';
import { EntraineurUpdateComponent } from 'app/entities/entraineur/entraineur-update.component';
import { EntraineurService } from 'app/entities/entraineur/entraineur.service';
import { Entraineur } from 'app/shared/model/entraineur.model';

describe('Component Tests', () => {
    describe('Entraineur Management Update Component', () => {
        let comp: EntraineurUpdateComponent;
        let fixture: ComponentFixture<EntraineurUpdateComponent>;
        let service: EntraineurService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleTestModule],
                declarations: [EntraineurUpdateComponent]
            })
                .overrideTemplate(EntraineurUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EntraineurUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntraineurService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Entraineur(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.entraineur = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Entraineur();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.entraineur = entity;
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
