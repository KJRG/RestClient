package com.capgemini.starterkit.restclient.dataprovider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

import org.apache.http.client.ClientProtocolException;

import com.capgemini.starterkit.restclient.dataprovider.data.BookVO;
import com.capgemini.starterkit.restclient.dataprovider.impl.DataProviderImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Provides data.
 *
 * @author Krzysztof
 */
public interface DataProvider {

	/**
	 * Instance of this interface.
	 */
	DataProvider INSTANCE = new DataProviderImpl();

	Collection<BookVO> findBooks(String title) throws ClientProtocolException, IOException;

	BookVO addBook(BookVO book)
			throws JsonProcessingException, UnsupportedEncodingException, ClientProtocolException, IOException;

	void deleteBook(Long id) throws ClientProtocolException, IOException;
}
