package org.bits.assignment.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.bits.assignment.entity.CriticsEntity;
import org.bits.assignment.model.CriticsSearchRequest;
import org.bits.assignment.model.CriticsSearchResponse;
import org.bits.assignment.repositories.CriticsRepository;
import org.bits.assignment.service.CriticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;


@Service
@Slf4j
public class CriticsServiceImpl implements CriticsService {

    @Autowired
    CriticsRepository criticsRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<CriticsSearchResponse> searchQuery(CriticsSearchRequest criticsSearchRequest) {
            List<CriticsSearchResponse> criticsSearchRespons;
        try{

            Query query = QueryBuilder(criticsSearchRequest);
            log.info(query.toString());
            List<CriticsEntity> animeSearch = mongoTemplate.find(query, CriticsEntity.class);
            criticsSearchRespons = animeSearch.stream().map(
                    anime ->
                        CriticsSearchResponse.builder()
                                .status(HttpStatus.OK)
                                .respMsg("Success")
                                .title(anime.getTitle())
                                .description(anime.getDescription())
                                .mediaType(anime.getMediaType())
                                .tags(anime.getTags())
                                .startYr(anime.getStartYr())
                                .sznOfRelease(anime.getSznOfRelease())
                                .eps(anime.getEps())
                                .contentWarn(anime.getContentWarn())
                                .ongoing(anime.getOngoing())
                                .finishYr(anime.getFinishYr())
                                .studios(anime.getStudios())
                                .watched(anime.getWatched())
                                .watching(anime.getWatching())
                                .wantWatch(anime.getWantWatch())
                                .dropped(anime.getDropped())
                                .rating(anime.getRating())
                                .votes(anime.getVotes())
                                .build()
                    ).collect(Collectors.toList());

        }catch(Exception ex){
            log.error(ex.getMessage());
            return Collections.singletonList(CriticsSearchResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .respMsg(ex.getMessage())
                    .build());
        }
        return criticsSearchRespons;
    }

    private Query QueryBuilder(CriticsSearchRequest criticsSearchRequest) {
        Query query = new Query();
        if(
                criticsSearchRequest.getPageSzie() != 0
                        && criticsSearchRequest.getPageNbr() != 0
        ){
            int skip = (criticsSearchRequest.getPageNbr() - 1) * criticsSearchRequest.getPageSzie();
            query.skip(skip).limit(criticsSearchRequest.getPageSzie());
        }else{
            query.skip(0).limit(50);
        }
        if (nonNull(criticsSearchRequest.getTags()) && !criticsSearchRequest.getTags().isEmpty()) {
            StringJoiner valueToSearch = new StringJoiner("|");
            criticsSearchRequest.getTags().forEach(valueToSearch::add);
            log.info(valueToSearch.toString());
            Pattern regExSearch = Pattern.compile(valueToSearch.toString(), Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("tags").regex(regExSearch));
        }

        if (nonNull(criticsSearchRequest.getTitle()) && StringUtils.hasLength(criticsSearchRequest.getTitle())) {
            query.addCriteria(Criteria.where("title").regex(criticsSearchRequest.getTitle()));
        }

        if (nonNull(criticsSearchRequest.getDescription()) && StringUtils.hasLength(criticsSearchRequest.getDescription())) {
            query.addCriteria(Criteria.where("description").regex(criticsSearchRequest.getDescription()));
        }

        query.fields().exclude("_id");
        return query;
    }

}
