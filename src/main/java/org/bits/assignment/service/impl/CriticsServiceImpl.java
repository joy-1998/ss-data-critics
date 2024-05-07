package org.bits.assignment.service.impl;


import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bits.assignment.entity.AnimeEntity;
import org.bits.assignment.model.CriticsSearchRequest;
import org.bits.assignment.model.CriticsSearchResponse;
import org.bits.assignment.model.MutationResponse;
import org.bits.assignment.repositories.CriticsRepository;
import org.bits.assignment.service.CriticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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
            List<AnimeEntity> animeSearch = mongoTemplate.find(query, AnimeEntity.class);
            criticsSearchRespons = animeSearch.stream().map(
                    anime ->
                        CriticsSearchResponse.builder()
                                .status(HttpStatus.OK)
                                .respMsg("Success")
                                .title(anime.getTitle())
                                .description(anime.getDescription())
                                .critics_reviews(anime.getCritics_reviews())
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

    @Override
    public MutationResponse addOrUpdate(CriticsSearchRequest criticsSearchRequest) {

        try{
            UpdateResult result;
            AnimeEntity newReviews = new AnimeEntity();
            newReviews.set_id(criticsSearchRequest.getTitle());
            newReviews.setTitle(criticsSearchRequest.getTitle());
            newReviews.setDescription(criticsSearchRequest.getDescription());
            newReviews.setCritics_reviews(criticsSearchRequest.getCritics_reviews());

            String criticName = (
                    !criticsSearchRequest.getCritics_reviews().isEmpty() &&
                    nonNull(criticsSearchRequest.getCritics_reviews().get(0).getCritic_name())
            )? criticsSearchRequest.getCritics_reviews().get(0).getCritic_name() : "";

            String criticReviews = (
                    !criticsSearchRequest.getCritics_reviews().isEmpty() &&
                            nonNull(criticsSearchRequest.getCritics_reviews().get(0).getCritics_review())
            )? criticsSearchRequest.getCritics_reviews().get(0).getCritics_review() : "";

            String criticRatings = (
                    !criticsSearchRequest.getCritics_reviews().isEmpty() &&
                            nonNull(criticsSearchRequest.getCritics_reviews().get(0).getRatings())
            )? criticsSearchRequest.getCritics_reviews().get(0).getRatings() : "";


            Query ExistingReviews = new Query();
            ExistingReviews.addCriteria(Criteria.where("title").is(newReviews.getTitle()));
            ExistingReviews.addCriteria(Criteria.where("critics_reviews.critic_name").is(criticName));

            if(!mongoTemplate.find(ExistingReviews, AnimeEntity.class).isEmpty()){
                Update updExRev = new Update().set("critics_reviews.$.critic_name",criticName);
                updExRev.set("critics_reviews.$.critics_review",criticReviews);
                updExRev.set("critics_reviews.$.ratings",criticRatings);
                result = mongoTemplate.updateFirst(ExistingReviews, updExRev, AnimeEntity.class);
                log.info("found a matching document - updating existing");
            }else{
                Query query = new Query().addCriteria(Criteria.where("title").is(newReviews.getTitle()));
                if(!mongoTemplate.find(query, AnimeEntity.class).isEmpty()){
                    Update updateValue = new Update().push("critics_reviews").each(newReviews.getCritics_reviews());
                    result = mongoTemplate.upsert(query,updateValue, AnimeEntity.class);
                    log.info("no matching records found - inserting new Reviews");
                }else{
                    log.info("code block here");
                    Update newValue = new Update()
                            .set("_id",newReviews.getTitle())
                            .set("title", newReviews.getTitle())
                            .set("description", newReviews.getDescription())
                            .set("critics_reviews",newReviews.getCritics_reviews());
                    result = mongoTemplate.upsert(query, newValue, AnimeEntity.class);
                }
            }

            log.info(result.toString());

            return MutationResponse.builder()
                    .status(HttpStatus.OK)
                    .respMsg("Updated Successfully")
                    .modifiedCount(String.valueOf(result.getModifiedCount()))
                    .upsertId(String.valueOf(result.getUpsertedId()))
                    .build();

        }catch(Exception ex){
            log.error(ex.getMessage());
            return MutationResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .respMsg(ex.getMessage())
                    .build();
        }
    }


    public MutationResponse deleteRecord(CriticsSearchRequest criticsSearchRequest) {

        try{
            UpdateResult result;
            AnimeEntity newReviews = new AnimeEntity();
            newReviews.set_id(criticsSearchRequest.getTitle());
            newReviews.setTitle(criticsSearchRequest.getTitle());
            newReviews.setDescription(criticsSearchRequest.getDescription());
            newReviews.setCritics_reviews(criticsSearchRequest.getCritics_reviews());

            String criticName = (
                    !criticsSearchRequest.getCritics_reviews().isEmpty() &&
                            nonNull(criticsSearchRequest.getCritics_reviews().get(0).getCritic_name())
            )? criticsSearchRequest.getCritics_reviews().get(0).getCritic_name() : "";


            Query recordToMatch = new Query();
            recordToMatch.addCriteria(Criteria.where("title").is(newReviews.getTitle()));

            Query recordToRemove = new Query();
            recordToRemove.addCriteria(Criteria.where("critic_name").is(criticName));

            Update docRemoval = new Update().pull("critics_reviews", recordToRemove);
            result = mongoTemplate.updateFirst(recordToMatch, docRemoval,"anime_critics_db");

            log.info("Doc Removal is completed");

            log.info(result.toString());

            if(result.getModifiedCount()==0){
                return MutationResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .respMsg("No Matching records where found to delete")
                        .modifiedCount(String.valueOf(result.getModifiedCount()))
                        .upsertId(String.valueOf(result.getUpsertedId()))
                        .build();
            }

            return MutationResponse.builder()
                    .status(HttpStatus.OK)
                    .respMsg("Deleted Successfully")
                    .modifiedCount(String.valueOf(result.getModifiedCount()))
                    .upsertId(String.valueOf(result.getUpsertedId()))
                    .build();

        }catch(Exception ex){
            log.error(ex.getMessage());
            ex.printStackTrace();
            return MutationResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .respMsg(ex.getMessage())
                    .build();
        }
    }



    private Query QueryBuilder(CriticsSearchRequest criticsSearchRequest) {
        Query query = new Query();
        if (nonNull(criticsSearchRequest.getTitle())) {
            query.addCriteria(Criteria.where("title").regex(criticsSearchRequest.getTitle()));
        }

        query.fields().exclude("_id");
        return query;
    }


}
