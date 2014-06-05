package cz.akineta.feedback.domain.rest;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
public class FeedbackValidationException extends RuntimeException
{

	private static final long serialVersionUID = 1L;
	private final List<FieldError> fieldErrors;

	public FeedbackValidationException(List<FieldError> fieldErrors)
	{
		super();
		this.fieldErrors = fieldErrors;
	}

	public List<String> getLocalizedMessages()
	{
		List<String> l10nErrorMessages = new ArrayList<>();

		for (FieldError fieldError : fieldErrors)
		{
			l10nErrorMessages.add(fieldError.getDefaultMessage());
		}

		return l10nErrorMessages;
	}

}
