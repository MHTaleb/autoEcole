/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CarComponentsPage, CarDeleteDialog, CarUpdatePage } from './car.page-object';

const expect = chai.expect;

describe('Car e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let carUpdatePage: CarUpdatePage;
    let carComponentsPage: CarComponentsPage;
    let carDeleteDialog: CarDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Cars', async () => {
        await navBarPage.goToEntity('car');
        carComponentsPage = new CarComponentsPage();
        await browser.wait(ec.visibilityOf(carComponentsPage.title), 5000);
        expect(await carComponentsPage.getTitle()).to.eq('autoEcoleApp.car.home.title');
    });

    it('should load create Car page', async () => {
        await carComponentsPage.clickOnCreateButton();
        carUpdatePage = new CarUpdatePage();
        expect(await carUpdatePage.getPageTitle()).to.eq('autoEcoleApp.car.home.createOrEditLabel');
        await carUpdatePage.cancel();
    });

    it('should create and save Cars', async () => {
        const nbButtonsBeforeCreate = await carComponentsPage.countDeleteButtons();

        await carComponentsPage.clickOnCreateButton();
        await promise.all([carUpdatePage.setMatriculeInput('matricule'), carUpdatePage.setMarqueInput('marque')]);
        expect(await carUpdatePage.getMatriculeInput()).to.eq('matricule');
        expect(await carUpdatePage.getMarqueInput()).to.eq('marque');
        await carUpdatePage.save();
        expect(await carUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await carComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Car', async () => {
        const nbButtonsBeforeDelete = await carComponentsPage.countDeleteButtons();
        await carComponentsPage.clickOnLastDeleteButton();

        carDeleteDialog = new CarDeleteDialog();
        expect(await carDeleteDialog.getDialogTitle()).to.eq('autoEcoleApp.car.delete.question');
        await carDeleteDialog.clickOnConfirmButton();

        expect(await carComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
