/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ExamenInfoComponentsPage, ExamenInfoDeleteDialog, ExamenInfoUpdatePage } from './examen-info.page-object';

const expect = chai.expect;

describe('ExamenInfo e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let examenInfoUpdatePage: ExamenInfoUpdatePage;
    let examenInfoComponentsPage: ExamenInfoComponentsPage;
    /*let examenInfoDeleteDialog: ExamenInfoDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ExamenInfos', async () => {
        await navBarPage.goToEntity('examen-info');
        examenInfoComponentsPage = new ExamenInfoComponentsPage();
        await browser.wait(ec.visibilityOf(examenInfoComponentsPage.title), 5000);
        expect(await examenInfoComponentsPage.getTitle()).to.eq('autoEcoleApp.examenInfo.home.title');
    });

    it('should load create ExamenInfo page', async () => {
        await examenInfoComponentsPage.clickOnCreateButton();
        examenInfoUpdatePage = new ExamenInfoUpdatePage();
        expect(await examenInfoUpdatePage.getPageTitle()).to.eq('autoEcoleApp.examenInfo.home.createOrEditLabel');
        await examenInfoUpdatePage.cancel();
    });

    /* it('should create and save ExamenInfos', async () => {
        const nbButtonsBeforeCreate = await examenInfoComponentsPage.countDeleteButtons();

        await examenInfoComponentsPage.clickOnCreateButton();
        await promise.all([
            examenInfoUpdatePage.etatSelectLastOption(),
            examenInfoUpdatePage.typeSelectLastOption(),
            examenInfoUpdatePage.examenSelectLastOption(),
            examenInfoUpdatePage.candidatSelectLastOption(),
        ]);
        await examenInfoUpdatePage.save();
        expect(await examenInfoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await examenInfoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last ExamenInfo', async () => {
        const nbButtonsBeforeDelete = await examenInfoComponentsPage.countDeleteButtons();
        await examenInfoComponentsPage.clickOnLastDeleteButton();

        examenInfoDeleteDialog = new ExamenInfoDeleteDialog();
        expect(await examenInfoDeleteDialog.getDialogTitle())
            .to.eq('autoEcoleApp.examenInfo.delete.question');
        await examenInfoDeleteDialog.clickOnConfirmButton();

        expect(await examenInfoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
