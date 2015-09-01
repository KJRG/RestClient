package com.capgemini.starterkit.restclient.dataprovider;

import java.io.IOException;
import java.util.Collection;

import org.apache.http.client.ClientProtocolException;

import com.capgemini.starterkit.restclient.dataprovider.data.BookVO;
import com.capgemini.starterkit.restclient.dataprovider.impl.DataProviderImpl;

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
	void deleteBook(Long id) throws ClientProtocolException, IOException;
}
