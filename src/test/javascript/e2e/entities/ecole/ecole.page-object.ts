import { element, by, ElementFinder } from 'protractor';

export class EcoleComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-ecole div table .btn-danger'));
    title = element.all(by.css('jhi-ecole div h2#page-heading span')).first();

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

export class EcoleUpdatePage {
    pageTitle = element(by.id('jhi-ecole-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomEcoleInput = element(by.id('field_nomEcole'));
    presidentInput = element(by.id('field_president'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNomEcoleInput(nomEcole) {
        await this.nomEcoleInput.sendKeys(nomEcole);
    }

    async getNomEcoleInput() {
        return this.nomEcoleInput.getAttribute('value');
    }

    async setPresidentInput(president) {
        await this.presidentInput.sendKeys(president);
    }

    async getPresidentInput() {
        return this.presidentInput.getAttribute('value');
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

export class EcoleDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-ecole-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-ecole'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
