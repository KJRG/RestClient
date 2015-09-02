package com.capgemini.starterkit.restclient.mapper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.capgemini.starterkit.restclient.dataprovider.data.AuthorVO;

public class AuthorMapper {
	public Set<AuthorVO> mapToSet(String authorsString) {
		HashSet<AuthorVO> authors = new HashSet<>();

		String[] authorFullNames = authorsString.split("\\s*,\\s*");
		for(String fullName : authorFullNames) {
			String[] authorsName = fullName.split(" ");

			String lastName = authorsName[authorsName.length - 1];
			String firstName = "";
			if(authorsName.length > 1) {
				firstName = fullName.substring(0, fullName.length() - lastName.length() - 1);
			}

			authors.add(new AuthorVO(null, firstName, lastName));
		}

		return authors;
	}

	public String mapToString(Set<AuthorVO> authorsSet) {
		StringBuilder builder = new StringBuilder("");

		Iterator<AuthorVO> iterator = authorsSet.iterator();
		while (iterator.hasNext()) {
			AuthorVO author = iterator.next();
			if(!author.getFirstName().isEmpty()) {
				builder.append(author.getFirstName() + " ");
			}
			builder.append(author.getLastName());
			if (iterator.hasNext()) {
				builder.append(", ");
			}
		}

		return builder.toString();
	}
}
