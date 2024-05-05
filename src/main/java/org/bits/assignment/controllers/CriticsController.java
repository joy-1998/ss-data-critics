package org.bits.assignment.controllers;

import org.bits.assignment.model.CriticsSearchRequest;
import org.bits.assignment.model.CriticsSearchResponse;
import org.bits.assignment.model.MutationResponse;
import org.bits.assignment.service.CriticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;


@RestController
@Configuration
@Slf4j
@RequestMapping(value = "/v1/api")
public class CriticsController {

    @Autowired
    CriticsService criticsService;

    public CriticsController(CriticsService criticsService) {
        this.criticsService = criticsService;
    }

    @PostMapping(path = "/getCriticsReviews", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CriticsSearchResponse>> criticReviews(@RequestBody CriticsSearchRequest criticsSearchRequest) {

            List<CriticsSearchResponse> criticsSearchResponse = criticsService.searchQuery(criticsSearchRequest);
            return new ResponseEntity<>(criticsSearchResponse, HttpStatus.OK);
    }

    @PostMapping(path = "/addOrUpdateCriticReviews", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MutationResponse> addReviews(@RequestBody CriticsSearchRequest criticsSearchRequest) {

            MutationResponse criticsSearchResponse = criticsService.addOrUpdate(criticsSearchRequest);
            return new ResponseEntity<>(criticsSearchResponse, HttpStatus.OK);

    }

    @DeleteMapping(path = "/deleteReviews", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MutationResponse> deleteReviews(@RequestBody CriticsSearchRequest criticsSearchRequest) {

            MutationResponse criticsSearchResponse = criticsService.deleteRecord(criticsSearchRequest);
            return new ResponseEntity<>(criticsSearchResponse, HttpStatus.OK);

    }

}