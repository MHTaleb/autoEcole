/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AutoEcoleV01TestModule } from '../../../test.module';
import { LessonDetailComponent } from 'app/entities/lesson/lesson-detail.component';
import { Lesson } from 'app/shared/model/lesson.model';

describe('Component Tests', () => {
    describe('Lesson Management Detail Component', () => {
        let comp: LessonDetailComponent;
        let fixture: ComponentFixture<LessonDetailComponent>;
        const route = ({ data: of({ lesson: new Lesson(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AutoEcoleV01TestModule],
                declarations: [LessonDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LessonDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LessonDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.lesson).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
