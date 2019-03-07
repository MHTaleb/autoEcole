import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ExamenInfo } from 'app/shared/model/examen-info.model';
import { ExamenInfoService } from './examen-info.service';
import { ExamenInfoComponent } from './examen-info.component';
import { ExamenInfoDetailComponent } from './examen-info-detail.component';
import { ExamenInfoUpdateComponent } from './examen-info-update.component';
import { ExamenInfoDeletePopupComponent } from './examen-info-delete-dialog.component';
import { IExamenInfo } from 'app/shared/model/examen-info.model';

@Injectable({ providedIn: 'root' })
export class ExamenInfoResolve implements Resolve<IExamenInfo> {
    constructor(private service: ExamenInfoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExamenInfo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ExamenInfo>) => response.ok),
                map((examenInfo: HttpResponse<ExamenInfo>) => examenInfo.body)
            );
        }
        return of(new ExamenInfo());
    }
}

export const examenInfoRoute: Routes = [
    {
        path: '',
        component: ExamenInfoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'autoEcoleV01App.examenInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ExamenInfoDetailComponent,
        resolve: {
            examenInfo: ExamenInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleV01App.examenInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ExamenInfoUpdateComponent,
        resolve: {
            examenInfo: ExamenInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleV01App.examenInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ExamenInfoUpdateComponent,
        resolve: {
            examenInfo: ExamenInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleV01App.examenInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const examenInfoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ExamenInfoDeletePopupComponent,
        resolve: {
            examenInfo: ExamenInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleV01App.examenInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
