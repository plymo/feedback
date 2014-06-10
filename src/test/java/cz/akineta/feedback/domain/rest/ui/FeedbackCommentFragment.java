package cz.akineta.feedback.domain.rest.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Date;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
public class FeedbackCommentFragment
{

	@FindBy(xpath = "//strong[1]")
	private WebElement nameElement;
	@FindBy(xpath = "//div[starts-with(@class,'title')]")
	private WebElement emailElement;
	@FindBy(xpath = "//div[starts-with(@class,'message')]")
	private WebElement messageElement;
	@FindBy(xpath = "//div[starts-with(@class,'title')]")
	private WebElement agreedWithSpamElement;
	@FindBy(xpath = "//strong[2]")
	private WebElement createdElement;

	public String getName()
	{
		return nameElement.getText();
	}

	public String getEmail()
	{
		String regexpPattern = "(.*)\\((.*) - (.*)\\)(.*)";

		return emailElement.getText().replaceAll(regexpPattern, "$2");
	}

	public String getMessage()
	{
		return messageElement.getText();
	}

	public boolean getAgreedWithSpam()
	{
		String regexpPattern = "(.*)\\((.*) - (.*)\\)(.*)";

		return emailElement.getText().replaceAll(regexpPattern, "$3").equalsIgnoreCase("spam is ok");
	}

	public Date getCreated()
	{
		//TODO: return LocalDateTime.parse(createdElement.getText(), DateTimeFormat.forPattern("dd-MMM-yy hh.mm.ss aa")).toDate();
		return new Date();
	}

}
