import { element, by, ElementFinder } from 'protractor';

export class VirementComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-virement div table .btn-danger'));
    title = element.all(by.css('jhi-virement div h2#page-heading span')).first();

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

export class VirementUpdatePage {
    pageTitle = element(by.id('jhi-virement-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    montantInput = element(by.id('field_montant'));
    dateVirementInput = element(by.id('field_dateVirement'));
    candidatSelect = element(by.id('field_candidat'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setMontantInput(montant) {
        await this.montantInput.sendKeys(montant);
    }

    async getMontantInput() {
        return this.montantInput.getAttribute('value');
    }

    async setDateVirementInput(dateVirement) {
        await this.dateVirementInput.sendKeys(dateVirement);
    }

    async getDateVirementInput() {
        return this.dateVirementInput.getAttribute('value');
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

export class VirementDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-virement-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-virement'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
