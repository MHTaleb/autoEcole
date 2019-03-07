import { element, by, ElementFinder } from 'protractor';

export class ExamenInfoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-examen-info div table .btn-danger'));
    title = element.all(by.css('jhi-examen-info div h2#page-heading span')).first();

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

export class ExamenInfoUpdatePage {
    pageTitle = element(by.id('jhi-examen-info-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    etatSelect = element(by.id('field_etat'));
    typeSelect = element(by.id('field_type'));
    examenSelect = element(by.id('field_examen'));
    candidatSelect = element(by.id('field_candidat'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setEtatSelect(etat) {
        await this.etatSelect.sendKeys(etat);
    }

    async getEtatSelect() {
        return this.etatSelect.element(by.css('option:checked')).getText();
    }

    async etatSelectLastOption() {
        await this.etatSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setTypeSelect(type) {
        await this.typeSelect.sendKeys(type);
    }

    async getTypeSelect() {
        return this.typeSelect.element(by.css('option:checked')).getText();
    }

    async typeSelectLastOption() {
        await this.typeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async examenSelectLastOption() {
        await this.examenSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async examenSelectOption(option) {
        await this.examenSelect.sendKeys(option);
    }

    getExamenSelect(): ElementFinder {
        return this.examenSelect;
    }

    async getExamenSelectedOption() {
        return this.examenSelect.element(by.css('option:checked')).getText();
    }

    async candidatSelectLastOption() {
        await this.candidatSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async candidatSelectOption(option) {
        await this.candidatSelect.sendKeys(option);
    }

    getCandidatSelect(): ElementFinder {
        return this.candidatSelect;
    }

    async getCandidatSelectedOption() {
        return this.candidatSelect.element(by.css('option:checked')).getText();
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

export class ExamenInfoDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-examenInfo-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-examenInfo'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
