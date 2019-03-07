/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { VirementComponentsPage, VirementDeleteDialog, VirementUpdatePage } from './virement.page-object';

const expect = chai.expect;

describe('Virement e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let virementUpdatePage: VirementUpdatePage;
    let virementComponentsPage: VirementComponentsPage;
    /*let virementDeleteDialog: VirementDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Virements', async () => {
        await navBarPage.goToEntity('virement');
        virementComponentsPage = new VirementComponentsPage();
        await browser.wait(ec.visibilityOf(virementComponentsPage.title), 5000);
        expect(await virementComponentsPage.getTitle()).to.eq('autoEcoleV01App.virement.home.title');
    });

    it('should load create Virement page', async () => {
        await virementComponentsPage.clickOnCreateButton();
        virementUpdatePage = new VirementUpdatePage();
        expect(await virementUpdatePage.getPageTitle()).to.eq('autoEcoleV01App.virement.home.createOrEditLabel');
        await virementUpdatePage.cancel();
    });

    /* it('should create and save Virements', async () => {
        const nbButtonsBeforeCreate = await virementComponentsPage.countDeleteButtons();

        await virementComponentsPage.clickOnCreateButton();
        await promise.all([
            virementUpdatePage.setMontantInput('5'),
            virementUpdatePage.setDateVirementInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            virementUpdatePage.candidatSelectLastOption(),
        ]);
        expect(await virementUpdatePage.getMontantInput()).to.eq('5');
        expect(await virementUpdatePage.getDateVirementInput()).to.contain('2001-01-01T02:30');
        await virementUpdatePage.save();
        expect(await virementUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await virementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last Virement', async () => {
        const nbButtonsBeforeDelete = await virementComponentsPage.countDeleteButtons();
        await virementComponentsPage.clickOnLastDeleteButton();

        virementDeleteDialog = new VirementDeleteDialog();
        expect(await virementDeleteDialog.getDialogTitle())
            .to.eq('autoEcoleV01App.virement.delete.question');
        await virementDeleteDialog.clickOnConfirmButton();

        expect(await virementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
