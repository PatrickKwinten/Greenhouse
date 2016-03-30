package com.quintessens.greenhouse;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.apache.commons.validator.routines.CreditCardValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.ISBNValidator;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.apache.commons.validator.routines.UrlValidator;

public class Validators implements Serializable {
	private static final long serialVersionUID = 7300894679566868915L;

	public void validateEmail(FacesContext facesContext, UIComponent component, Object value) {
		EmailValidator emailValidator = EmailValidator.getInstance();
		if (!emailValidator.isValid(value.toString())) {
			FacesMessage message = new FacesMessage("Email is invalid.");
			throw new ValidatorException(message);
		}
	}

	public void validateIPAddress(FacesContext facesContext, UIComponent component, Object value) {
		InetAddressValidator ipValidator = InetAddressValidator.getInstance();
		if (!ipValidator.isValid(value.toString())) {
			FacesMessage message = new FacesMessage("IP address is invalid.");
			throw new ValidatorException(message);
		}
	}

	public void validateUrl(FacesContext facesContext, UIComponent component, Object value) {
		UrlValidator urlValidator = UrlValidator.getInstance();
		if (!urlValidator.isValid(value.toString())) {
			FacesMessage message = new FacesMessage("URL is invalid.");
			throw new ValidatorException(message);
		}
	}

	public void validateISBN(FacesContext facesContext, UIComponent component, Object value) {
		ISBNValidator isbnValidator = ISBNValidator.getInstance();
		if (!isbnValidator.isValid(value.toString())) {
			FacesMessage message = new FacesMessage("ISBN is invalid.");
			throw new ValidatorException(message);
		}
	}

	public void validateAnyCreditCard(FacesContext facesContext, UIComponent component, Object value) {
		CreditCardValidator ccValidator = new CreditCardValidator(CreditCardValidator.AMEX + CreditCardValidator.DINERS + CreditCardValidator.DISCOVER + CreditCardValidator.MASTERCARD + CreditCardValidator.VISA);
		if (!ccValidator.isValid(value.toString())) {
			FacesMessage message = new FacesMessage("Credit card number is invalid.");
			throw new ValidatorException(message);
		}
	}

	public void validateAmexCreditCard(FacesContext facesContext, UIComponent component, Object value) {
		CreditCardValidator ccValidator = new CreditCardValidator(CreditCardValidator.AMEX);
		if (!ccValidator.isValid(value.toString())) {
			FacesMessage message = new FacesMessage("American Express credit card number is invalid.");
			throw new ValidatorException(message);
		}
	}

	public void validateDinersCreditCard(FacesContext facesContext, UIComponent component, Object value) {
		CreditCardValidator ccValidator = new CreditCardValidator(CreditCardValidator.DINERS);
		if (!ccValidator.isValid(value.toString())) {
			FacesMessage message = new FacesMessage("Credit card number is invalid.");
			throw new ValidatorException(message);
		}
	}

	public void validateDiscoverCreditCard(FacesContext facesContext, UIComponent component, Object value) {
		CreditCardValidator ccValidator = new CreditCardValidator(CreditCardValidator.DISCOVER);
		if (!ccValidator.isValid(value.toString())) {
			FacesMessage message = new FacesMessage("Discover credit card number is invalid.");
			throw new ValidatorException(message);
		}
	}

	public void validateMasterCardCreditCard(FacesContext facesContext, UIComponent component, Object value) {
		CreditCardValidator ccValidator = new CreditCardValidator(CreditCardValidator.MASTERCARD + CreditCardValidator.VISA);
		if (!ccValidator.isValid(value.toString())) {
			FacesMessage message = new FacesMessage("MasterCard credit card number is invalid.");
			throw new ValidatorException(message);
		}
	}

	public void validateVisaCreditCard(FacesContext facesContext, UIComponent component, Object value) {
		CreditCardValidator ccValidator = new CreditCardValidator(CreditCardValidator.VISA);
		if (!ccValidator.isValid(value.toString())) {
			FacesMessage message = new FacesMessage("Visa credit card number is invalid.");
			throw new ValidatorException(message);
		}
	}
}