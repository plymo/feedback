package cz.akineta.feedback.domain.rest;

/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
public class FieldValidationError
{

	private final String fieldName;
	private final String l10nMessage;

	public FieldValidationError(String fieldName, String l10nMessage)
	{
		this.fieldName = fieldName;
		this.l10nMessage = l10nMessage;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public String getL10nMessage()
	{
		return l10nMessage;
	}

}
