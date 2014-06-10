package cz.akineta.feedback.domain;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
@Entity
@Table(name = "`feedback`")
public class Feedback
{

	@Id
	@GeneratedValue
	private Long id;
	@NotNull(message = "{feedback.validation.name}")
	@Size(min = 3, max = 255, message = "{feedback.validation.name}")
	private String name;
	@NotNull(message = "{feedback.validation.email}")
	@Email(message = "{feedback.validation.email}")
	private String email;
	@NotNull(message = "{feedback.validation.message}")
	@Size(min = 5, max = 255, message = "{feedback.validation.message}")
	private String message;
	private Boolean agreedWithSpam = true;
	private Date created;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Boolean getAgreedWithSpam()
	{
		return agreedWithSpam;
	}

	public void setAgreedWithSpam(Boolean agreedWithSpam)
	{
		if (agreedWithSpam == null)
		{
			this.agreedWithSpam = false;
		}

		this.agreedWithSpam = agreedWithSpam;
	}

	public Date getCreated()
	{
		return created;
	}

	public void setCreated(Date created)
	{
		this.created = created;
	}

}
