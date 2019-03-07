import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExamen } from 'app/shared/model/examen.model';

type EntityResponseType = HttpResponse<IExamen>;
type EntityArrayResponseType = HttpResponse<IExamen[]>;

@Injectable({ providedIn: 'root' })
export class ExamenService {
    public resourceUrl = SERVER_API_URL + 'api/examen';

    constructor(protected http: HttpClient) {}

    create(examen: IExamen): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(examen);
        return this.http
            .post<IExamen>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(examen: IExamen): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(examen);
        return this.http
            .put<IExamen>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IExamen>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IExamen[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(examen: IExamen): IExamen {
        const copy: IExamen = Object.assign({}, examen, {
            dateExamen: examen.dateExamen != null && examen.dateExamen.isValid() ? examen.dateExamen.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateExamen = res.body.dateExamen != null ? moment(res.body.dateExamen) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((examen: IExamen) => {
                examen.dateExamen = examen.dateExamen != null ? moment(examen.dateExamen) : null;
            });
        }
        return res;
    }
}
