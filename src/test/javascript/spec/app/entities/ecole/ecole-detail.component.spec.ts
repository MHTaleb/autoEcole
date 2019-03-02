/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AutoEcoleTestModule } from '../../../test.module';
import { EcoleDetailComponent } from 'app/entities/ecole/ecole-detail.component';
import { Ecole } from 'app/shared/model/ecole.model';

describe('Component Tests', () => {
    describe('Ecole Management Detail Component', () => {
        let comp: EcoleDetailComponent;
        let fixture: ComponentFixture<EcoleDetailComponent>;
        const route = ({ data: of({ ecole: new Ecole(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleTestModule],
                declarations: [EcoleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EcoleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EcoleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.ecole).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
