package org.bits.assignment.service;


import org.bits.assignment.model.CriticsSearchRequest;
import org.bits.assignment.model.CriticsSearchResponse;

import java.util.List;

public interface CriticsService {

    List<CriticsSearchResponse> searchQuery (CriticsSearchRequest criticsSearchRequest);


}
