import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVirement } from 'app/shared/model/virement.model';

type EntityResponseType = HttpResponse<IVirement>;
type EntityArrayResponseType = HttpResponse<IVirement[]>;

@Injectable({ providedIn: 'root' })
export class VirementService {
    public resourceUrl = SERVER_API_URL + 'api/virements';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/virements';

    constructor(protected http: HttpClient) {}

    create(virement: IVirement): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(virement);
        return this.http
            .post<IVirement>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(virement: IVirement): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(virement);
        return this.http
            .put<IVirement>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IVirement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IVirement[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IVirement[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(virement: IVirement): IVirement {
        const copy: IVirement = Object.assign({}, virement, {
            dateVirement:
                virement.dateVirement != null && virement.dateVirement.isValid() ? virement.dateVirement.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateVirement = res.body.dateVirement != null ? moment(res.body.dateVirement) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((virement: IVirement) => {
                virement.dateVirement = virement.dateVirement != null ? moment(virement.dateVirement) : null;
            });
        }
        return res;
    }
}
