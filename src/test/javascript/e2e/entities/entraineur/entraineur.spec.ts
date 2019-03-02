/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EntraineurComponentsPage, EntraineurDeleteDialog, EntraineurUpdatePage } from './entraineur.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Entraineur e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let entraineurUpdatePage: EntraineurUpdatePage;
    let entraineurComponentsPage: EntraineurComponentsPage;
    let entraineurDeleteDialog: EntraineurDeleteDialog;
    const fileNameToUpload = 'logo-jhipster.png';
    const fileToUpload = '../../../../../main/webapp/content/images/' + fileNameToUpload;
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Entraineurs', async () => {
        await navBarPage.goToEntity('entraineur');
        entraineurComponentsPage = new EntraineurComponentsPage();
        await browser.wait(ec.visibilityOf(entraineurComponentsPage.title), 5000);
        expect(await entraineurComponentsPage.getTitle()).to.eq('autoEcoleApp.entraineur.home.title');
    });

    it('should load create Entraineur page', async () => {
        await entraineurComponentsPage.clickOnCreateButton();
        entraineurUpdatePage = new EntraineurUpdatePage();
        expect(await entraineurUpdatePage.getPageTitle()).to.eq('autoEcoleApp.entraineur.home.createOrEditLabel');
        await entraineurUpdatePage.cancel();
    });

    it('should create and save Entraineurs', async () => {
        const nbButtonsBeforeCreate = await entraineurComponentsPage.countDeleteButtons();

        await entraineurComponentsPage.clickOnCreateButton();
        await promise.all([
            entraineurUpdatePage.setPhotoInput(absolutePath),
            entraineurUpdatePage.setNomInput('nom'),
            entraineurUpdatePage.setPrenomInput('prenom'),
            entraineurUpdatePage.setTelephoneInput('telephone'),
            entraineurUpdatePage.compteSelectLastOption()
        ]);
        expect(await entraineurUpdatePage.getPhotoInput()).to.endsWith(fileNameToUpload);
        expect(await entraineurUpdatePage.getNomInput()).to.eq('nom');
        expect(await entraineurUpdatePage.getPrenomInput()).to.eq('prenom');
        expect(await entraineurUpdatePage.getTelephoneInput()).to.eq('telephone');
        await entraineurUpdatePage.save();
        expect(await entraineurUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await entraineurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Entraineur', async () => {
        const nbButtonsBeforeDelete = await entraineurComponentsPage.countDeleteButtons();
        await entraineurComponentsPage.clickOnLastDeleteButton();

        entraineurDeleteDialog = new EntraineurDeleteDialog();
        expect(await entraineurDeleteDialog.getDialogTitle()).to.eq('autoEcoleApp.entraineur.delete.question');
        await entraineurDeleteDialog.clickOnConfirmButton();

        expect(await entraineurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
