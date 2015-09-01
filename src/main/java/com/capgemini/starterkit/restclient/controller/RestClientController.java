package com.capgemini.starterkit.restclient.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;

import com.capgemini.starterkit.restclient.dataprovider.DataProvider;
import com.capgemini.starterkit.restclient.dataprovider.data.AuthorVO;
import com.capgemini.starterkit.restclient.dataprovider.data.BookVO;
import com.capgemini.starterkit.restclient.model.BookSearch;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Controller for the rest client screen.
 * <p>
 * The JavaFX runtime will inject corresponding objects in the @FXML annotated
 * fields. The @FXML annotated methods will be called by JavaFX runtime at
 * specific points in time.
 * </p>
 *
 * @author Krzysztof
 */
public class RestClientController {

	/**
	 * <p>
	 * NOTE: The variable name must be {@code resources}.
	 * </p>
	 */
	@FXML
	private ResourceBundle resources;

	/**
	 * <p>
	 * NOTE: The variable name must be {@code location}.
	 * </p>
	 */
	@FXML
	private URL location;

	@FXML
	private TextField titleField;

	@FXML
	private Button searchButton;

	@FXML
	private Button deleteButton;

	@FXML
	private TableView<BookVO> resultTable;

	@FXML
	private TableColumn<BookVO, String> idColumn;

	@FXML
	private TableColumn<BookVO, String> titleColumn;

	@FXML
	private TableColumn<BookVO, String> authorsColumn;

	private final DataProvider dataProvider = DataProvider.INSTANCE;

	private final BookSearch model = new BookSearch();

	/**
	 * The JavaFX runtime instantiates this controller.
	 * <p>
	 * The @FXML annotated fields are not yet initialized at this point.
	 * </p>
	 */
	public RestClientController() {
	}

	/**
	 * The JavaFX runtime calls this method after the FXML file loaded.
	 * <p>
	 * The @FXML annotated fields are initialized at this point.
	 * </p>
	 * <p>
	 * NOTE: The method name must be {@code initialize}.
	 * </p>
	 */
	@FXML
	private void initialize() {
		initializeResultTable();

		/*
		 * Bind controls properties to model properties.
		 */
		titleField.textProperty().bindBidirectional(model.titleProperty());
		resultTable.itemsProperty().bind(model.resultProperty());
		searchButton.disableProperty().bind(titleField.textProperty().isEmpty());
	}

	private void initializeResultTable() {
		/*
		 * Define what properties of BookVO will be displayed in different
		 * columns.
		 */
		idColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getId().toString()));
		titleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
		authorsColumn.setCellValueFactory(
				cellData -> new ReadOnlyStringWrapper(createAuthorsString(cellData.getValue().getAuthors())));

		/*
		 * Show specific text for an empty table.
		 */
		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));

		/*
		 * When table row gets selected get the id of selected book.
		 */
		resultTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BookVO>() {

			@Override
			public void changed(ObservableValue<? extends BookVO> observable, BookVO oldValue, BookVO newValue) {
				if(newValue == null) {
					return;
				}

				Task<Void> backgroundTask = new Task<Void>() {

					@Override
					protected Void call() throws Exception {
						return null;
					}

					@Override
					protected void succeeded() {
						model.setId(newValue.getId());
					}
				};
				new Thread(backgroundTask).start();
			}
		});
	}

	/**
	 * The JavaFX runtime calls this method when the <b>Search</b> button is
	 * clicked.
	 *
	 * @param event
	 *            {@link ActionEvent} holding information about this event
	 */
	@FXML
	private void searchButtonAction(ActionEvent event) {
		/*
		 * Use task to execute the potentially long running call in background
		 * (separate thread), so that the JavaFX Application Thread is not
		 * blocked.
		 */
		Task<Collection<BookVO>> backgroundTask = new Task<Collection<BookVO>>() {

			/**
			 * This method will be executed in a background thread.
			 */
			@Override
			protected Collection<BookVO> call() throws Exception {
				/*
				 * Get the data.
				 */
				Collection<BookVO> result = dataProvider.findBooks(model.getTitle());

				return result;
			}

			/**
			 * This method will be executed in the JavaFX Application Thread
			 * when the task finishes.
			 */
			@Override
			protected void succeeded() {
				/*
				 * Copy the result to model.
				 */
				model.setResult(new ArrayList<BookVO>(getValue()));

				/*
				 * Reset sorting in table.
				 */
				resultTable.getSortOrder().clear();
			}

		};

		/*
		 * Start the background task.
		 */

		new Thread(backgroundTask).start();
	}

	private String createAuthorsString(Set<AuthorVO> authors) {
		StringBuilder builder = new StringBuilder();

		Iterator<AuthorVO> iterator = authors.iterator();
		while (iterator.hasNext()) {
			AuthorVO author = iterator.next();
			builder.append(author.getFirstName() + " " + author.getLastName());
			if (iterator.hasNext()) {
				builder.append(", ");
			}
		}

		return builder.toString();
	}

	@FXML
	private void deleteButtonAction() {
		/*
		 * If no row is selected, no book will be deleted.
		 */
		if(resultTable.getSelectionModel().getSelectedItem() == null) {
			return;
		}

		Task<Void> backgroundTask = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				try {
					/*
					 * Remove the book.
					 */
					dataProvider.deleteBook(model.getId());
				} catch (ClientProtocolException e) {
					throw e;
				} catch (IOException e) {
					throw e;
				}

				return null;
			}

			@Override
			protected void succeeded() {
				/*
				 * Remove the book from model.
				 */
				model.resultProperty().removeIf(b -> b.getId() == model.getId());
			}

		};

		/*
		 * Start the background task.
		 */

		new Thread(backgroundTask).start();
	}
}