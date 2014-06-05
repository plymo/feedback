package cz.akineta.feedback.domain;

import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * @author Jakub Mahdal, <jakub.mahdal@akineta.cz>
 */
interface FeedbackRepository extends PagingAndSortingRepository<Feedback, Long>
{
}