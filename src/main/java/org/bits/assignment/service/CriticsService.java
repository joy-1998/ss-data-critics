package org.bits.assignment.service;


import org.bits.assignment.model.CriticsSearchRequest;
import org.bits.assignment.model.CriticsSearchResponse;
import org.bits.assignment.model.MutationResponse;

import java.util.List;

public interface CriticsService {

    List<CriticsSearchResponse> searchQuery (CriticsSearchRequest criticsSearchRequest);

    MutationResponse addOrUpdate (CriticsSearchRequest criticsSearchRequest);

    MutationResponse deleteRecord(CriticsSearchRequest criticsSearchRequest);

}
