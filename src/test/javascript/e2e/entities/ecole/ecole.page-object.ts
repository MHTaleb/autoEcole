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
    titreEcoleInput = element(by.id('field_titreEcole'));
    presidentSelect = element(by.id('field_president'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setTitreEcoleInput(titreEcole) {
        await this.titreEcoleInput.sendKeys(titreEcole);
    }

    async getTitreEcoleInput() {
        return this.titreEcoleInput.getAttribute('value');
    }

    async presidentSelectLastOption() {
        await this.presidentSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async presidentSelectOption(option) {
        await this.presidentSelect.sendKeys(option);
    }

    getPresidentSelect(): ElementFinder {
        return this.presidentSelect;
    }

    async getPresidentSelectedOption() {
        return this.presidentSelect.element(by.css('option:checked')).getText();
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
