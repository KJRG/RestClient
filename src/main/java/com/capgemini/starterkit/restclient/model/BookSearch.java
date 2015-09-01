package com.capgemini.starterkit.restclient.model;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.starterkit.restclient.dataprovider.data.AuthorVO;
import com.capgemini.starterkit.restclient.dataprovider.data.BookVO;

import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

public class BookSearch {

	private final LongProperty id = new SimpleLongProperty();
	private final StringProperty title = new SimpleStringProperty();
	private final SetProperty<AuthorVO> authors = new SimpleSetProperty<>();
	private final ListProperty<BookVO> result = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<>()));

	public final Long getId() {
		return id.get();
	}

	public final void setId(Long value) {
		id.set(value);
	}

	public LongProperty idProperty() {
		return id;
	}

	public final String getTitle() {
		return title.get();
	}

	public final void setTitle(String value) {
		title.set(value);
	}

	public StringProperty titleProperty() {
		return title;
	}

	public final ObservableSet<AuthorVO> getAuthors() {
		return authors.get();
	}

	public final void setAuthors(ObservableSet<AuthorVO> value) {
		authors.set(value);
	}

	public SetProperty<AuthorVO> authorsProperty() {
		return authors;
	}

	public final List<BookVO> getResult() {
		return result.get();
	}

	public final void setResult(List<BookVO> value) {
		result.setAll(value);
	}

	public ListProperty<BookVO> resultProperty() {
		return result;
	}

	@Override
	public String toString() {
		return "BookSearch [id=" + id + ", title=" + title + ", authors=" + authors + ", result=" + result + "]";
	}

}
