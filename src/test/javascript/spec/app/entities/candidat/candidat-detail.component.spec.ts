/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AutoEcoleV01TestModule } from '../../../test.module';
import { CandidatDetailComponent } from 'app/entities/candidat/candidat-detail.component';
import { Candidat } from 'app/shared/model/candidat.model';

describe('Component Tests', () => {
    describe('Candidat Management Detail Component', () => {
        let comp: CandidatDetailComponent;
        let fixture: ComponentFixture<CandidatDetailComponent>;
        const route = ({ data: of({ candidat: new Candidat(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleV01TestModule],
                declarations: [CandidatDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CandidatDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CandidatDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.candidat).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
