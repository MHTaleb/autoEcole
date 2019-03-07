import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILesson } from 'app/shared/model/lesson.model';

type EntityResponseType = HttpResponse<ILesson>;
type EntityArrayResponseType = HttpResponse<ILesson[]>;

@Injectable({ providedIn: 'root' })
export class LessonService {
    public resourceUrl = SERVER_API_URL + 'api/lessons';

    constructor(protected http: HttpClient) {}

    create(lesson: ILesson): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(lesson);
        return this.http
            .post<ILesson>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(lesson: ILesson): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(lesson);
        return this.http
            .put<ILesson>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILesson>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILesson[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(lesson: ILesson): ILesson {
        const copy: ILesson = Object.assign({}, lesson, {
            dateLesson: lesson.dateLesson != null && lesson.dateLesson.isValid() ? lesson.dateLesson.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateLesson = res.body.dateLesson != null ? moment(res.body.dateLesson) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((lesson: ILesson) => {
                lesson.dateLesson = lesson.dateLesson != null ? moment(lesson.dateLesson) : null;
            });
        }
        return res;
    }
}
