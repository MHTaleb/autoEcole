import { element, by, ElementFinder } from 'protractor';

export class ExamenComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-examen div table .btn-danger'));
    title = element.all(by.css('jhi-examen div h2#page-heading span')).first();

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

export class ExamenUpdatePage {
    pageTitle = element(by.id('jhi-examen-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dateExamenInput = element(by.id('field_dateExamen'));
    voitureSelect = element(by.id('field_voiture'));
    examinateurSelect = element(by.id('field_examinateur'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDateExamenInput(dateExamen) {
        await this.dateExamenInput.sendKeys(dateExamen);
    }

    async getDateExamenInput() {
        return this.dateExamenInput.getAttribute('value');
    }

    async voitureSelectLastOption() {
        await this.voitureSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async voitureSelectOption(option) {
        await this.voitureSelect.sendKeys(option);
    }

    getVoitureSelect(): ElementFinder {
        return this.voitureSelect;
    }

    async getVoitureSelectedOption() {
        return this.voitureSelect.element(by.css('option:checked')).getText();
    }

    async examinateurSelectLastOption() {
        await this.examinateurSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async examinateurSelectOption(option) {
        await this.examinateurSelect.sendKeys(option);
    }

    getExaminateurSelect(): ElementFinder {
        return this.examinateurSelect;
    }

    async getExaminateurSelectedOption() {
        return this.examinateurSelect.element(by.css('option:checked')).getText();
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

export class ExamenDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-examen-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-examen'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
