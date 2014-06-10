package cz.akineta.feedback.domain.rest.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
public class FeedbackFormFragment
{

	@FindBy(id = "name")
	private WebElement nameElement;
	@FindBy(id = "email")
	private WebElement emailElement;
	@FindBy(id = "message")
	private WebElement messageElement;
	@FindBy(id = "agreedWithSpam")
	private WebElement agreedWithSpamElement;
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement submitButton;
	@FindBy(xpath = "//button[@type='reset']")
	private WebElement resetButton;

	public void fillName(String name)
	{
		nameElement.sendKeys(name);
	}

	public void fillEmail(String email)
	{
		emailElement.sendKeys(email);
	}

	public void fillMessage(String message)
	{
		messageElement.sendKeys(message);
	}

	public void fillAgreedWithSpam(boolean agreedWithSpam)
	{
		if (agreedWithSpamElement.isSelected() != agreedWithSpam)
		{
			agreedWithSpamElement.click();
		}
	}

	public void addFeedback(String name, String email, String message, boolean agreedWithSpam)
	{
		fillName(name);
		fillEmail(email);
		fillMessage(message);
		fillAgreedWithSpam(agreedWithSpam);
		submit();
	}

	public void submit()
	{
		//guardAjax(submitButton).click();
		submitButton.click();
	}

	public void reset()
	{
		resetButton.click();
	}

}
