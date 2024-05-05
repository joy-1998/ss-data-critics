package org.bits.assignment.repositories;

import org.bits.assignment.entity.AnimeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface CriticsRepository extends MongoRepository<AnimeEntity, UUID> {


}
