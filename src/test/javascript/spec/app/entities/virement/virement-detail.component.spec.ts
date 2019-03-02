/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AutoEcoleTestModule } from '../../../test.module';
import { VirementDetailComponent } from 'app/entities/virement/virement-detail.component';
import { Virement } from 'app/shared/model/virement.model';

describe('Component Tests', () => {
    describe('Virement Management Detail Component', () => {
        let comp: VirementDetailComponent;
        let fixture: ComponentFixture<VirementDetailComponent>;
        const route = ({ data: of({ virement: new Virement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleTestModule],
                declarations: [VirementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VirementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VirementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.virement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
