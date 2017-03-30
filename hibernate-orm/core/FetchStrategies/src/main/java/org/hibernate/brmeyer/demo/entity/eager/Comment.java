package org.hibernate.brmeyer.demo.entity.eager;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 * The Class Comment.
 */
@Entity
public class Comment {

	/** The id. */
	@Id
	@GeneratedValue
	private int id;

	/** The date added. */
	private Calendar dateAdded;
	
	/** The submitter. */
	@ManyToOne
	private User submitter;
	
	/** The text. */
	@Lob
	private String text;
	
	/** The project. */
	@ManyToOne
	private Project project;

	/**
	 * Gets the date added.
	 *
	 * @return the date added
	 */
	public Calendar getDateAdded() {
		return dateAdded;
	}

	/**
	 * Sets the date added.
	 *
	 * @param dateAdded the new date added
	 */
	public void setDateAdded(Calendar dateAdded) {
		this.dateAdded = dateAdded;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the submitter.
	 *
	 * @return the submitter
	 */
	public User getSubmitter() {
		return submitter;
	}

	/**
	 * Sets the submitter.
	 *
	 * @param submitter the new submitter
	 */
	public void setSubmitter(User submitter) {
		this.submitter = submitter;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the project.
	 *
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * Sets the project.
	 *
	 * @param project the new project
	 */
	public void setProject(Project project) {
		this.project = project;
	}
}
