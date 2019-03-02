/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CandidatComponentsPage, CandidatDeleteDialog, CandidatUpdatePage } from './candidat.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Candidat e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let candidatUpdatePage: CandidatUpdatePage;
    let candidatComponentsPage: CandidatComponentsPage;
    let candidatDeleteDialog: CandidatDeleteDialog;
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

    it('should load Candidats', async () => {
        await navBarPage.goToEntity('candidat');
        candidatComponentsPage = new CandidatComponentsPage();
        await browser.wait(ec.visibilityOf(candidatComponentsPage.title), 5000);
        expect(await candidatComponentsPage.getTitle()).to.eq('autoEcoleApp.candidat.home.title');
    });

    it('should load create Candidat page', async () => {
        await candidatComponentsPage.clickOnCreateButton();
        candidatUpdatePage = new CandidatUpdatePage();
        expect(await candidatUpdatePage.getPageTitle()).to.eq('autoEcoleApp.candidat.home.createOrEditLabel');
        await candidatUpdatePage.cancel();
    });

    it('should create and save Candidats', async () => {
        const nbButtonsBeforeCreate = await candidatComponentsPage.countDeleteButtons();

        await candidatComponentsPage.clickOnCreateButton();
        await promise.all([
            candidatUpdatePage.setPhotoInput(absolutePath),
            candidatUpdatePage.setNomInput('nom'),
            candidatUpdatePage.setPrenomInput('prenom'),
            candidatUpdatePage.setPereInput('pere'),
            candidatUpdatePage.setMereInput('mere'),
            candidatUpdatePage.setTelephoneInput('telephone'),
            candidatUpdatePage.setDateInscriptionInput('2000-12-31'),
            candidatUpdatePage.setDateNaissanceInput('2000-12-31'),
            candidatUpdatePage.setLieuNaissanceInput('lieuNaissance'),
            candidatUpdatePage.nationaliteSelectLastOption(),
            candidatUpdatePage.setAdresseInput('adresse')
        ]);
        expect(await candidatUpdatePage.getPhotoInput()).to.endsWith(fileNameToUpload);
        expect(await candidatUpdatePage.getNomInput()).to.eq('nom');
        expect(await candidatUpdatePage.getPrenomInput()).to.eq('prenom');
        expect(await candidatUpdatePage.getPereInput()).to.eq('pere');
        expect(await candidatUpdatePage.getMereInput()).to.eq('mere');
        expect(await candidatUpdatePage.getTelephoneInput()).to.eq('telephone');
        expect(await candidatUpdatePage.getDateInscriptionInput()).to.eq('2000-12-31');
        expect(await candidatUpdatePage.getDateNaissanceInput()).to.eq('2000-12-31');
        expect(await candidatUpdatePage.getLieuNaissanceInput()).to.eq('lieuNaissance');
        expect(await candidatUpdatePage.getAdresseInput()).to.eq('adresse');
        await candidatUpdatePage.save();
        expect(await candidatUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await candidatComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Candidat', async () => {
        const nbButtonsBeforeDelete = await candidatComponentsPage.countDeleteButtons();
        await candidatComponentsPage.clickOnLastDeleteButton();

        candidatDeleteDialog = new CandidatDeleteDialog();
        expect(await candidatDeleteDialog.getDialogTitle()).to.eq('autoEcoleApp.candidat.delete.question');
        await candidatDeleteDialog.clickOnConfirmButton();

        expect(await candidatComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
