'use strict';

const HTTP_STATUS_CODE_OK = 200;

class Validator {
    /**
     * Initialize Validator object for given form element.
     *
     * @param formElement
     * @param options
     */
    constructor(formElement, options) {
        if (typeof options === 'undefined') {
            // Default options.
            options = {};
        }

        this._formElement = formElement;

        // Validation rules.
        this._validators = typeof options.validators !== 'undefined' ? options.validators : {};

        // Form submission event handler.
        this._onSubmitEvent = typeof options.onSubmitEventHandler === 'function'
            ? options.onSubmitEventHandler
            : function (e) {
                if (!this.isValidForm()) {
                    e.preventDefault();

                    this.setFocusOnFirstInvalidField();
                }
            }.bind(this);

        // Form field input event handler.
        this._inputEvent = function (e) {
            this._validateElement(e.target);
        }.bind(this);

        const elements = this.getElements();

        // Add form field input event handler.
        for (let i = 0; i < elements.length; i++) {
            elements.item(i).addEventListener('input', this._inputEvent);
        }

        // Add form submission event handler.
        this.getFormSubmitButton().addEventListener('click', this._onSubmitEvent);
    }

    /**
     * Remove all validation event listeners, on form elements.
     */
    destroy() {
        const elements = this.getElements();

        // Remove form field input event handler.
        for (let i = 0; i < elements.length; i++) {
            elements.item(i).removeEventListener('input', this._inputEvent);
        }

        // Remove form submission event handler.
        this.getFormSubmitButton().removeEventListener('click', this._onSubmitEvent);
    }

    /**
     * Set focus on first form field.
     */
    setFocusOnFirstField() {
        const elements = this.getElements();

        if (elements.length > 0) {
            elements.item(0).focus();
        }
    }

    /**
     * Set focus on first invalid form field.
     */
    setFocusOnFirstInvalidField() {
        const elements = this.getElements();

        for (let i = 0; i < elements.length; i++) {
            let element = elements.item(i);

            if (this._isInvalid(element)) {
                element.focus();

                return;
            }
        }
    }

    /**
     * Set errors.
     *
     * @param errors
     */
    setErrors(errors) {
        const elements = this.getElements();

        for (let i = 0; i < elements.length; i++) {
            let element = elements.item(i),
                name = element.getAttribute('name');

            if (typeof errors[name] !== 'undefined') {
                for (let i = 0; i < errors[name].length; i++) {
                    this._setInvalid(element, errors[name][i]);
                }
            }
        }
    }

    /**
     * Validate form.
     *
     * @param except
     */
    validateForm(except) {
        if (typeof except === 'undefined') {
            except = [];
        }

        const elements = this.getElements();

        for (let i = 0; i < elements.length; i++) {
            let element = elements.item(i);

            if (except.length === 0 || except.indexOf(element.getAttribute('name')) === -1) {
                this._validateElement(element);
            }
        }
    }

    /**
     * Validate form field by field name.
     *
     * @param name
     */
    validateField(name) {
        const element = this.getElement(name);

        if (typeof element !== 'undefined') {
            this._validateElement(element);
        }
    }

