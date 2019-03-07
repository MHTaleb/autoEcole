import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Virement } from 'app/shared/model/virement.model';
import { VirementService } from './virement.service';
import { VirementComponent } from './virement.component';
import { VirementDetailComponent } from './virement-detail.component';
import { VirementUpdateComponent } from './virement-update.component';
import { VirementDeletePopupComponent } from './virement-delete-dialog.component';
import { IVirement } from 'app/shared/model/virement.model';

@Injectable({ providedIn: 'root' })
export class VirementResolve implements Resolve<IVirement> {
    constructor(private service: VirementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVirement> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Virement>) => response.ok),
                map((virement: HttpResponse<Virement>) => virement.body)
            );
        }
        return of(new Virement());
    }
}

export const virementRoute: Routes = [
    {
        path: '',
        component: VirementComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'autoEcoleV01App.virement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: VirementDetailComponent,
        resolve: {
            virement: VirementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleV01App.virement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: VirementUpdateComponent,
        resolve: {
            virement: VirementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleV01App.virement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: VirementUpdateComponent,
        resolve: {
            virement: VirementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleV01App.virement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const virementPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: VirementDeletePopupComponent,
        resolve: {
            virement: VirementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleV01App.virement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
