package org.kelab.enterprise.service;

import org.bson.Document;

import java.util.List;

public interface WordsFrequencyService {


	List<Document> query(String key, String companyName);

	List<Document> queryByCompany(String key, String companyName);
}
