import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEntraineur } from 'app/shared/model/entraineur.model';

type EntityResponseType = HttpResponse<IEntraineur>;
type EntityArrayResponseType = HttpResponse<IEntraineur[]>;

@Injectable({ providedIn: 'root' })
export class EntraineurService {
    public resourceUrl = SERVER_API_URL + 'api/entraineurs';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/entraineurs';

    constructor(protected http: HttpClient) {}

    create(entraineur: IEntraineur): Observable<EntityResponseType> {
        return this.http.post<IEntraineur>(this.resourceUrl, entraineur, { observe: 'response' });
    }

    update(entraineur: IEntraineur): Observable<EntityResponseType> {
        return this.http.put<IEntraineur>(this.resourceUrl, entraineur, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEntraineur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEntraineur[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEntraineur[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
