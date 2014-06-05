package cz.akineta.feedback.domain;


import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;

import java.util.List;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
public interface FeedbackService
{

	void save(@NotNull Feedback feedback);

	Feedback findById(long id);

	@NotNull
	List<Feedback> getFeedbackMessages(int page);

}
