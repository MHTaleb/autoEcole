import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Examinateur } from 'app/shared/model/examinateur.model';
import { ExaminateurService } from './examinateur.service';
import { ExaminateurComponent } from './examinateur.component';
import { ExaminateurDetailComponent } from './examinateur-detail.component';
import { ExaminateurUpdateComponent } from './examinateur-update.component';
import { ExaminateurDeletePopupComponent } from './examinateur-delete-dialog.component';
import { IExaminateur } from 'app/shared/model/examinateur.model';

@Injectable({ providedIn: 'root' })
export class ExaminateurResolve implements Resolve<IExaminateur> {
    constructor(private service: ExaminateurService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExaminateur> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Examinateur>) => response.ok),
                map((examinateur: HttpResponse<Examinateur>) => examinateur.body)
            );
        }
        return of(new Examinateur());
    }
}

export const examinateurRoute: Routes = [
    {
        path: '',
        component: ExaminateurComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'autoEcoleV01App.examinateur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ExaminateurDetailComponent,
        resolve: {
            examinateur: ExaminateurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleV01App.examinateur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ExaminateurUpdateComponent,
        resolve: {
            examinateur: ExaminateurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleV01App.examinateur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ExaminateurUpdateComponent,
        resolve: {
            examinateur: ExaminateurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleV01App.examinateur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const examinateurPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ExaminateurDeletePopupComponent,
        resolve: {
            examinateur: ExaminateurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleV01App.examinateur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
