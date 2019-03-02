import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Car } from 'app/shared/model/car.model';
import { CarService } from './car.service';
import { CarComponent } from './car.component';
import { CarDetailComponent } from './car-detail.component';
import { CarUpdateComponent } from './car-update.component';
import { CarDeletePopupComponent } from './car-delete-dialog.component';
import { ICar } from 'app/shared/model/car.model';

@Injectable({ providedIn: 'root' })
export class CarResolve implements Resolve<ICar> {
    constructor(private service: CarService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICar> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Car>) => response.ok),
                map((car: HttpResponse<Car>) => car.body)
            );
        }
        return of(new Car());
    }
}

export const carRoute: Routes = [
    {
        path: '',
        component: CarComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'autoEcoleApp.car.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CarDetailComponent,
        resolve: {
            car: CarResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleApp.car.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CarUpdateComponent,
        resolve: {
            car: CarResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleApp.car.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CarUpdateComponent,
        resolve: {
            car: CarResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleApp.car.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const carPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CarDeletePopupComponent,
        resolve: {
            car: CarResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'autoEcoleApp.car.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
