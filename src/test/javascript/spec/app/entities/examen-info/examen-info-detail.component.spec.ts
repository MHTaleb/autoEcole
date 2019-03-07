/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AutoEcoleV01TestModule } from '../../../test.module';
import { ExamenInfoDetailComponent } from 'app/entities/examen-info/examen-info-detail.component';
import { ExamenInfo } from 'app/shared/model/examen-info.model';

describe('Component Tests', () => {
    describe('ExamenInfo Management Detail Component', () => {
        let comp: ExamenInfoDetailComponent;
        let fixture: ComponentFixture<ExamenInfoDetailComponent>;
        const route = ({ data: of({ examenInfo: new ExamenInfo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleV01TestModule],
                declarations: [ExamenInfoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ExamenInfoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExamenInfoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.examenInfo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
