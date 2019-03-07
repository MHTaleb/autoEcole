/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EcoleComponentsPage, EcoleDeleteDialog, EcoleUpdatePage } from './ecole.page-object';

const expect = chai.expect;

describe('Ecole e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let ecoleUpdatePage: EcoleUpdatePage;
    let ecoleComponentsPage: EcoleComponentsPage;
    let ecoleDeleteDialog: EcoleDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Ecoles', async () => {
        await navBarPage.goToEntity('ecole');
        ecoleComponentsPage = new EcoleComponentsPage();
        await browser.wait(ec.visibilityOf(ecoleComponentsPage.title), 5000);
        expect(await ecoleComponentsPage.getTitle()).to.eq('autoEcoleV01App.ecole.home.title');
    });

    it('should load create Ecole page', async () => {
        await ecoleComponentsPage.clickOnCreateButton();
        ecoleUpdatePage = new EcoleUpdatePage();
        expect(await ecoleUpdatePage.getPageTitle()).to.eq('autoEcoleV01App.ecole.home.createOrEditLabel');
        await ecoleUpdatePage.cancel();
    });

    it('should create and save Ecoles', async () => {
        const nbButtonsBeforeCreate = await ecoleComponentsPage.countDeleteButtons();

        await ecoleComponentsPage.clickOnCreateButton();
        await promise.all([ecoleUpdatePage.setNomEcoleInput('nomEcole'), ecoleUpdatePage.setPresidentInput('president')]);
        expect(await ecoleUpdatePage.getNomEcoleInput()).to.eq('nomEcole');
        expect(await ecoleUpdatePage.getPresidentInput()).to.eq('president');
        await ecoleUpdatePage.save();
        expect(await ecoleUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await ecoleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Ecole', async () => {
        const nbButtonsBeforeDelete = await ecoleComponentsPage.countDeleteButtons();
        await ecoleComponentsPage.clickOnLastDeleteButton();

        ecoleDeleteDialog = new EcoleDeleteDialog();
        expect(await ecoleDeleteDialog.getDialogTitle()).to.eq('autoEcoleV01App.ecole.delete.question');
        await ecoleDeleteDialog.clickOnConfirmButton();

        expect(await ecoleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
