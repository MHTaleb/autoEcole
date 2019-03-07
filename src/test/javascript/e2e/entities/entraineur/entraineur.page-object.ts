import { element, by, ElementFinder } from 'protractor';

export class EntraineurComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-entraineur div table .btn-danger'));
    title = element.all(by.css('jhi-entraineur div h2#page-heading span')).first();

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

export class EntraineurUpdatePage {
    pageTitle = element(by.id('jhi-entraineur-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    photoInput = element(by.id('file_photo'));
    nomInput = element(by.id('field_nom'));
    prenomInput = element(by.id('field_prenom'));
    telephoneInput = element(by.id('field_telephone'));
    compteSelect = element(by.id('field_compte'));

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

    async setTelephoneInput(telephone) {
        await this.telephoneInput.sendKeys(telephone);
    }

    async getTelephoneInput() {
        return this.telephoneInput.getAttribute('value');
    }

    async compteSelectLastOption() {
        await this.compteSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async compteSelectOption(option) {
        await this.compteSelect.sendKeys(option);
    }

    getCompteSelect(): ElementFinder {
        return this.compteSelect;
    }

    async getCompteSelectedOption() {
        return this.compteSelect.element(by.css('option:checked')).getText();
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

export class EntraineurDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-entraineur-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-entraineur'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
