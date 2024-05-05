package org.bits.assignment.controllers;

import org.bits.assignment.model.CriticsSearchRequest;
import org.bits.assignment.model.CriticsSearchResponse;
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

    @PostMapping(path = "/searchByGenre", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CriticsSearchResponse>> genre_search(@RequestBody CriticsSearchRequest criticsSearchRequest) {

        if(!nonNull(criticsSearchRequest.getTags())){
            List<CriticsSearchResponse> criticsSearchRespons = Collections.singletonList(CriticsSearchResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .respMsg("genre tags is missing")
                    .build());
            return new ResponseEntity<>(criticsSearchRespons,HttpStatus.BAD_REQUEST);
        }else if(!criticsSearchRequest.getTags().isEmpty()){
            List<CriticsSearchResponse> criticsSearchResponse = criticsService.searchQuery(criticsSearchRequest);
            return new ResponseEntity<>(criticsSearchResponse, HttpStatus.OK);
        }
        List<CriticsSearchResponse> criticsSearchRespons = Collections.singletonList(CriticsSearchResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .respMsg("genre cannot be empty")
                .build());
        return new ResponseEntity<>(criticsSearchRespons,HttpStatus.BAD_REQUEST);

    }

    @PostMapping(path = "/searchByTitle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CriticsSearchResponse>> title_search(@RequestBody CriticsSearchRequest criticsSearchRequest) {
        if(StringUtils.hasLength(criticsSearchRequest.getTitle())){
            List<CriticsSearchResponse> criticsSearchResponse = criticsService.searchQuery(criticsSearchRequest);
            return new ResponseEntity<>(criticsSearchResponse, HttpStatus.OK);
        }

        List<CriticsSearchResponse> criticsSearchRespons = Collections.singletonList(CriticsSearchResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .respMsg("missing title attribute")
                .build());
        return new ResponseEntity<>(criticsSearchRespons,HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/searchByDescription", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CriticsSearchResponse>> desc_search(@RequestBody CriticsSearchRequest criticsSearchRequest) {
        if(StringUtils.hasLength(criticsSearchRequest.getDescription())){
            List<CriticsSearchResponse> criticsSearchResponse = criticsService.searchQuery(criticsSearchRequest);
            return new ResponseEntity<>(criticsSearchResponse, HttpStatus.OK);
        }
        List<CriticsSearchResponse> criticsSearchRespons = Collections.singletonList(CriticsSearchResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .respMsg("missing description attribute")
                .build());
        return new ResponseEntity<>(criticsSearchRespons,HttpStatus.BAD_REQUEST);

    }

}