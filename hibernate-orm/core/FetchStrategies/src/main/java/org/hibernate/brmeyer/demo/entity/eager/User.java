package org.hibernate.brmeyer.demo.entity.eager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * The Class User.
 */
@Entity
public class User {
	
	/** The id. */
	@Id
	@GeneratedValue
	private int id;

	/** The address 1. */
	private String address1;

	/** The address 2. */
	private String address2;

	/** The city. */
	private String city;

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

	/** The zip. */
	private String zip;
	
	/** The community memberships. */
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Community> communityMemberships = new ArrayList<Community>();
	
	/** The communities created. */
	@OneToMany(mappedBy = "creator", fetch = FetchType.EAGER)
	private List<Community> communitiesCreated = new ArrayList<Community>();
	
	/** The skills. */
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Skill> skills = new ArrayList<Skill>();
	
	/** The tools. */
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Tool> tools = new ArrayList<Tool>();
	
	/** The projects submitted. */
	@OneToMany(mappedBy = "submitter", fetch = FetchType.EAGER)
	private List<Project> projectsSubmitted = new ArrayList<Project>();
	
	/** The projects organized. */
	@OneToMany(mappedBy = "organizer", fetch = FetchType.EAGER)
	private List<Project> projectsOrganized = new ArrayList<Project>();
	
	/** The projects volunteered. */
	@ManyToMany(mappedBy = "volunteers", fetch = FetchType.EAGER)
	private List<Project> projectsVolunteered = new ArrayList<Project>();
	
	/** The comments. */
	@OneToMany(mappedBy = "submitter", fetch = FetchType.EAGER)
	private List<Comment> comments = new ArrayList<Comment>();
	
	/** The donations. */
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Donation> donations = new ArrayList<Donation>();
	
	/** The service events organized. */
	@OneToMany(mappedBy = "organizer", fetch = FetchType.EAGER)
	private List<ServiceEvent> serviceEventsOrganized = new ArrayList<ServiceEvent>();
	
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
	 * Gets the community memberships.
	 *
	 * @return the community memberships
	 */
	public List<Community> getCommunityMemberships() {
		return communityMemberships;
	}
	
	/**
	 * Sets the community memberships.
	 *
	 * @param communityMemberships the new community memberships
	 */
	public void setCommunityMemberships(List<Community> communityMemberships) {
		this.communityMemberships = communityMemberships;
	}
	
	/**
	 * Gets the communities created.
	 *
	 * @return the communities created
	 */
	public List<Community> getCommunitiesCreated() {
		return communitiesCreated;
	}
	
	/**
	 * Sets the communities created.
	 *
	 * @param communitiesCreated the new communities created
	 */
	public void setCommunitiesCreated(List<Community> communitiesCreated) {
		this.communitiesCreated = communitiesCreated;
	}
	
	/**
	 * Gets the skills.
	 *
	 * @return the skills
	 */
	public List<Skill> getSkills() {
		return skills;
	}
	
	/**
	 * Sets the skills.
	 *
	 * @param skills the new skills
	 */
	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
	
	/**
	 * Gets the tools.
	 *
	 * @return the tools
	 */
	public List<Tool> getTools() {
		return tools;
	}
	
	/**
	 * Sets the tools.
	 *
	 * @param tools the new tools
	 */
	public void setTools(List<Tool> tools) {
		this.tools = tools;
	}
	
	/**
	 * Gets the projects submitted.
	 *
	 * @return the projects submitted
	 */
	public List<Project> getProjectsSubmitted() {
		return projectsSubmitted;
	}
	
	/**
	 * Sets the projects submitted.
	 *
	 * @param projectsSubmitted the new projects submitted
	 */
	public void setProjectsSubmitted(List<Project> projectsSubmitted) {
		this.projectsSubmitted = projectsSubmitted;
	}
	
	/**
	 * Gets the projects organized.
	 *
	 * @return the projects organized
	 */
	public List<Project> getProjectsOrganized() {
		return projectsOrganized;
	}
	
	/**
	 * Sets the projects organized.
	 *
	 * @param projectsOrganized the new projects organized
	 */
	public void setProjectsOrganized(List<Project> projectsOrganized) {
		this.projectsOrganized = projectsOrganized;
	}
	
	/**
	 * Gets the projects volunteered.
	 *
	 * @return the projects volunteered
	 */
	public List<Project> getProjectsVolunteered() {
		return projectsVolunteered;
	}
	
	/**
	 * Sets the projects volunteered.
	 *
	 * @param projectsVolunteered the new projects volunteered
	 */
	public void setProjectsVolunteered(List<Project> projectsVolunteered) {
		this.projectsVolunteered = projectsVolunteered;
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
	
	/**
	 * Gets the service events organized.
	 *
	 * @return the service events organized
	 */
	public List<ServiceEvent> getServiceEventsOrganized() {
		return serviceEventsOrganized;
	}
	
	/**
	 * Sets the service events organized.
	 *
	 * @param serviceEventsOrganized the new service events organized
	 */
	public void setServiceEventsOrganized(List<ServiceEvent> serviceEventsOrganized) {
		this.serviceEventsOrganized = serviceEventsOrganized;
	}
}
