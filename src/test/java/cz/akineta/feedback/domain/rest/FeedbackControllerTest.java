package cz.akineta.feedback.domain.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.akineta.feedback.config.MvcDispatcherConfig;
import cz.akineta.feedback.config.TestingApplicationConfig;
import cz.akineta.feedback.config.TestingThymeLeafViewResolverConfig;
import cz.akineta.feedback.domain.Feedback;
import cz.akineta.feedback.domain.FeedbackService;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestingApplicationConfig.class, MvcDispatcherConfig.class, TestingThymeLeafViewResolverConfig.class})
@WebAppConfiguration
@ActiveProfiles(profiles = "testing")
public class FeedbackControllerTest
{

	private final Date TEST_DATE_CREATED = DateTime.now().toDate();
	private MockMvc mockMvc;
	@Autowired
	protected WebApplicationContext wac;
	@Autowired
	private FeedbackService feedbackServiceMock;
	private Feedback firstFeedback;
	private Feedback secondFeedback;

	@Before
	public void setup()
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		Mockito.reset(feedbackServiceMock);

		doAnswer(new Answer()
		{
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable
			{
				Feedback feedback = (Feedback) invocation.getArguments()[0];
				feedback.setId(1L);
				feedback.setCreated(TEST_DATE_CREATED);

				return null;
			}
		}).when(feedbackServiceMock).save(any(Feedback.class));

		firstFeedback = createValidFeedback();
		firstFeedback.setId(1L);
		firstFeedback.setName("First");
		firstFeedback.setCreated(TEST_DATE_CREATED);

		secondFeedback = createValidFeedback();
		secondFeedback.setId(2L);
		secondFeedback.setName("Second");
		secondFeedback.setCreated(TEST_DATE_CREATED);

