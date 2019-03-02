import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Examen } from 'app/shared/model/examen.model';
import { ExamenService } from './examen.service';
import { ExamenComponent } from './examen.component';
import { ExamenDetailComponent } from './examen-detail.component';
import { ExamenUpdateComponent } from './examen-update.component';
import { ExamenDeletePopupComponent } from './examen-delete-dialog.component';
import { IExamen } from 'app/shared/model/examen.model';

@Injectable({ providedIn: 'root' })
export class ExamenResolve implements Resolve<IExamen> {
    constructor(private service: ExamenService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExamen> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Examen>) => response.ok),
                map((examen: HttpResponse<Examen>) => examen.body)
            );
        }
        return of(new Examen());
    }
}

export const examenRoute: Routes = [
    {
        path: '',
        component: ExamenComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'autoEcoleApp.examen.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ExamenDetailComponent,
        resolve: {
            examen: ExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleApp.examen.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ExamenUpdateComponent,
        resolve: {
            examen: ExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleApp.examen.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ExamenUpdateComponent,
        resolve: {
            examen: ExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleApp.examen.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const examenPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ExamenDeletePopupComponent,
        resolve: {
            examen: ExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleApp.examen.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
