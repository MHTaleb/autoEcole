/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AutoEcoleTestModule } from '../../../test.module';
import { ExaminateurDetailComponent } from 'app/entities/examinateur/examinateur-detail.component';
import { Examinateur } from 'app/shared/model/examinateur.model';

describe('Component Tests', () => {
    describe('Examinateur Management Detail Component', () => {
        let comp: ExaminateurDetailComponent;
        let fixture: ComponentFixture<ExaminateurDetailComponent>;
        const route = ({ data: of({ examinateur: new Examinateur(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleTestModule],
                declarations: [ExaminateurDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ExaminateurDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExaminateurDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.examinateur).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
