package org.hibernate.search.demos.wikipedia.dto;

public class PageOutputDto {

	private Long id;

	private String title;

	private String content;

	private UserOutputDto lastContributor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserOutputDto getLastContributor() {
		return lastContributor;
	}

	public void setLastContributor(UserOutputDto lastContributor) {
		this.lastContributor = lastContributor;
	}

}
