import { element, by, ElementFinder } from 'protractor';

export class CandidatComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-candidat div table .btn-danger'));
    title = element.all(by.css('jhi-candidat div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CandidatUpdatePage {
    pageTitle = element(by.id('jhi-candidat-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    photoInput = element(by.id('file_photo'));
    nomInput = element(by.id('field_nom'));
    prenomInput = element(by.id('field_prenom'));
    pereInput = element(by.id('field_pere'));
    mereInput = element(by.id('field_mere'));
    telephoneInput = element(by.id('field_telephone'));
    dateInscriptionInput = element(by.id('field_dateInscription'));
    dateNaissanceInput = element(by.id('field_dateNaissance'));
    lieuNaissanceInput = element(by.id('field_lieuNaissance'));
    nationaliteSelect = element(by.id('field_nationalite'));
    adresseInput = element(by.id('field_adresse'));
    nidInput = element(by.id('field_nid'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setPhotoInput(photo) {
        await this.photoInput.sendKeys(photo);
    }

    async getPhotoInput() {
        return this.photoInput.getAttribute('value');
    }

    async setNomInput(nom) {
        await this.nomInput.sendKeys(nom);
    }

    async getNomInput() {
        return this.nomInput.getAttribute('value');
    }

    async setPrenomInput(prenom) {
        await this.prenomInput.sendKeys(prenom);
    }

    async getPrenomInput() {
        return this.prenomInput.getAttribute('value');
    }

    async setPereInput(pere) {
        await this.pereInput.sendKeys(pere);
    }

    async getPereInput() {
        return this.pereInput.getAttribute('value');
    }

    async setMereInput(mere) {
        await this.mereInput.sendKeys(mere);
    }

    async getMereInput() {
        return this.mereInput.getAttribute('value');
    }

    async setTelephoneInput(telephone) {
        await this.telephoneInput.sendKeys(telephone);
    }

    async getTelephoneInput() {
        return this.telephoneInput.getAttribute('value');
    }

    async setDateInscriptionInput(dateInscription) {
        await this.dateInscriptionInput.sendKeys(dateInscription);
    }

    async getDateInscriptionInput() {
        return this.dateInscriptionInput.getAttribute('value');
    }

    async setDateNaissanceInput(dateNaissance) {
        await this.dateNaissanceInput.sendKeys(dateNaissance);
    }

    async getDateNaissanceInput() {
        return this.dateNaissanceInput.getAttribute('value');
    }

    async setLieuNaissanceInput(lieuNaissance) {
        await this.lieuNaissanceInput.sendKeys(lieuNaissance);
    }

    async getLieuNaissanceInput() {
        return this.lieuNaissanceInput.getAttribute('value');
    }

    async setNationaliteSelect(nationalite) {
        await this.nationaliteSelect.sendKeys(nationalite);
    }

    async getNationaliteSelect() {
        return this.nationaliteSelect.element(by.css('option:checked')).getText();
    }

    async nationaliteSelectLastOption() {
        await this.nationaliteSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setAdresseInput(adresse) {
        await this.adresseInput.sendKeys(adresse);
    }

    async getAdresseInput() {
        return this.adresseInput.getAttribute('value');
    }

    async setNidInput(nid) {
        await this.nidInput.sendKeys(nid);
    }

    async getNidInput() {
        return this.nidInput.getAttribute('value');
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class CandidatDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-candidat-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-candidat'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
