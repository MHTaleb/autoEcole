import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEcole } from 'app/shared/model/ecole.model';

type EntityResponseType = HttpResponse<IEcole>;
type EntityArrayResponseType = HttpResponse<IEcole[]>;

@Injectable({ providedIn: 'root' })
export class EcoleService {
    public resourceUrl = SERVER_API_URL + 'api/ecoles';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/ecoles';

    constructor(protected http: HttpClient) {}

    create(ecole: IEcole): Observable<EntityResponseType> {
        return this.http.post<IEcole>(this.resourceUrl, ecole, { observe: 'response' });
    }

    update(ecole: IEcole): Observable<EntityResponseType> {
        return this.http.put<IEcole>(this.resourceUrl, ecole, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEcole>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEcole[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEcole[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
