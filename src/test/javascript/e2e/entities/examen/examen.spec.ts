/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ExamenComponentsPage, ExamenDeleteDialog, ExamenUpdatePage } from './examen.page-object';

const expect = chai.expect;

describe('Examen e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let examenUpdatePage: ExamenUpdatePage;
    let examenComponentsPage: ExamenComponentsPage;
    /*let examenDeleteDialog: ExamenDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Examen', async () => {
        await navBarPage.goToEntity('examen');
        examenComponentsPage = new ExamenComponentsPage();
        await browser.wait(ec.visibilityOf(examenComponentsPage.title), 5000);
        expect(await examenComponentsPage.getTitle()).to.eq('autoEcoleV01App.examen.home.title');
    });

    it('should load create Examen page', async () => {
        await examenComponentsPage.clickOnCreateButton();
        examenUpdatePage = new ExamenUpdatePage();
        expect(await examenUpdatePage.getPageTitle()).to.eq('autoEcoleV01App.examen.home.createOrEditLabel');
        await examenUpdatePage.cancel();
    });

    /* it('should create and save Examen', async () => {
        const nbButtonsBeforeCreate = await examenComponentsPage.countDeleteButtons();

        await examenComponentsPage.clickOnCreateButton();
        await promise.all([
            examenUpdatePage.setDateExamenInput('2000-12-31'),
            examenUpdatePage.voitureSelectLastOption(),
            examenUpdatePage.examinateurSelectLastOption(),
        ]);
        expect(await examenUpdatePage.getDateExamenInput()).to.eq('2000-12-31');
        await examenUpdatePage.save();
        expect(await examenUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await examenComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last Examen', async () => {
        const nbButtonsBeforeDelete = await examenComponentsPage.countDeleteButtons();
        await examenComponentsPage.clickOnLastDeleteButton();

        examenDeleteDialog = new ExamenDeleteDialog();
        expect(await examenDeleteDialog.getDialogTitle())
            .to.eq('autoEcoleV01App.examen.delete.question');
        await examenDeleteDialog.clickOnConfirmButton();

        expect(await examenComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
