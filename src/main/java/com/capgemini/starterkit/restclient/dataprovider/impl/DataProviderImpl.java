package com.capgemini.starterkit.restclient.dataprovider.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.capgemini.starterkit.restclient.dataprovider.DataProvider;
import com.capgemini.starterkit.restclient.dataprovider.data.BookVO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provides data.
 *
 * @author Krzysztof
 */
public class DataProviderImpl implements DataProvider {

	public DataProviderImpl() {
	}

	@Override
	public Collection<BookVO> findBooks(String title) throws ClientProtocolException, IOException {

		/*
		 * Create and execute HTTP GET request.
		 */
		CloseableHttpClient httpClient = HttpClients.createDefault();
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
		}
		catch(JsonParseException e) {
			e.printStackTrace();
		}
		catch(JsonMappingException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		return books;
	}

	@Override
	public void deleteBook(Long id) throws ClientProtocolException, IOException {
		/*
		 * Create and execute HTTP DELETE request.
		 */
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete("http://localhost:9721/workshop/rest/books/book/" + id.toString());
		httpClient.execute(httpDelete);
	}
}
