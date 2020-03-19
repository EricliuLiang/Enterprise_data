package org.kelab.enterprise.service;

import java.util.List;
import java.util.Map;

public interface InvestmentRelationshipService {
    public Map<String, List<String>> queryInvestmentRelationshipService(String companyName);
}
