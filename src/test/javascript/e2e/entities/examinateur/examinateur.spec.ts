/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ExaminateurComponentsPage, ExaminateurDeleteDialog, ExaminateurUpdatePage } from './examinateur.page-object';

const expect = chai.expect;

describe('Examinateur e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let examinateurUpdatePage: ExaminateurUpdatePage;
    let examinateurComponentsPage: ExaminateurComponentsPage;
    let examinateurDeleteDialog: ExaminateurDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Examinateurs', async () => {
        await navBarPage.goToEntity('examinateur');
        examinateurComponentsPage = new ExaminateurComponentsPage();
        await browser.wait(ec.visibilityOf(examinateurComponentsPage.title), 5000);
        expect(await examinateurComponentsPage.getTitle()).to.eq('autoEcoleV01App.examinateur.home.title');
    });

    it('should load create Examinateur page', async () => {
        await examinateurComponentsPage.clickOnCreateButton();
        examinateurUpdatePage = new ExaminateurUpdatePage();
        expect(await examinateurUpdatePage.getPageTitle()).to.eq('autoEcoleV01App.examinateur.home.createOrEditLabel');
        await examinateurUpdatePage.cancel();
    });

    it('should create and save Examinateurs', async () => {
        const nbButtonsBeforeCreate = await examinateurComponentsPage.countDeleteButtons();

        await examinateurComponentsPage.clickOnCreateButton();
        await promise.all([
            examinateurUpdatePage.setNomInput('nom'),
            examinateurUpdatePage.setPrenomInput('prenom'),
            examinateurUpdatePage.setTelephoneInput('telephone')
        ]);
        expect(await examinateurUpdatePage.getNomInput()).to.eq('nom');
        expect(await examinateurUpdatePage.getPrenomInput()).to.eq('prenom');
        expect(await examinateurUpdatePage.getTelephoneInput()).to.eq('telephone');
        await examinateurUpdatePage.save();
        expect(await examinateurUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await examinateurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Examinateur', async () => {
        const nbButtonsBeforeDelete = await examinateurComponentsPage.countDeleteButtons();
        await examinateurComponentsPage.clickOnLastDeleteButton();

        examinateurDeleteDialog = new ExaminateurDeleteDialog();
        expect(await examinateurDeleteDialog.getDialogTitle()).to.eq('autoEcoleV01App.examinateur.delete.question');
        await examinateurDeleteDialog.clickOnConfirmButton();

        expect(await examinateurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
