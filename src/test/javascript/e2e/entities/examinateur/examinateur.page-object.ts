import { element, by, ElementFinder } from 'protractor';

export class ExaminateurComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-examinateur div table .btn-danger'));
    title = element.all(by.css('jhi-examinateur div h2#page-heading span')).first();

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

export class ExaminateurUpdatePage {
    pageTitle = element(by.id('jhi-examinateur-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomInput = element(by.id('field_nom'));
    prenomInput = element(by.id('field_prenom'));
    telephoneInput = element(by.id('field_telephone'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
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

export class ExaminateurDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-examinateur-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-examinateur'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
