package cz.akineta.feedback.domain.rest.ui;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
@RunWith(Arquillian.class)
@RunAsClient
public class FeedbackScreenTest
{
	@Drone
	private WebDriver driver;
	@Page
	private FeedbackPage feedbackPage;

	@Test
	public void pageTitleIsCorrect(@InitialPage FeedbackPage feedbackPage)
	{
		assertThat(feedbackPage.getTitle(), is("Gimme feedback"));
	}

	@Test
	public void addNewFeedbackSpamIsOkSuccessfully(@InitialPage FeedbackPage feedbackPage)
	{
		feedbackPage.addFeedback("Peter Jimbosh", "peter@jimbosh.com", "New comment added - agreed with spam.", true);
		assertThat(feedbackPage.commentExists("Peter Jimbosh", "peter@jimbosh.com", "New comment added - agreed with spam.", true), is(true));
	}

	@Test
	public void addNewFeedbackSpamIsNokSuccessfully(@InitialPage FeedbackPage feedbackPage)
	{
		feedbackPage.addFeedback("Peter Jimbosh", "peter@jimbosh.com", "New comment added - not agreed with spam.", false);
		assertThat(feedbackPage.commentExists("Peter Jimbosh", "peter@jimbosh.com", "New comment added - not agreed with spam.", false), is(true));
	}

	@Test
	public void addNewFeedbackIvalidName(@InitialPage FeedbackPage feedbackPage)
	{
		feedbackPage.addFeedback("", "ivalid email", "Robur has added some comment.", true);
		assertThat(feedbackPage.commentExists("", "ivalid email", "Robur has added some comment.", true), is(false));
	}

	@Test
	public void addNewFeedbackIvalidEmail(@InitialPage FeedbackPage feedbackPage)
	{
		feedbackPage.addFeedback("Robur Dobyvatel", "ivalid email", "Robur has added some comment.", true);
		assertThat(feedbackPage.commentExists("Robur Dobyvatel", "ivalid email", "Robur has added some comment.", true), is(false));
	}

	@Test
	public void addNewFeedbackIvalidMessage(@InitialPage FeedbackPage feedbackPage)
	{
		feedbackPage.addFeedback("Robur Dobyvatel", "valid@email", "", true);
		assertThat(feedbackPage.commentExists("Robur Dobyvatel", "valid@email", "", true), is(false));
	}

	@Test
	public void jsInjection(@InitialPage FeedbackPage feedbackPage)
	{
		feedbackPage.addFeedback("<script type=\"text/javascript\">alert('Hi.')</script>", "valid@email", "Robur has added some comment.", true);
		assertThat(feedbackPage.commentExists("<script type=\"text/javascript\">alert('Hi.')</script>", "valid@email", "Robur has added some comment.", true), is(true));
	}

	@Test
	@Ignore
	public void sqlInjection(@InitialPage FeedbackPage feedbackPage)
	{
		//TODO:
	}

}
