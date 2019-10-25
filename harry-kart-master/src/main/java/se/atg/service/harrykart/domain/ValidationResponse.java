package se.atg.service.harrykart.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;

public class ValidationResponse {

	private ValidationStatus status;

	private Object response;

	private List<ErrorMessage> errorMessageList;

	private String errorMessage;

	private String infoMessage;

	private String alertMessage;

	private Object additionalResponse;

	public ValidationResponse() {
		super();
	}

//	/**
//	 *
//	 * @param messageHandler
//	 * @param languageFile
//	 */
//	public void getMessagesFromMessageHandler(MessageHandler messageHandler, LanguageFile languageFile){
//		if (messageHandler.getAlert()) {
//			this.setStatus(ValidationStatus.ALERT);
//			this.setAlertMessage(languageFile.getString(messageHandler.getAlertMessage()));
//		}
//		else if(messageHandler.getError()){
//			this.setStatus(ValidationStatus.ERROR);
//			this.setErrorMessage(languageFile.getString(messageHandler.getErrorMessage()));
//		}
//		else{
//			this.setStatus(ValidationStatus.SUCCESS);
//			this.setInfoMessage(getInfoMessage(messageHandler.getInfoMessages(), languageFile));
//		}
//	}

	public class ErrorMessage {
		private String fieldName;
		private String message;

		public ErrorMessage(String fieldName, String message) {
			this.fieldName = fieldName;
			this.message = message;
		}

		public String getFieldName() {
			return fieldName;
		}

		public String getMessage() {
			return message;
		}
	}

	public enum ValidationStatus {
		SUCCESS, ERROR, ALERT, PROMT
	};

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public void setErrorMessageList(List<FieldError> errors) {
		if (errorMessageList == null) {
			errorMessageList = new ArrayList<ErrorMessage>();
		}
		for (FieldError fieldError : errors) {
			errorMessageList.add(new ErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()));
		}
	}

	public List<ErrorMessage> getErrorMessageList() {
		return errorMessageList;
	}

	public ValidationStatus getStatus() {
		return status;
	}

	public void setStatus(ValidationStatus status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getInfoMessage() {
		return infoMessage;
	}

	public void setInfoMessage(String infoMessage) {
		this.infoMessage = infoMessage;
	}

	public Object getAdditionalResponse() {
		return additionalResponse;
	}

	public void setAdditionalResponse(Object additionalResponse) {
		this.additionalResponse = additionalResponse;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}

	public String getAlertMessage() {
		return alertMessage;
	}
}