    /**
     * Check if form is valid.
     *
     * @returns {boolean}
     */
    isValidForm() {
        if (!this.isValidatedForm()) {
            this.validateForm();
        }

        const elements = this.getElements();

        for (let i = 0; i < elements.length; i++) {
            if (this._isInvalid(elements.item(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if field of form is valid.
     *
     * @param name
     * @returns {*}
     */
    isValidField(name) {
        const element = this.getElement(name);

        if (typeof element !== 'undefined') {
            if (!this._isValidated(element)) {
                this._validateElement(element);
            }

            return this._isValid(element);
        }
    }

    /**
     * Check if form was validated.
     *
     * @returns {boolean}
     */
    isValidatedForm() {
        const elements = this.getElements();

        for (let i = 0; i < elements.length; i++) {
            if (!this._isValidated(elements.item(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if field of form was validated.
     *
     * @param name
     * @returns {boolean}
     */
    isValidatedField(name) {
        const element = this.getElement(name);

        if (typeof element === 'undefined') {
            return this._isValidated(element);
        }
    }

    /**
     * Set field of form as valid.
     *
     * @param name
     * @param message
     */
    setValidField(name, message) {
        const element = this.getElement(name);

        if (typeof element !== 'undefined') {
            this._setValid(element, message);
        }
    }

    /**
     * Set field of form as invalid.
     *
     * @param name
     * @param message
     */
    setInvalidField(name, message) {
        const element = this.getElement(name);

        if (typeof element !== 'undefined') {
            this._setInvalid(element, message);
        }
    }

    /**
     * Clear all validation on field of form.
     *
     * @param name
     */
    clearFieldValidation(name) {
        const element = this.getElement(name);

        if (typeof element !== 'undefined') {
            this._clearValidation(element);
        }
    }

    /**
     * Get validation form element.
     *
     * @returns {*}
     */
    getFormElement() {
        return this._formElement;
    }

    /**
     * Get form submit button.
     *
     * @returns {any}
     */
    getFormSubmitButton() {
        return this._formElement.querySelector('button[type=submit]');
    }

    /**
     * Get form elements for validation.
     *
     * @returns {NodeListOf<HTMLElementTagNameMap[string]> | NodeListOf<Element> | NodeListOf<SVGElementTagNameMap[string]>}
     */
    getElements() {
        const supportedElements = [
            'input.form-control',
            'textarea.form-control',
            'select.form-select',
            'input.form-check-input'
        ];

        return this._formElement.querySelectorAll(supportedElements.join(','));
    }

    /**
     * Clear validation on all elements of form.
     */
    clearFormValidation() {
        const elements = this.getElements();

        for (let i = 0; i < elements.length; i++) {
            this._clearValidation(elements.item(i));
        }
    }

    /**
     * Get element of form by name.
     *
     * @param name
     * @returns {Element}
     */
    getElement(name) {
        const elements = this.getElements();

        for (let i = 0; i < elements.length; i++) {
            let element = elements.item(i);

            if (element.getAttribute('name') === name) {
                return element;
            }
        }
    }

    /**
     * Check if field is validated.
     *
     * @param element
     * @returns {boolean}
     * @private
     */
    _isValidated(element) {
        if (!this._isValid(element) && !this._isInvalid(element)) {
            return false;
        }

        return true;
    }

    /**
     * Check if field is invalid.
     *
     * @param element
     * @returns {*}
     * @private
     */
    _isInvalid(element) {
        return element.classList.contains('is-invalid');
    }

    /**
     * Check if field is valid.
     *
     * @param element
     * @returns {*}
     * @private
     */
    _isValid(element) {
        return element.classList.contains('is-valid');
    }

    /**
     * Validate element of form.
     *
     * @param element
     * @private
     */
    _validateElement(element) {
        const validators = this._getElementValidators(element);

        let valid = true,
            message = '';

        for (let validator in validators) {
            if (!validators.hasOwnProperty(validator)) {
                continue;
            }

            if (typeof validators[validator] === 'function') {
                let result = (validators[validator].bind(this))(element);

                valid = typeof result.valid === 'boolean' ? result.valid : true;
                message = typeof result.message === 'string' ? result.message : '';

                if (!valid) {
                    // Exit after first validation error.
                    break;
                }
            }
        }

        valid ? this._setValid(element, message) : this._setInvalid(element, message);
    }

    /**
     * Get element of form validators.
     *
     * @param element
     * @returns {{callback: (function(*): {valid: boolean, message: string})}|*}
     * @private
     */
    _getElementValidators(element) {
        const name = element.getAttribute('name');

        if (typeof this._validators[name] !== 'undefined') {
            return this._validators[name];
        } else {
            // Default validator.
            return {
                'callback': function (element) {
                    return {'valid': true};
                }
            };
        }
    }

    /**
     * Set element of form as valid.
     *
     * @param element
     * @param message
     * @private
     */
    _setValid(element, message) {
        this._clearValidation(element);

        element.classList.add('is-valid');
        element.closest('div').append(this._getValidFeedbackElement(message));
    }

    /**
     * Set element of form as invalid.
     *
     * @param element
     * @param message
     * @private
     */
    _setInvalid(element, message) {
        this._clearValidation(element);

        element.classList.add('is-invalid');
        element.closest('div').append(this._getInvalidFeedbackElement(message));
    }

    /**
     * Clear all validation on element of form.
     *
     * @param element
     * @private
     */
    _clearValidation(element) {
        element.classList.remove('is-invalid', 'is-valid');

        const feedbacks = element.closest('div')
            .querySelectorAll('div.invalid-feedback, div.valid-feedback');

        for (let i = 0; i < feedbacks.length; i++) {
            feedbacks.item(i).remove();
        }
    }

    /**
     * Get validation valid feedback element.
     *
     * @param message
     * @returns {HTMLDivElement}
     * @private
     */
    _getValidFeedbackElement(message) {
        return this._getFeedbackElement(true, message);
    }

    /**
     * Get validation invalid feedback element.
     *
     * @param message
     * @returns {HTMLDivElement}
     * @private
     */
    _getInvalidFeedbackElement(message) {
        return this._getFeedbackElement(false, message);
    }

    /**
     * Get validation feedback element.
     *
     * @param isValid
     * @param message
     * @returns {HTMLDivElement}
     * @private
     */
    _getFeedbackElement(isValid, message) {
        if (typeof isValid !== 'boolean') {
            isValid = true;
        }

        const element = document.createElement('div');

        element.classList.add(isValid ? 'valid-feedback' : 'invalid-feedback');

        if (typeof message === 'string' && message !== '') {
            element.append(document.createTextNode(message));
        }

        return element;
    }
}

class AlertManager {
    constructor(element) {
        this._element = element;
    }

    success(message) {
        this._show('success', message);
    }

    warning(message) {
        this._show('warning', message);
    }

    danger(message) {
        this._show('danger', message);
    }

    _show(level, message) {
        const alertElement = (function (level, message) {
            const alertElement = this._getAlertElement(level);

            alertElement.append(this._getIconElement(level));
            alertElement.append(this._getMessageElement(message));
            alertElement.append(this._getCloseButtonElement());

            return alertElement;
        }.bind(this))(level, message);

        const timeoutId = setTimeout(function (alertElement) {
            alertElement.querySelector('button[data-bs-dismiss]').click();
        }, 10000, alertElement);

        alertElement.addEventListener('close.bs.alert', function (e) {
            clearTimeout(timeoutId);
        }, {once: true});

        this._element.append(alertElement);
    }

    _getAlertElement(level) {
        const alertElement = document.createElement('div');

        alertElement.setAttribute('role', 'alert');

        alertElement.classList.add(
            'alert', 'alert-' + level, 'alert-dismissible', 'fade', 'show',
            'd-flex', 'justify-content-start', 'align-items-center', 'gap-3',
            'border', 'border-' + level
        );

        return alertElement;
    }

    _getIconElement(level) {
        const iconElement = document.createElement('i');

        let iconClassName = '';

        switch (level) {
            case 'success':
                iconClassName = 'bi-check-square';
                break;

            case 'warning':
                iconClassName = 'bi-info-square';
                break;

            case 'danger':
            default:
                iconClassName = 'bi-exclamation-square';
                break;
        }

        iconElement.classList.add('bi', iconClassName, 'fs-1', 'd-flex', 'align-items-center');

        return iconElement;
    }

    _getMessageElement(message) {
        const messageElement = document.createElement('span');

        messageElement.append(document.createTextNode(message));

        return messageElement;
    }

    _getCloseButtonElement() {
        const buttonElement = document.createElement('button');

        buttonElement.setAttribute('type', 'button');
        buttonElement.setAttribute('data-bs-dismiss', 'alert');
        buttonElement.setAttribute('aria-label', 'Закрити');

        buttonElement.classList.add('btn-close', 'focus-ring', 'focus-ring-dark');

        return buttonElement;
    }
}

class Currency {
    constructor(
        alphabeticCode,
        decimalPlace,
        name,
        nameUk,
        createdAt,
        updatedAt
    ) {
        this._alphabeticCode = alphabeticCode;
        this._decimalPlace = decimalPlace;
        this._name = name;
        this._nameUk = nameUk;
        this._createdAt = createdAt;
        this._updatedAt = updatedAt;
    }

    get alphabeticCode() {
        return this._alphabeticCode;
    }

    get decimalPlace() {
        return this._decimalPlace;
    }

    get name() {
        return this._name;
    }

    get nameUk() {
        return this._nameUk;
    }

    get createdAt() {
        return this._createdAt;
    }

    get updatedAt() {
        return this._updatedAt;
    }

    set alphabeticCode(alphabeticCode) {
        this._alphabeticCode = alphabeticCode;
    }

    set decimalPlace(decimalPlace) {
        this._decimalPlace = decimalPlace;
    }

    set name(name) {
        this._name = name;
    }

    set nameUk(nameUk) {
        this._nameUk = nameUk;
    }

    set createdAt(createdAt) {
        this._createdAt = createdAt;
    }

    set updatedAt(updatedAt) {
        this._updatedAt = updatedAt;
    }
}

class Rate {
    constructor(
        id,
        unit,
        unitCurrency,
        rateCurrency,
        buyRate,
        saleRate,
        createdAt,
        updatedAt
    ) {
        this._id = id;
        this._unit = unit;
        this._unitCurrency = unitCurrency;
        this._rateCurrency = rateCurrency;
        this._buyRate = buyRate;
        this._saleRate = saleRate;
        this._createdAt = createdAt;
        this._updatedAt = updatedAt;
    }

    get id() {
        return this._id;
    }

    get unit() {
        return this._unit;
    }

    get unitCurrency() {
        return this._unitCurrency;
    }

    get rateCurrency() {
        return this._rateCurrency;
    }

    get buyRate() {
        return this._buyRate;
    }

    get saleRate() {
        return this._saleRate;
    }

    get createdAt() {
        return this._createdAt;
    }

    get updatedAt() {
        return this._updatedAt;
    }

    set id(id) {
        this._id = id;
    }

    set unit(unit) {
        this._unit = unit;
    }

    set unitCurrency(unitCurrency) {
        this._unitCurrency = unitCurrency;
    }

    set rateCurrency(rateCurrency) {
        this._rateCurrency = rateCurrency;
    }

    set buyRate(buyRate) {
        this._buyRate = buyRate;
    }

    set saleRate(saleRate) {
        this._saleRate = saleRate;
    }

    set createdAt(createdAt) {
        this._createdAt = createdAt;
    }

    set updatedAt(updatedAt) {
        this._updatedAt = updatedAt;
    }
}

class Exchanger {
    constructor(
        id,
        rates,
        name,
        nameUk,
        createdAt,
        updatedAt
    ) {
        this._id = id;
        this._rates = rates;
        this._name = name;
        this._nameUk = nameUk;
        this._createdAt = createdAt;
        this._updatedAt = updatedAt;
    }

    get id() {
        return this._id;
    }

    get rates() {
        return this._rates;
    }

    get name() {
        return this._name;
    }

    get nameUk() {
        return this._nameUk;
    }

    get createdAt() {
        return this._createdAt;
    }

    get updatedAt() {
        return this._updatedAt;
    }

    set id(id) {
        this._id = id;
    }

    set rates(rates) {
        this._rates = rates;
    }

    set name(name) {
        this._name = name;
    }

    set nameUk(nameUk) {
        this._nameUk = nameUk;
    }

    set createdAt(createdAt) {
        this._createdAt = createdAt;
    }

    set updatedAt(updatedAt) {
        this._updatedAt = updatedAt;
    }
}

class App {
    constructor() {
        this._alertsContainerElement = document.getElementById('alerts-container');
        this._exchangersContainerElement = document.getElementById('exchangers-container');
        this._spinnerModalElement = document.getElementById('spinner-modal');
        this._periodModalElement = document.getElementById('period-modal');
        this._chartModalElement = document.getElementById('chart-modal');

        this._periodLinkElement = this._exchangersContainerElement.querySelector('#period-link');
        this._reloadLinkElement = this._exchangersContainerElement.querySelector('#reload-link');

        this._exchangersBodyElement = this._exchangersContainerElement.querySelector('#exchangers-body');

        this._periodSubmitElement = this._periodModalElement.querySelector('#period-submit');

        this._buyRateChartElement = this._chartModalElement.querySelector('#buy-rate-chart');
        this._saleRateChartElement = this._chartModalElement.querySelector('#sale-rate-chart');
        this._buyRateChartToggleElement = this._chartModalElement.querySelector('#buy-rate-chart-toggle');
        this._saleRateChartToggleElement = this._chartModalElement.querySelector('#sale-rate-chart-toggle');

        this._alertManager = new AlertManager(
            this._alertsContainerElement.querySelector('div.row > div')
        );
        this._spinnerModal = bootstrap.Modal.getOrCreateInstance(
            this._spinnerModalElement,
            {'backdrop': 'static', 'focus': true, 'keyboard': false}
        );
        this._periodModal = bootstrap.Modal.getOrCreateInstance(
            this._periodModalElement,
            {'backdrop': 'static', 'focus': true, 'keyboard': true}
        );
        this._chartModal =  bootstrap.Modal.getOrCreateInstance(
            this._chartModalElement,
            {'backdrop': 'static', 'focus': true, 'keyboard': true}
        );
        this._periodValidator = new Validator(
            this._periodModalElement.querySelector('form'),
            {
                'validators': {
                    'start_at': {
                        'callback': function (element) {
                            const value = element.value.trim(),
                                regexp = new RegExp(/^(0[1-9]|[12][0-9]|3[01])\.(0[1-9]|1[0-2])\.(\d{4})$/);

                            if (value === '') {
                                return {
                                    'valid': false,
                                    'message': 'Поле обовʼязкове для заповнення.'
                                };
                            }

                            if (!(regexp.test(value))) {
                                return {
                                    'valid': false,
                                    'message': 'Поле має місити коректну дату.'
                                };
                            }

                            const [day, month, year] = value.split('.').map(function (item) {
                                    return parseInt(item);
                                }),
                                monthIndex = month - 1,
                                startAt = new Date(year, monthIndex, day, 0, 0, 0, 0);

                            if (startAt.getFullYear() !== year || startAt.getMonth() !== monthIndex || startAt.getDate() !== day) {
                                return {
                                    'valid': false,
                                    'message': 'Поле має місити коректну дату.'
                                };
                            }

                            const endAtValue = this.getElement('end_at').value.trim();

                            if (endAtValue !== '' && regexp.test(endAtValue)) {
                                const [day, month, year] = endAtValue.split('.').map(function (item) {
                                        return parseInt(item);
                                    }),
                                    monthIndex = month - 1,
                                    endAt = new Date(year, monthIndex, day, 0, 0, 0, 0);

                                if (endAt.getFullYear() !== year || endAt.getMonth() !== monthIndex || endAt.getDate() !== day) {
                                    this.setInvalidField(
                                        'end_at',
                                        'Поле має місити коректну дату.'
                                    );
                                } else {
                                    if (startAt.getTime() > endAt.getTime()) {
                                        return {
                                            'valid': false,
                                            'message': 'Дата не може бути більшою дати закінчення періоду.'
                                        };
                                    } else {
                                        this.setValidField('end_at');
                                    }
                                }
                            }

                            return {
                                'valid': true
                            };
                        }
                    },
                    'end_at': {
                        'callback': function (element) {
                            const value = element.value.trim(),
                                regexp = new RegExp(/^(0[1-9]|[12][0-9]|3[01])\.(0[1-9]|1[0-2])\.(\d{4})$/);

                            if (value === '') {
                                return {
                                    'valid': false,
                                    'message': 'Поле обовʼязкове для заповнення.'
                                };
                            }

                            if (!(regexp.test(value))) {
                                return {
                                    'valid': false,
                                    'message': 'Поле має місити коректну дату.'
                                };
                            }

                            const [day, month, year] = value.split('.').map(function (item) {
                                    return parseInt(item);
                                }),
                                monthIndex = month - 1,
                                endAt = new Date(year, monthIndex, day, 0, 0, 0, 0);

                            if (endAt.getFullYear() !== year || endAt.getMonth() !== monthIndex || endAt.getDate() !== day) {
                                return {
                                    'valid': false,
                                    'message': 'Поле має місити коректну дату.'
                                };
                            }

                            const startAtValue = this.getElement('start_at').value.trim();

                            if (startAtValue !== '' && regexp.test(startAtValue)) {
                                const [day, month, year] = startAtValue.split('.').map(function (item) {
                                        return parseInt(item);
                                    }),
                                    monthIndex = month - 1,
                                    startAt = new Date(year, monthIndex, day, 0, 0, 0, 0);

                                if (startAt.getFullYear() !== year || startAt.getMonth() !== monthIndex || startAt.getDate() !== day) {
                                    this.setInvalidField(
                                        'start_at',
                                        'Поле має місити коректну дату.'
                                    );
                                } else {
                                    if (endAt.getTime() < startAt.getTime()) {
                                        return {
                                            'valid': false,
                                            'message': 'Дата не може бути меншою дати початку періоду.'
                                        };
                                    } else {
                                        this.setValidField('start_at');
                                    }
                                }
                            }

                            return {
                                'valid': true
                            };
                        }
                    }
                },
                'onSubmitEventHandler': function (e) {
                    e.preventDefault();

                    if (this._periodValidator.isValidForm()) {
                        this._periodModalElement.addEventListener('hidden.bs.modal', function (e) {
                            this._spinnerModalElement.addEventListener('shown.bs.modal', function (e) {
                                    const urlSearchParams = new URLSearchParams();

                                    urlSearchParams.set(
                                        'rate_ids',
                                        (function (elements) {
                                            const rateIds = [];

                                            elements.forEach(function (element) {
                                                rateIds.push(element.getAttribute('data-rate-id'));
                                            });

                                            return rateIds.join(',');
                                        })(this._exchangersBodyElement.querySelectorAll('div[data-rate-id].text-bg-success.bg-gradient'))
                                    );

                                    urlSearchParams.set(
                                        'start_at',
                                        (function (value) {
                                            const [day, month, year] = value
                                                    .split('.')
                                                    .map(function (item) {
                                                        return parseInt(item);
                                                    }),
                                                monthIndex = month - 1,
                                                date = new Date(Date.UTC(year, monthIndex, day, 0, 0, 0, 0));

                                            return date.toISOString().slice(0, -1) + '0'.repeat(3);
                                        })(this._periodValidator.getElement('start_at').value.trim())
                                    );

                                    urlSearchParams.set(
                                        'end_at',
                                        (function (value) {
                                            const [day, month, year] = value
                                                    .split('.')
                                                    .map(function (item) {
                                                        return parseInt(item);
                                                    }),
                                                monthIndex = month - 1,
                                                date = new Date(Date.UTC(year, monthIndex, day, 23, 59, 59, 999));

                                            return date.toISOString().slice(0, -1) + '9'.repeat(3);
                                        })(this._periodValidator.getElement('end_at').value.trim())
                                    );

                                    const timeout = 30000,
                                        abortController = new AbortController(),
                                        url = '/api/exchanger-archive-rates?' + urlSearchParams.toString(),
                                        options = {
                                            signal: abortController.signal,
                                            method: 'GET',
                                            headers: {
                                                'Accept': 'application/json',
                                                'Content-Type': 'application/json'
                                            }
                                        },
                                        timeoutId = setTimeout(function (abortController) {
                                            abortController.abort();
                                        }, timeout, abortController);

                                    fetch(url, options)
                                        .then(function (response) {
                                            if (response.status !== HTTP_STATUS_CODE_OK) {
                                                return Promise.reject(response);
                                            }

                                            return response.json();
                                        })
                                        .then(function (exchangers) {
                                            return exchangers.map(function (exchanger) {
                                                return new Exchanger(
                                                    exchanger.id,
                                                    exchanger.rates.map(function (rate) {
                                                        return new Rate(
                                                            rate.id,
                                                            rate.unit,
                                                            new Currency(
                                                                rate.unit_currency.alphabetic_code,
                                                                rate.unit_currency.decimal_place,
                                                                rate.unit_currency.name,
                                                                rate.unit_currency.name_uk,
                                                                new Date(rate.unit_currency.created_at),
                                                                new Date(rate.unit_currency.updated_at)
                                                            ),
                                                            new Currency(
                                                                rate.rate_currency.alphabetic_code,
                                                                rate.rate_currency.decimal_place,
                                                                rate.rate_currency.name,
                                                                rate.rate_currency.name_uk,
                                                                new Date(rate.rate_currency.created_at),
                                                                new Date(rate.rate_currency.updated_at)
                                                            ),
                                                            rate.buy_rate,
                                                            rate.sale_rate,
                                                            new Date(rate.created_at),
                                                            new Date(rate.updated_at)
                                                        );
                                                    }),
                                                    exchanger.name,
                                                    exchanger.name_uk,
                                                    new Date(exchanger.created_at),
                                                    new Date(exchanger.updated_at)
                                                );
                                            });
                                        })
                                        .then(function (exchangers) {
                                            if (exchangers.length === 0) {
                                                this._spinnerModalElement.addEventListener('hidden.bs.modal', function (e) {
                                                    this._periodModalElement.addEventListener('shown.bs.modal', function (e) {
                                                        this._alertManager.warning('Немає даних для перегляду за вибраний період.');
                                                    }.bind(this), {once: true});

                                                    this._periodModal.show();
                                                }.bind(this), {once: true});
                                            } else {
                                                const options = {
                                                        locale: 'uk-UA',
                                                        animation: false,
                                                        scales: {
                                                            x: {
                                                                type: 'linear',
                                                                ticks: {
                                                                    callback: function (value, index, values) {
                                                                        const createdAt = new Date(value);
                                                                        const dateTimeFormat = new Intl.DateTimeFormat(
                                                                            'uk-UA',
                                                                            {
                                                                                year: 'numeric',
                                                                                month: '2-digit',
                                                                                day: '2-digit'
                                                                            }
                                                                        );

                                                                        return dateTimeFormat.format(createdAt);
                                                                    },
                                                                    font: {
                                                                        family: 'SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", monospace'
                                                                    }
                                                                }
                                                            },
                                                            y: {
                                                                type: 'linear',
                                                                ticks: {
                                                                    font: {
                                                                        family: 'SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", monospace'
                                                                    }
                                                                }
                                                            }
                                                        },
                                                        plugins: {
                                                            legend: {
                                                                position: 'top',
                                                                labels: {
                                                                    font: {
                                                                        family: 'SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", monospace'
                                                                    }
                                                                }
                                                            },
                                                            colors: {
                                                                forceOverride: true
                                                            },
                                                            tooltip: {
                                                                callbacks: {
                                                                    title: function (context) {
                                                                        if (context.length > 0) {
                                                                            const createdAt = new Date(context[0].raw.x),
                                                                                dateTimeFormat = new Intl.DateTimeFormat(
                                                                                    'uk-UA',
                                                                                    {
                                                                                        year: 'numeric',
                                                                                        month: '2-digit',
                                                                                        day: '2-digit',
                                                                                        hour: '2-digit',
                                                                                        minute: '2-digit',
                                                                                        second: '2-digit'
                                                                                    }
                                                                                );

                                                                            return dateTimeFormat.format(createdAt);
                                                                        } else {
                                                                            return '';
                                                                        }
                                                                    },
                                                                    label: function(context) {
                                                                        const rateCurrency = context.raw.rate_currency,
                                                                            rateCurrencyDecimalPlace = rateCurrency.decimalPlace,
                                                                            rateNumberFormat = Intl.NumberFormat(
                                                                                'uk-UA',
                                                                                {
                                                                                    maximumFractionDigits: rateCurrencyDecimalPlace,
                                                                                    minimumFractionDigits: rateCurrencyDecimalPlace
                                                                                }
                                                                            );

                                                                        return context.dataset.label + ' '
                                                                            + rateNumberFormat.format(context.raw.y) + ' '
                                                                            + rateCurrency.alphabeticCode;
                                                                    }
                                                                },
                                                                titleFont: {
                                                                    family: 'SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", monospace'
                                                                },
                                                                bodyFont: {
                                                                    family: 'SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", monospace'
                                                                },
                                                                footerFont: {
                                                                    family: 'SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", monospace'
                                                                }
                                                            }
                                                        }
                                                    },
                                                    buyDatasets = [],
                                                    saleDatasets = [];

                                                exchangers.forEach(function (exchanger) {
                                                    const buyDatasetGroupedByUnitAlphabeticCode = {},
                                                        saleDatasetGroupedByUnitAlphabeticCode = {};

                                                    exchanger.rates.forEach(function (rate) {
                                                        const unitCurrency = rate.unitCurrency,
                                                            rateCurrency = rate.rateCurrency,
                                                            unitAlphabeticCode = unitCurrency.alphabeticCode;

                                                        if (
                                                            typeof buyDatasetGroupedByUnitAlphabeticCode[unitAlphabeticCode] === 'undefined'
                                                                || typeof saleDatasetGroupedByUnitAlphabeticCode[unitAlphabeticCode] === 'undefined'
                                                        ) {
                                                            const unitCurrencyDecimalPlace = unitCurrency.decimalPlace,
                                                                unitNumberFormat = Intl.NumberFormat(
                                                                    'uk-UA',
                                                                    {
                                                                        maximumFractionDigits: unitCurrencyDecimalPlace,
                                                                        minimumFractionDigits: unitCurrencyDecimalPlace
                                                                    }
                                                                ),
                                                                label = exchanger.nameUk + ' (' + unitNumberFormat.format(rate.unit) + ' ' + unitAlphabeticCode + ')';

                                                            buyDatasetGroupedByUnitAlphabeticCode[unitAlphabeticCode] = {
                                                                label: label,
                                                                data: []
                                                            };

                                                            saleDatasetGroupedByUnitAlphabeticCode[unitAlphabeticCode] = {
                                                                label: label,
                                                                data: []
                                                            };
                                                        }

                                                        buyDatasetGroupedByUnitAlphabeticCode[unitAlphabeticCode].data.push({
                                                            x: rate.createdAt.valueOf(),
                                                            y: rate.buyRate,
                                                            rate_currency: rateCurrency
                                                        });
                                                        buyDatasetGroupedByUnitAlphabeticCode[unitAlphabeticCode].data.push({
                                                            x: rate.updatedAt.valueOf(),
                                                            y: rate.buyRate,
                                                            rate_currency: rateCurrency
                                                        });
                                                        saleDatasetGroupedByUnitAlphabeticCode[unitAlphabeticCode].data.push({
                                                            x: rate.createdAt.valueOf(),
                                                            y: rate.saleRate,
                                                            rate_currency: rateCurrency
                                                        });
                                                        saleDatasetGroupedByUnitAlphabeticCode[unitAlphabeticCode].data.push({
                                                            x: rate.updatedAt.valueOf(),
                                                            y: rate.saleRate,
                                                            rate_currency: rateCurrency
                                                        });
                                                    });

                                                    for (let unitAlphabeticCode in buyDatasetGroupedByUnitAlphabeticCode) {
                                                        buyDatasets.push(
                                                            buyDatasetGroupedByUnitAlphabeticCode[unitAlphabeticCode]
                                                        );

                                                        saleDatasets.push(
                                                            saleDatasetGroupedByUnitAlphabeticCode[unitAlphabeticCode]
                                                        );
                                                    }
                                                });

                                                new Chart(this._buyRateChartElement, {
                                                    type: 'line',
                                                    data: {
                                                        labels: [],
                                                        datasets: buyDatasets
                                                    },
                                                    options: options
                                                });

                                                new Chart(this._saleRateChartElement, {
                                                    type: 'line',
                                                    data: {
                                                        labels: [],
                                                        datasets: saleDatasets
                                                    },
                                                    options: options
                                                });

                                                this._spinnerModalElement.addEventListener('hidden.bs.modal', function (e) {
                                                    this._chartModal.show();
                                                }.bind(this), {once: true});
                                            }

                                            this._spinnerModal.hide();
                                        }.bind(this))
                                        .catch(function (error) {
                                            if ((error instanceof DOMException) && error.name === 'AbortError') {
                                                this._spinnerModalElement.addEventListener('hidden.bs.modal', function (e) {
                                                    this._periodModalElement.addEventListener('shown.bs.modal', function (e) {
                                                        this._alertManager.warning('Час очікування запиту вичерпано. Будь ласка, спробуйте ще раз.');
                                                    }.bind(this), {once: true});

                                                    this._periodModal.show();
                                                }.bind(this), {once: true});
                                            } else {
                                                this._spinnerModalElement.addEventListener('hidden.bs.modal', function (e) {
                                                    this._alertManager.danger('Щось пішло не так. Оновіть сторінку та повторіть спробу.');

                                                    this._alertsContainerElement.focus();
                                                }.bind(this), {once: true});
                                            }

                                            this._spinnerModal.hide();
                                        }.bind(this))
                                        .finally(function () {
                                            clearTimeout(timeoutId);
                                        });
                            }.bind(this), {once: true});

                            this._spinnerModal.show();
                        }.bind(this), {once: true});

                        this._periodModal.hide();
                    } else {
                        this._periodValidator.setFocusOnFirstInvalidField();
                    }
                }.bind(this)
            }
        );

        this._resizeWindowListener = function (e) {
            [
                this._buyRateChartElement,
                this._saleRateChartElement
            ].forEach(function (chartElement) {
                const chart = Chart.getChart(chartElement);

                if (typeof chart !== 'undefined' ) {
                    chart.resize();
                }
            });

            const container = this._exchangersContainerElement,
                card = this._exchangersContainerElement.querySelector('.card'),
                header = card.querySelector('.card-header'),
                body = card.querySelector('.card-body'),
                footer = card.querySelector('.card-footer'),
                windowHeight = Math.floor(window.innerHeight),
                headerHeight = Math.ceil(header.getBoundingClientRect().height),
                footerHeight = Math.ceil(footer.getBoundingClientRect().height),
                containerStyles = window.getComputedStyle(container),
                paddingTop = Math.ceil(parseFloat(containerStyles.paddingTop)),
                paddingBottom = Math.ceil(parseFloat(containerStyles.paddingBottom)),
                cardStyles = window.getComputedStyle(card),
                borderTopWidth = Math.ceil(parseFloat(cardStyles.borderTopWidth)),
                borderBottomWidth = Math.ceil(parseFloat(cardStyles.borderBottomWidth)),
                maxBodyHeight = Math.floor(
                    windowHeight - (headerHeight + footerHeight + paddingTop + paddingBottom + borderTopWidth + borderBottomWidth)
                );

            body.style.maxHeight = maxBodyHeight + 'px';
        }.bind(this);

        this._clickPeriodSubmitElementListener = function (e) {
            e.preventDefault();

            this._periodValidator.getFormSubmitButton().click();
        }.bind(this);

        this._clickPeriodLinkListener = function (e) {
            e.preventDefault();

            if (
                this._exchangersBodyElement
                    .querySelectorAll('div[data-rate-id].text-bg-success.bg-gradient')
                    .length === 0
            ) {
                this._alertManager.warning('Будь ласка, оберіть курси валют для перегляду.');

                this._alertsContainerElement.focus();
            } else {
                const dateTimeFormat = new Intl.DateTimeFormat(
                    'uk-UA',
                    {
                        year: 'numeric',
                        month: '2-digit',
                        day: '2-digit'
                    }
                );

                this._periodValidator.getElement('start_at').value = (function (dateTimeFormat) {
                    const startAt = new Date();

                    startAt.setMilliseconds(0);
                    startAt.setSeconds(0);
                    startAt.setMinutes(0);
                    startAt.setHours(0);
                    startAt.setDate(1);
                    startAt.setMonth(startAt.getMonth() - 1);

                    return dateTimeFormat.format(startAt);
                })(dateTimeFormat);

                this._periodValidator.getElement('end_at').value = (function (dateTimeFormat) {
                    const endAt = new Date();

                    endAt.setMilliseconds(999);
                    endAt.setSeconds(59);
                    endAt.setMinutes(59);
                    endAt.setHours(23);

                    return dateTimeFormat.format(endAt);
                })(dateTimeFormat);

                this._periodValidator.clearFormValidation();

                this._periodModal.show();
            }
        }.bind(this);

        this._clickReloadLinkListener = function (e) {
            e.preventDefault();

            this._spinnerModalElement.addEventListener('shown.bs.modal', function (e) {
                this._cleanExchangersBodyRows();

                const timeout = 30000,
                    abortController = new AbortController(),
                    url = '/api/exchanger-rates',
                    options = {
                        signal: abortController.signal,
                        method: 'GET',
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        }
                    },
                    timeoutId = setTimeout(function (abortController) {
                        abortController.abort();
                    }, timeout, abortController);

                fetch(url, options)
                    .then(function (response) {
                        if (response.status !== HTTP_STATUS_CODE_OK) {
                            return Promise.reject(response);
                        }

                        return response.json();
                    })
                    .then(function (exchangers) {
                        return exchangers.map(function (exchanger) {
                            return new Exchanger(
                                exchanger.id,
                                exchanger.rates.map(function (rate) {
                                    return new Rate(
                                        rate.id,
                                        rate.unit,
                                        new Currency(
                                            rate.unit_currency.alphabetic_code,
                                            rate.unit_currency.decimal_place,
                                            rate.unit_currency.name,
                                            rate.unit_currency.name_uk,
                                            new Date(rate.unit_currency.created_at),
                                            new Date(rate.unit_currency.updated_at)
                                        ),
                                        new Currency(
                                            rate.rate_currency.alphabetic_code,
                                            rate.rate_currency.decimal_place,
                                            rate.rate_currency.name,
                                            rate.rate_currency.name_uk,
                                            new Date(rate.rate_currency.created_at),
                                            new Date(rate.rate_currency.updated_at)
                                        ),
                                        rate.buy_rate,
                                        rate.sale_rate,
                                        new Date(rate.created_at),
                                        new Date(rate.updated_at)
                                    );
                                }),
                                exchanger.name,
                                exchanger.name_uk,
                                new Date(exchanger.created_at),
                                new Date(exchanger.updated_at)
                            );
                        });
                    })
                    .then(function (exchangers) {
                        this._addExchangersBodyRows(exchangers);

                        this._spinnerModalElement.addEventListener('hidden.bs.modal', function (e) {
                            this._exchangersContainerElement.focus();
                        }.bind(this), {once: true});

                        this._spinnerModal.hide();
                    }.bind(this))
                    .catch(function (error) {
                        if ((error instanceof DOMException) && error.name === 'AbortError') {
                            this._spinnerModalElement.addEventListener('hidden.bs.modal', function (e) {
                                this._alertManager.warning('Час очікування запиту вичерпано. Будь ласка, спробуйте ще раз.');

                                this._alertsContainerElement.focus();
                            }.bind(this), {once: true});
                        } else {
                            this._spinnerModalElement.addEventListener('hidden.bs.modal', function (e) {
                                this._alertManager.danger('Щось пішло не так. Оновіть сторінку та повторіть спробу.');

                                this._alertsContainerElement.focus();
                            }.bind(this), {once: true});
                        }

                        this._spinnerModal.hide();
                    }.bind(this))
                    .finally(function () {
                        clearTimeout(timeoutId);
                    });
            }.bind(this), {once: true});

            this._spinnerModal.show();
        }.bind(this);

        this._shownPeriodModalElementListener = function (e) {
            this._periodValidator.isValidForm()
                ? this._periodValidator.setFocusOnFirstField()
                : this._periodValidator.setFocusOnFirstInvalidField();
        }.bind(this);

        this._clickRateRowListener = function (e) {
            e.preventDefault();

            const element = e.currentTarget;

            if (element.classList.contains('text-bg-success') || element.classList.contains('bg-gradient')) {
                element.classList.remove('text-bg-success', 'bg-gradient');
            } else {
                element.classList.add('text-bg-success', 'bg-gradient');
            }
        }.bind(this);

        this._hiddenChartModalElementListener = function (e) {
            [
                Chart.getChart(this._buyRateChartElement),
                Chart.getChart(this._saleRateChartElement)
            ].forEach(function (chart) {
                if (typeof chart !== 'undefined') {
                    chart.destroy();
                }
            });
        }.bind(this);

        this._shownChartModalElementListener = function (e) {
            this._buyRateChartToggleElement.click();
        }.bind(this);

        this._clickBuyRateChartToggleElementListener = function (e) {
            e.preventDefault();

            this._saleRateChartElement.closest('div').classList.add('d-none');
            this._buyRateChartElement.closest('div').classList.remove('d-none');

            const chart = Chart.getChart(this._buyRateChartElement);

            if (typeof chart !== 'undefined') {
                chart.render();
            }

            this._buyRateChartToggleElement.closest('div').classList.add('d-none');
            this._saleRateChartToggleElement.closest('div').classList.remove('d-none');

            this._chartModalElement.querySelector('div.modal-header > h5').textContent = this._buyRateChartToggleElement.textContent;

            this._chartModalElement.focus();
        }.bind(this);

        this._clickSaleRateChartToggleElementListener = function (e) {
            e.preventDefault();

            this._buyRateChartElement.closest('div').classList.add('d-none');
            this._saleRateChartElement.closest('div').classList.remove('d-none');

            const chart = Chart.getChart(this._saleRateChartElement);

            if (typeof chart !== 'undefined') {
                chart.render();
            }

            this._saleRateChartToggleElement.closest('div').classList.add('d-none');
            this._buyRateChartToggleElement.closest('div').classList.remove('d-none');

            this._chartModalElement.querySelector('div.modal-header > h5').textContent = this._saleRateChartToggleElement.textContent;

            this._chartModalElement.focus();
        }.bind(this);
    }

    run() {
        this._periodLinkElement.addEventListener('click', this._clickPeriodLinkListener);
        this._reloadLinkElement.addEventListener('click', this._clickReloadLinkListener);
        this._periodSubmitElement.addEventListener('click', this._clickPeriodSubmitElementListener);
        this._buyRateChartToggleElement.addEventListener('click', this._clickBuyRateChartToggleElementListener);
        this._saleRateChartToggleElement.addEventListener('click', this._clickSaleRateChartToggleElementListener);
        this._chartModalElement.addEventListener('shown.bs.modal', this._shownChartModalElementListener);
        this._periodModalElement.addEventListener('shown.bs.modal', this._shownPeriodModalElementListener);
        this._chartModalElement.addEventListener('hidden.bs.modal', this._hiddenChartModalElementListener);

        window.addEventListener('resize', this._resizeWindowListener);

        window.dispatchEvent(new Event('resize'));

        this._reloadLinkElement.click();
    }

    _addExchangersBodyRows(exchangers) {
        exchangers.forEach(function (exchanger) {
            this._exchangersBodyElement.append(this._getExchangerBodyRowElement(exchanger));
        }.bind(this));
    }

    _cleanExchangersBodyRows() {
        while (this._exchangersBodyElement.childNodes.length > 0) {
            const rateRow = this._exchangersBodyElement.querySelector('div[data-rate-id]');

            if (rateRow !== null) {
                rateRow.removeEventListener('click', this._clickRateRowListener);
            }

            this._exchangersBodyElement.removeChild(this._exchangersBodyElement.firstChild);
        }
    }

    _getExchangerBodyRowElement(exchanger) {
        const element = document.createElement('div');

        element.classList.add('row');

        element.setAttribute('data-exchanger-id', exchanger.id);

        element.append((function (exchanger) {
            const element = document.createElement('div');

            element.classList.add(
                'col-4', 'text-start', 'd-flex', 'justify-content-start', 'align-items-center', 'px-2',
                'py-2', 'border-bottom', 'border-end'
            );

            element.append(document.createTextNode(exchanger.nameUk));

            return element;
        })(exchanger));

        element.append((function (exchanger) {
            const element = document.createElement('div');

            element.classList.add('col-8');

            exchanger.rates.forEach(function (rate) {
                element.append((function (rate) {
                    const element = document.createElement('div'),
                        unitCurrency = rate.unitCurrency,
                        rateCurrency = rate.rateCurrency,
                        unitCurrencyDecimalPlace = unitCurrency.decimalPlace,
                        rateCurrencyDecimalPlace = rateCurrency.decimalPlace,
                        unitCurrencyAlphabeticCode = unitCurrency.alphabeticCode,
                        rateCurrencyAlphabeticCode = rateCurrency.alphabeticCode,
                        unitFormatter = Intl.NumberFormat(
                            'uk-UA',
                            {
                                maximumFractionDigits: unitCurrencyDecimalPlace,
                                minimumFractionDigits: unitCurrencyDecimalPlace
                            }
                        ),
                        rateFormatter = Intl.NumberFormat(
                            'uk-UA',
                            {
                                maximumFractionDigits: rateCurrencyDecimalPlace,
                                minimumFractionDigits: rateCurrencyDecimalPlace
                            }
                        );

                    element.classList.add('row', 'border-bottom');

                    element.setAttribute('data-rate-id', rate.id);
                    element.setAttribute('role', 'button');

                    element.append((function () {
                        const element = document.createElement('div');

                        element.setAttribute(
                            'data-unit-currency-alphabetic-code',
                            unitCurrencyAlphabeticCode
                        );

                        element.classList.add(
                            'col-3', 'text-end', 'd-flex', 'justify-content-end', 'align-items-center', 'px-2',
                            'py-2', 'border-end'
                        );

                        element.append(document.createTextNode(
                            unitFormatter.format(rate.unit) + ' ' + unitCurrencyAlphabeticCode)
                        );

                        return element;
                    })());

                    [
                        rateFormatter.format(rate.buyRate) + ' ' + rateCurrencyAlphabeticCode,
                        rateFormatter.format(rate.saleRate) + ' ' + rateCurrencyAlphabeticCode
                    ].forEach(function (item) {
                        element.append((function () {
                            const element = document.createElement('div');

                            element.setAttribute(
                                'data-rate-currency-alphabetic-code',
                                rateCurrencyAlphabeticCode
                            );

                            element.classList.add(
                                'col-3', 'text-end', 'd-flex', 'justify-content-end', 'align-items-center', 'px-2',
                                'py-2', 'border-end'
                            );

                            element.append(document.createTextNode(item));

                            return element;
                        })());
                    });

                    element.append((function () {
                        const element = document.createElement('div'),
                            dateTimeFormat = new Intl.DateTimeFormat(
                                'uk-UA',
                                {
                                    year: 'numeric',
                                    month: '2-digit',
                                    day: '2-digit',
                                    hour: '2-digit',
                                    minute: '2-digit',
                                    second: '2-digit'
                                }
                            );

                        element.classList.add(
                            'col-3', 'text-center', 'd-flex', 'justify-content-center', 'align-items-center', 'px-2',
                            'py-2'
                        );

                        element.append(document.createTextNode(dateTimeFormat.format(rate.updatedAt)));

                        return element;
                    }.bind(this))());

                    element.addEventListener('click', this._clickRateRowListener);

                    return element;
                }.bind(this))(rate));
            }.bind(this));

            return element;
        }.bind(this))(exchanger));

        return element;
    }
}