/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AutoEcoleTestModule } from '../../../test.module';
import { EntraineurDetailComponent } from 'app/entities/entraineur/entraineur-detail.component';
import { Entraineur } from 'app/shared/model/entraineur.model';

describe('Component Tests', () => {
    describe('Entraineur Management Detail Component', () => {
        let comp: EntraineurDetailComponent;
        let fixture: ComponentFixture<EntraineurDetailComponent>;
        const route = ({ data: of({ entraineur: new Entraineur(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleTestModule],
                declarations: [EntraineurDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EntraineurDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EntraineurDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.entraineur).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