		when(feedbackServiceMock.findById(1L)).thenReturn(firstFeedback);
		when(feedbackServiceMock.findById(2L)).thenReturn(secondFeedback);
		when(feedbackServiceMock.findById(3L)).thenReturn(null);
		when(feedbackServiceMock.getFeedbackMessages(anyInt())).thenReturn(Arrays.asList(firstFeedback, secondFeedback));
	}

	@Test
	public void showFeedbackById() throws Exception
	{
		mockMvc.perform(get("/feedback/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(jsonPath("$.id", is(firstFeedback.getId().intValue())))
				.andExpect(jsonPath("$.name", is(firstFeedback.getName())))
				.andExpect(jsonPath("$.email", is(firstFeedback.getEmail())))
				.andExpect(jsonPath("$.message", is(firstFeedback.getMessage())))
				.andExpect(jsonPath("$.agreedWithSpam", is(firstFeedback.getAgreedWithSpam())))
				.andExpect(jsonPath("$.created", is(firstFeedback.getCreated().getTime())));

		verify(feedbackServiceMock, times(1)).findById(1L);
		verifyNoMoreInteractions(feedbackServiceMock);
	}

	@Test
	public void showFeedbackByNonExistentId() throws Exception
	{
		mockMvc.perform(get("/feedback/3"))
				.andExpect(status().isNotFound())
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(jsonPath("$.error", not(isEmptyString())));

		verify(feedbackServiceMock, times(1)).findById(3L);
		verifyNoMoreInteractions(feedbackServiceMock);
	}

	@Test
	public void showAllFeedbackComments() throws Exception
	{
		mockMvc.perform(get("/feedback"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(firstFeedback.getId().intValue())))
				.andExpect(jsonPath("$[0].name", is(firstFeedback.getName())))
				.andExpect(jsonPath("$[0].email", is(firstFeedback.getEmail())))
				.andExpect(jsonPath("$[0].message", is(firstFeedback.getMessage())))
				.andExpect(jsonPath("$[0].agreedWithSpam", is(firstFeedback.getAgreedWithSpam())))
				.andExpect(jsonPath("$[0].created", is(firstFeedback.getCreated().getTime())))
				.andExpect(jsonPath("$[1].id", is(secondFeedback.getId().intValue())))
				.andExpect(jsonPath("$[1].name", is(secondFeedback.getName())))
				.andExpect(jsonPath("$[1].email", is(secondFeedback.getEmail())))
				.andExpect(jsonPath("$[1].message", is(secondFeedback.getMessage())))
				.andExpect(jsonPath("$[1].agreedWithSpam", is(secondFeedback.getAgreedWithSpam())))
				.andExpect(jsonPath("$[1].created", is(secondFeedback.getCreated().getTime())));

		verify(feedbackServiceMock, times(1)).getFeedbackMessages(0);
		verifyNoMoreInteractions(feedbackServiceMock);
	}

	@Test
	public void addValidFeedback() throws Exception
	{
		Feedback feedback = createValidFeedback();

		verifyValidCreated(feedback);
	}

	private Feedback createValidFeedback()
	{
		Feedback feedback = new Feedback();
		feedback.setName("Valid Name");
		feedback.setEmail("invalid@email.com");
		feedback.setMessage("Valid Message");
		feedback.setAgreedWithSpam(true);

		return feedback;
	}

	private void verifyValidCreated(Feedback feedback) throws Exception
	{
		mockMvc.perform(post("/feedback")
						.contentType(MediaType.APPLICATION_JSON)
						.content(convertObjectToJsonBytes(feedback))
		)
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is(feedback.getName())))
				.andExpect(jsonPath("$.email", is(feedback.getEmail())))
				.andExpect(jsonPath("$.message", is(feedback.getMessage())))
				.andExpect(jsonPath("$.agreedWithSpam", is(feedback.getAgreedWithSpam())))
				.andExpect(jsonPath("$.created", is(TEST_DATE_CREATED.getTime())));

		verify(feedbackServiceMock, times(1)).save(any(Feedback.class));
		verifyZeroInteractions(feedbackServiceMock);
	}

	@Test
	public void invalidNullName() throws Exception
	{
		Feedback feedback = createValidFeedback();
		feedback.setName(null);

		verifyInvalid(feedback, "name");
	}

	private void verifyInvalid(Feedback feedback, String invalidFieldName) throws Exception
	{
		mockMvc.perform(post("/feedback")
						.contentType(MediaType.APPLICATION_JSON)
						.content(convertObjectToJsonBytes(feedback))
		)
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(jsonPath("$.", hasSize(1)))
				.andExpect(jsonPath("$.[0].fieldName", containsString(invalidFieldName)));

		verifyZeroInteractions(feedbackServiceMock);
	}

	@Test
	public void invalidBlankName() throws Exception
	{
		Feedback feedback = createValidFeedback();
		feedback.setName("");

		verifyInvalid(feedback, "name");
	}

	@Test
	public void invalidTooShortName() throws Exception
	{
		Feedback feedback = createValidFeedback();
		feedback.setName("N");

		verifyInvalid(feedback, "name");
	}

	@Test
	public void invalidTooLongName() throws Exception
	{
		Feedback feedback = createValidFeedback();
		feedback.setName("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam nec massa in tortor dictum viverra "
				+ "sit amet feugiat dolor. Morbi aliquet convallis ullamcorper. Sed pharetra congue aliquam. Aenean iaculis scelerisque "
				+ "elementum. Donec quis est bibendum. Lorem Ipsum.");

		verifyInvalid(feedback, "name");
	}

	@Test
	public void invalidNullEmail() throws Exception
	{
		Feedback feedback = createValidFeedback();
		feedback.setEmail(null);

		verifyInvalid(feedback, "email");
	}

	@Test
	public void invalidEmail() throws Exception
	{
		Feedback feedback = createValidFeedback();
		feedback.setEmail("Invalid Email");

		verifyInvalid(feedback, "email");
	}

	@Test
	public void invalidTooLongEmail() throws Exception
	{
		Feedback feedback = createValidFeedback();
		feedback.setEmail("LoremIpsumdolorsitametconsecteturadipiscingelitEtiamnecmassaintortordictumviverra@"
				+ "LoremIpsumdolorsitametconsecteturadipiscingelitEtiamnecmassaintortordictumviverraLoremIpsum"
				+ "dolorsitametconsecteturadipiscingelitEtiamnecmassaintortordictumviverraLoremIpsumdolorsitame"
				+ "tconsecteturadipiscingelitEtiamnecmassaintortordictumviverra.cz");

		verifyInvalid(feedback, "email");
	}

	@Test
	public void invalidNullMessage() throws Exception
	{
		Feedback feedback = createValidFeedback();
		feedback.setMessage(null);

		verifyInvalid(feedback, "message");
	}

	@Test
	public void invalidBlankMessage() throws Exception
	{
		Feedback feedback = createValidFeedback();
		feedback.setMessage("");

		verifyInvalid(feedback, "message");
	}

	@Test
	public void invalidTooShortMessage() throws Exception
	{
		Feedback feedback = createValidFeedback();
		feedback.setMessage("N");

		verifyInvalid(feedback, "message");
	}

	@Test
	public void invalidTooLongMessage() throws Exception
	{
		Feedback feedback = createValidFeedback();
		feedback.setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam nec massa in tortor dictum viverra "
				+ "sit amet feugiat dolor. Morbi aliquet convallis ullamcorper. Sed pharetra congue aliquam. Aenean iaculis scelerisque "
				+ "elementum. Donec quis est bibendum. Lorem Ipsum.");

		verifyInvalid(feedback, "message");
	}

	private static byte[] convertObjectToJsonBytes(Object object) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		return mapper.writeValueAsBytes(object);
	}

}
