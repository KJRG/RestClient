package com.capgemini.starterkit.restclient.dataprovider.data;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookVO {

	private Long id;
	private String title;
	private Set<AuthorVO> authors;

	@JsonCreator
	public BookVO(@JsonProperty("id") Long id, @JsonProperty("title") String title,
			@JsonProperty("authors") Set<AuthorVO> authors) {
		this.id = id;
		this.title = title;
		this.authors = authors;
	}

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

	public Set<AuthorVO> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<AuthorVO> authors) {
		this.authors = authors;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", authors=" + authors + "]";
	}
}
