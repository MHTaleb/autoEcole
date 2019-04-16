import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ICandidat } from 'app/shared/model/candidat.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { CandidatService } from './candidat.service';

import { BreakpointObserver, BreakpointState, Breakpoints } from '@angular/cdk/layout';
@Component({
    selector: 'jhi-candidat',
    templateUrl: './candidat.component.html'
})
export class CandidatComponent implements OnInit, OnDestroy {
    currentAccount: any;
    candidats: ICandidat[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    searchValue: any;
    small: any;
    medium: any;
    large: any;

    constructor(
        protected candidatService: CandidatService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager,
        public breakpointObserver: BreakpointObserver
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadAll() {
        this.candidatService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<ICandidat[]>) => this.paginateCandidats(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/candidat'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/candidat',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCandidats();
        /*
        this.breakpointObserver
            .observe(['(max-width: 425px)'])
            .subscribe((state: BreakpointState) => {
                if (state.matches) {
                    this.small = true;
                    this.medium = false;
                    this.large = false;
                }
                console.log('Small screen event');
            });
        this.breakpointObserver
            .observe(['(max-width: 1025px)', '(min-width: 426px)'])
            .subscribe((state: BreakpointState) => {
                if (state.matches) {
                    this.small = false;
                    this.medium = true;
                    this.large = false;
                }
                console.log('Medium screen event');
            });
        this.breakpointObserver
            .observe(['(min-width: 1025px)'])
            .subscribe((state: BreakpointState) => {
                if (state.matches) {
                    this.small = false;
                    this.medium = false;
                    this.large = true;
                }
                console.log('Large screen event');
            });*/
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICandidat) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInCandidats() {
        this.eventSubscriber = this.eventManager.subscribe('candidatListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    cardsStyleClasses() {
        return {
            'mb-4': true,
            'col-sm-12': this.small,
            'col-sm-4': this.medium,
            'col-sm-3': this.large
        };
    }

    protected paginateCandidats(data: ICandidat[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.candidats = data;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
