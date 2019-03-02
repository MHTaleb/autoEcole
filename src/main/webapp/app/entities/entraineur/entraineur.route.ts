import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Entraineur } from 'app/shared/model/entraineur.model';
import { EntraineurService } from './entraineur.service';
import { EntraineurComponent } from './entraineur.component';
import { EntraineurDetailComponent } from './entraineur-detail.component';
import { EntraineurUpdateComponent } from './entraineur-update.component';
import { EntraineurDeletePopupComponent } from './entraineur-delete-dialog.component';
import { IEntraineur } from 'app/shared/model/entraineur.model';

@Injectable({ providedIn: 'root' })
export class EntraineurResolve implements Resolve<IEntraineur> {
    constructor(private service: EntraineurService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEntraineur> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Entraineur>) => response.ok),
                map((entraineur: HttpResponse<Entraineur>) => entraineur.body)
            );
        }
        return of(new Entraineur());
    }
}

export const entraineurRoute: Routes = [
    {
        path: '',
        component: EntraineurComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'autoEcoleApp.entraineur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EntraineurDetailComponent,
        resolve: {
            entraineur: EntraineurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleApp.entraineur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EntraineurUpdateComponent,
        resolve: {
            entraineur: EntraineurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleApp.entraineur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EntraineurUpdateComponent,
        resolve: {
            entraineur: EntraineurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleApp.entraineur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const entraineurPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EntraineurDeletePopupComponent,
        resolve: {
            entraineur: EntraineurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleApp.entraineur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
