/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AutoEcoleTestModule } from '../../../test.module';
import { ExamenDetailComponent } from 'app/entities/examen/examen-detail.component';
import { Examen } from 'app/shared/model/examen.model';

describe('Component Tests', () => {
    describe('Examen Management Detail Component', () => {
        let comp: ExamenDetailComponent;
        let fixture: ComponentFixture<ExamenDetailComponent>;
        const route = ({ data: of({ examen: new Examen(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleTestModule],
                declarations: [ExamenDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ExamenDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExamenDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.examen).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
