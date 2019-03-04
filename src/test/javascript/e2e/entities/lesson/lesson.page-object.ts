import { element, by, ElementFinder } from 'protractor';

export class LessonComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-lesson div table .btn-danger'));
    title = element.all(by.css('jhi-lesson div h2#page-heading span')).first();

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

export class LessonUpdatePage {
    pageTitle = element(by.id('jhi-lesson-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    typeLessonSelect = element(by.id('field_typeLesson'));
    dateLessonInput = element(by.id('field_dateLesson'));
    heurLessonInput = element(by.id('field_heurLesson'));
    candidatSelect = element(by.id('field_candidat'));
    voitureSelect = element(by.id('field_voiture'));
    entraineurSelect = element(by.id('field_entraineur'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setTypeLessonSelect(typeLesson) {
        await this.typeLessonSelect.sendKeys(typeLesson);
    }

    async getTypeLessonSelect() {
        return this.typeLessonSelect.element(by.css('option:checked')).getText();
    }

    async typeLessonSelectLastOption() {
        await this.typeLessonSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setDateLessonInput(dateLesson) {
        await this.dateLessonInput.sendKeys(dateLesson);
    }

    async getDateLessonInput() {
        return this.dateLessonInput.getAttribute('value');
    }

    async setHeurLessonInput(heurLesson) {
        await this.heurLessonInput.sendKeys(heurLesson);
    }

    async getHeurLessonInput() {
        return this.heurLessonInput.getAttribute('value');
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

    async entraineurSelectLastOption() {
        await this.entraineurSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async entraineurSelectOption(option) {
        await this.entraineurSelect.sendKeys(option);
    }

    getEntraineurSelect(): ElementFinder {
        return this.entraineurSelect;
    }

    async getEntraineurSelectedOption() {
        return this.entraineurSelect.element(by.css('option:checked')).getText();
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

export class LessonDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-lesson-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-lesson'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
