package com.capgemini.starterkit.restclient.dataprovider.impl;

import java.util.Collection;
import java.util.Collections;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.ClientProtocolException;

import com.capgemini.starterkit.restclient.dataprovider.DataProvider;
import com.capgemini.starterkit.restclient.dataprovider.data.BookVO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provides data.
 *
 * @author Krzysztof
 */
public class DataProviderImpl implements DataProvider {

	private CloseableHttpClient httpClient;

	public DataProviderImpl() {
		httpClient = HttpClients.createDefault();
	}


	@Override
	public Collection<BookVO> findBooks(String title) throws ClientProtocolException, IOException {

		/*
		 * Create and execute HTTP GET request.
		 */
		HttpGet httpGet = new HttpGet("http://localhost:9721/workshop/rest/books/books-by-title?titlePrefix=" + title);
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());

		/*
		 * Parse the response from JSON.
		 */
		ObjectMapper mapper = new ObjectMapper();
		Collection<BookVO> books = Collections.emptyList();
		try {
			books = mapper.readValue(response, new TypeReference<Collection<BookVO>>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return books;
	}

	@Override
	public BookVO addBook(BookVO book)
			throws JsonProcessingException, UnsupportedEncodingException, ClientProtocolException, IOException {

		/*
		 * Create HTTP POST request.
		 */
		HttpPost httpPost = new HttpPost("http://localhost:9721/workshop/rest/books/book");
		httpPost.addHeader("Content-Type", "application/json");

		/*
		 * Convert the book to JSON.
		 */
		ObjectMapper mapper = new ObjectMapper();
		String bookJson = "";
		try {
			bookJson = mapper.writeValueAsString(book);
		} catch (JsonProcessingException e) {
			throw e;
		}

		StringEntity entity = new StringEntity(bookJson, "UTF-8");

		/*
		 * Add book data to request.
		 */
		httpPost.setEntity(entity);

		/*
		 * Execute the request.
		 */
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
		String response = EntityUtils.toString(httpResponse.getEntity());

		/*
		 * Parse the response from JSON.
		 */
		BookVO savedBook = null;
		try {
			savedBook = mapper.readValue(response, BookVO.class);
		} catch (JsonParseException e) {
			throw e;
		} catch (JsonMappingException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}

		return savedBook;
	}

	@Override
	public void deleteBook(Long id) throws ClientProtocolException, IOException {

		/*
		 * Create and execute HTTP DELETE request.
		 */
		HttpDelete httpDelete = new HttpDelete("http://localhost:9721/workshop/rest/books/book/" + id.toString());
		httpClient.execute(httpDelete);
	}

}
