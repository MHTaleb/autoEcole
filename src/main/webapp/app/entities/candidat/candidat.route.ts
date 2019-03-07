import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Candidat } from 'app/shared/model/candidat.model';
import { CandidatService } from './candidat.service';
import { CandidatComponent } from './candidat.component';
import { CandidatDetailComponent } from './candidat-detail.component';
import { CandidatUpdateComponent } from './candidat-update.component';
import { CandidatDeletePopupComponent } from './candidat-delete-dialog.component';
import { ICandidat } from 'app/shared/model/candidat.model';

@Injectable({ providedIn: 'root' })
export class CandidatResolve implements Resolve<ICandidat> {
    constructor(private service: CandidatService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICandidat> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Candidat>) => response.ok),
                map((candidat: HttpResponse<Candidat>) => candidat.body)
            );
        }
        return of(new Candidat());
    }
}

export const candidatRoute: Routes = [
    {
        path: '',
        component: CandidatComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'autoEcoleV01App.candidat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CandidatDetailComponent,
        resolve: {
            candidat: CandidatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleV01App.candidat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CandidatUpdateComponent,
        resolve: {
            candidat: CandidatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleV01App.candidat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CandidatUpdateComponent,
        resolve: {
            candidat: CandidatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleV01App.candidat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const candidatPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CandidatDeletePopupComponent,
        resolve: {
            candidat: CandidatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleV01App.candidat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
