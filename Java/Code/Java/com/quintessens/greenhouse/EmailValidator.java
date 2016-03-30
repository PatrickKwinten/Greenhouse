package com.quintessens.greenhouse;

/**
* @author Dököll Solutions, Inc.
* @version 2010.12.30.2.30.PM
*/

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import lotus.domino.NotesException;
import lotus.domino.local.Database;
import lotus.domino.local.Document;
//Faces message for email validation/confirmation
//2012.02.08.2.03.PM
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

public class EmailValidator implements Validator{
	
	// declare variables
	private String LastName;
	private String FirstName;
	private String EmailAddress;
	
	// Added email confirmation
	// @author: Köll S. Cherizard
	// @version 2012.02.08.1.56.PM
	private String EmailConfirm;
	
	/**
	* @return the lastName
	*/
	public String getLastName() {
		return LastName;
	}
	
	/**
	* @param lastName
	* the lastName to set
	*/
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	
	/**
	* @return the firstName
	*/
	public String getFirstName() {
		return FirstName;
	}
	
	/**
	* @param firstName
	* the firstName to set
	*/
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	
	/**
	* @return the emailAddress
	*/
	public String getEmailAddress() {
		return EmailAddress;
	}
	
	/**
	* @param emailAddress the emailAddress to set
	*/
	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
	}
	
	/**
	* @return the emailConfirm
	*/
	public String getEmailConfirm() {
		return EmailConfirm;
	}
	
	/**
	* @param emailConfirm
	* the emailConfirm to set
	*/
	public void setEmailConfirm(String emailConfirm) {
		EmailConfirm = emailConfirm;
	}
	
	//NOTE: this email validation works best with Xpages
	//works where address is a @yourcompany.state.ny.us type
	//the built-in option, as far as I can see works only with @yourcompany.com, .net, and so on...
	//2012.02.14.10.00.AM
	public void validate(FacesContext facesContext, UIComponent uIComponent,Object object) throws ValidatorException {
		// @FacesValidator(value="SubscriptionBean") is needed up top
		// 2012.02.14.10.00.AM
		EmailConfirm = (String) object;
		EmailAddress = (String) object;
		
		// TODO modify built-in pattern to add below characters within Xpages
		//2012.02.14.10.23.AM
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");// \w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*
		Matcher cm = p.matcher(EmailConfirm);
		Matcher em = p.matcher(EmailAddress);
		boolean matchCFound = cm.matches();
		boolean matchEFound = em.matches();
		if (!matchCFound || !matchEFound) {
			FacesMessage message = new FacesMessage();
			
			// load message to Xpages form
			// 2012.02.14.9.40.AM
			message.setSummary("Invalid Email ID");
			throw new ValidatorException(message);
		}
	}
	
	//button code
	public void submitEntry() {
	// let's add a try catch here
	try {
	// get the current database being used
	Database database = (Database) FacesContext.getCurrentInstance()
	.getApplication().getVariableResolver().resolveVariable(
	FacesContext.getCurrentInstance(), "database");
	//document create call
	Document submitDocument = (Document) database.createDocument();
	// submit using Instructions form
	submitDocument.appendItemValue("form", "ContactForm");
	// using appendItemValue to insert in the fields needed
	// notice we are referencing the field on the xpage, and the
	// JavaBean variables
	submitDocument.appendItemValue("lastName", LastName);
	submitDocument.appendItemValue("firstName", FirstName);
	submitDocument.appendItemValue("emailAddress", EmailAddress);
	//Email Confirm catch: DO NOT let user submit form if email does not match
	//2012.02.14.10.00.AM
	if (EmailAddress.equalsIgnoreCase(EmailConfirm)) // {
		try {
		//load successful page ONLY if user email confirmation checks out okay
		//2012.02.14.10.00.AM
		FacesContext.getCurrentInstance().getExternalContext().redirect("../docu.nsf/xpsuccessful.xsp");// ./index.html
		//2012.02.29.3.00.PM
		// TODO Clear session so user does not re-enter data by hitting back button on Successful page
		//Added session Invalidate code
		//2012.03.02.2.21.PM
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(false);
		httpSession.invalidate();
		//saves the data, based on above fields to Form and ONLY if user email confirmation checks out okay
		//2012.02.14.10.00.AM
		submitDocument.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	else
		// try connecting to the confirm email Xpages form
		// should carry over the data from the previous page
		try {
			// call FacesContext to redirect to page in question
			//give user option to re-enter email
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../docu.nsf/xpconfirmemail.xsp");
			//DO NOT save this form, Unless email confirm works, send user back to try again
			return;
		} catch (IOException ex) {
		}
		
		// clean up the system
		submitDocument.recycle();
		// throwable initialized if there is an error, either in the field
		// or the query
		} catch (NotesException e) {
		// print this error to the server
		e.printStackTrace();
		}
	}
}
 
