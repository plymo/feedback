package cz.akineta.feedback.domain.rest.ui;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.jboss.arquillian.graphene.Graphene.waitGui;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
@Location("http://devbox/feedback?locale=en")
public class FeedbackPage
{

	private static final String FEEDBACK_FORM_ID = "feedbackForm";
	private static final String FEEDBACK_COMMENTS_ID = "feedbackComments";
	private static final String FEEDBACK_COMMENTS_EXISTS_XPATH = "//ul[@id='" + FEEDBACK_COMMENTS_ID + "']/li/div[starts-with(@class,'row')]";
	@Drone
	private WebDriver driver;
	@FindBy(id = FEEDBACK_FORM_ID)
	private FeedbackFormFragment feedbackForm;
	@FindBy(id = FEEDBACK_COMMENTS_ID)
	private List<FeedbackCommentFragment> feedbackComments;

	public String getTitle()
	{
		return driver.getTitle();
	}

	public void waitForFeedbackFormToDisplay()
	{
		waitGui().until().element(By.id(FEEDBACK_FORM_ID)).is().visible();
	}

	public FeedbackFormFragment getForm()
	{
		waitForFeedbackFormToDisplay();

		return feedbackForm;
	}

	public void addFeedback(String name, String email, String message, boolean agreedWithSpam)
	{
		waitForFeedbackFormToDisplay();
		feedbackForm.addFeedback(name, email, message, agreedWithSpam);
	}

	public List<FeedbackCommentFragment> getComments()
	{
		waitForCommentsToDisplay();

		return feedbackComments;
	}

	public void waitForCommentsToDisplay()
	{
		waitGui().until().element(By.xpath(FEEDBACK_COMMENTS_EXISTS_XPATH)).is().present();
	}

	public boolean commentExists(String name, String email, String message, boolean agreedWithSpam)
	{
		for (FeedbackCommentFragment commentFragment : getComments())
		{
			if (name.equals(commentFragment.getName()) && email.equals(commentFragment.getEmail())
					&& message.equals(commentFragment.getMessage()) && agreedWithSpam == commentFragment.getAgreedWithSpam())
			{
				return true;
			}
		}

		return false;
	}

}
