package cz.akineta.feedback.domain;


import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
@Service
public class SimpleFeedbackService implements FeedbackService
{

	private static final int PAGE_SIZE = 10;
	private final FeedbackRepository feedbackRepository;

	@Autowired
	public SimpleFeedbackService(FeedbackRepository feedbackRepository)
	{
		this.feedbackRepository = feedbackRepository;
	}

	@Override
	@Transactional
	public void save(@NotNull Feedback feedback)
	{
		Assert.notNull(feedback, "feedback object is null");
		Assert.isNull(feedback.getId(), "feedback ID has to be null when saving.");

		feedback.setCreated(DateTime.now().toDate());
		feedbackRepository.save(feedback);
	}

	@Override
	public Feedback findById(long id)
	{
		return feedbackRepository.findOne(id);
	}

	@NotNull
	@Override
	public List<Feedback> getFeedbackMessages(int page)
	{
		return feedbackRepository.findAll(new PageRequest(page, PAGE_SIZE, new Sort(Sort.Direction.DESC, "created"))).getContent();
	}

}
