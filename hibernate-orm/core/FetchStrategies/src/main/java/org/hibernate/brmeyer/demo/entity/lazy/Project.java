package org.hibernate.brmeyer.demo.entity.lazy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


/**
 * The Class Project.
 */
@Entity
public class Project {

	/** The id. */
	@Id
	@GeneratedValue
	private int id;

	/** The organizer. */
	@ManyToOne
	private User organizer = null;

	/** The address 1. */
	private String address1;
	
	/** The address 2. */
	private String address2;

	/** The city. */
	private String city;

	/** The date added. */
	private Calendar dateAdded;

	/** The description. */
	@Lob
	private String description;

	/** The email. */
	private String email;

	/** The first name. */
	private String firstName;

	/** The last name. */
	private String lastName;

	/** The phone. */
	private String phone;
	
	/** The state. */
	private String state;

	/** The submitter. */
	@ManyToOne
	private User submitter = null;

	/** The zip. */
	private String zip;

	/** The volunteers. */
	@ManyToMany
	private List<User> volunteers = new ArrayList<User>();

    /** The image urls. */
    @ElementCollection
    private List<String> imageUrls = new ArrayList<String>();
    
    /** The comments. */
    @OneToMany(mappedBy = "project")
    private List<Comment> comments = new ArrayList<Comment>();
    
    /** The donations. */
    @OneToMany(mappedBy = "project")
    private List<Donation> donations = new ArrayList<Donation>();
    
    /** The title. */
    private String title;

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
	 * Gets the organizer.
	 *
	 * @return the organizer
	 */
	public User getOrganizer() {
		return organizer;
	}

	/**
	 * Sets the organizer.
	 *
	 * @param organizer the new organizer
	 */
	public void setOrganizer(User organizer) {
		this.organizer = organizer;
	}

	/**
	 * Gets the address 2.
	 *
	 * @return the address 2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * Sets the address 2.
	 *
	 * @param address2 the new address 2
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * Gets the address 1.
	 *
	 * @return the address 1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * Sets the address 1.
	 *
	 * @param address1 the new address 1
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

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
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the phone.
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone the new phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(String state) {
		this.state = state;
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
	 * Gets the zip.
	 *
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * Sets the zip.
	 *
	 * @param zip the new zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * Gets the volunteers.
	 *
	 * @return the volunteers
	 */
	public List<User> getVolunteers() {
		return volunteers;
	}

	/**
	 * Sets the volunteers.
	 *
	 * @param volunteers the new volunteers
	 */
	public void setVolunteers(List<User> volunteers) {
		this.volunteers = volunteers;
	}

    /**
     * Gets the image urls.
     *
     * @return the image urls
     */
    public List<String> getImageUrls() {
        return imageUrls;
    }

    /**
     * Sets the image urls.
     *
     * @param imageUrls the new image urls
     */
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Gets the comments.
	 *
	 * @return the comments
	 */
	public List<Comment> getComments() {
		return comments;
	}
	
	/**
	 * Sets the comments.
	 *
	 * @param comments the new comments
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	/**
	 * Gets the donations.
	 *
	 * @return the donations
	 */
	public List<Donation> getDonations() {
		return donations;
	}
	
	/**
	 * Sets the donations.
	 *
	 * @param donations the new donations
	 */
	public void setDonations(List<Donation> donations) {
		this.donations = donations;
	}
}
