import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExamenInfo } from 'app/shared/model/examen-info.model';

type EntityResponseType = HttpResponse<IExamenInfo>;
type EntityArrayResponseType = HttpResponse<IExamenInfo[]>;

@Injectable({ providedIn: 'root' })
export class ExamenInfoService {
    public resourceUrl = SERVER_API_URL + 'api/examen-infos';

    constructor(protected http: HttpClient) {}

    create(examenInfo: IExamenInfo): Observable<EntityResponseType> {
        return this.http.post<IExamenInfo>(this.resourceUrl, examenInfo, { observe: 'response' });
    }

    update(examenInfo: IExamenInfo): Observable<EntityResponseType> {
        return this.http.put<IExamenInfo>(this.resourceUrl, examenInfo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IExamenInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IExamenInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
