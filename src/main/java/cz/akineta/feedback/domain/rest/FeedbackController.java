package cz.akineta.feedback.domain.rest;

import cz.akineta.feedback.domain.Feedback;
import cz.akineta.feedback.domain.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
@Controller
@RequestMapping(value = "/feedback")
public class FeedbackController
{

	private final FeedbackService feedbackService;
	private final MessageSource messageSource;

	@Autowired
	public FeedbackController(FeedbackService feedbackService, MessageSource messageSource)
	{
		this.feedbackService = feedbackService;
		this.messageSource = messageSource;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Feedback> getFeedbackMessages()
	{
		return feedbackService.getFeedbackMessages(0);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Feedback createFeedbackMessage(@Valid @RequestBody Feedback feedback, BindingResult result)
	{
		if (result.hasErrors())
		{
			throw new FeedbackValidationException(result.getFieldErrors());
		}

		feedbackService.save(feedback);

		return feedback;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Feedback showFeedbackMessage(@PathVariable long id)
	{
		Feedback feedback = feedbackService.findById(id);

		if (feedback == null)
		{
			throw new FeedbackNotFoundException();
		}

		return feedback;
	}

	@ExceptionHandler(FeedbackNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public String handleFeedbackNotFoundException(Locale locale, FeedbackNotFoundException exception)
	{
		return messageSource.getMessage("feedback.exception.notFound", null, locale);
	}

	@ExceptionHandler(FeedbackValidationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public List<String> handleFeedbackValidationException(FeedbackValidationException exception)
	{
		return exception.getLocalizedMessages();
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public String handleException(Locale locale, Exception exception)
	{
		return messageSource.getMessage("feedback.exception.badRequest", null, locale);
	}

}
