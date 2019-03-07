import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExaminateur } from 'app/shared/model/examinateur.model';

type EntityResponseType = HttpResponse<IExaminateur>;
type EntityArrayResponseType = HttpResponse<IExaminateur[]>;

@Injectable({ providedIn: 'root' })
export class ExaminateurService {
    public resourceUrl = SERVER_API_URL + 'api/examinateurs';

    constructor(protected http: HttpClient) {}

    create(examinateur: IExaminateur): Observable<EntityResponseType> {
        return this.http.post<IExaminateur>(this.resourceUrl, examinateur, { observe: 'response' });
    }

    update(examinateur: IExaminateur): Observable<EntityResponseType> {
        return this.http.put<IExaminateur>(this.resourceUrl, examinateur, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IExaminateur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IExaminateur[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
