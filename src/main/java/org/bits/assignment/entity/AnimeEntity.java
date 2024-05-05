package org.bits.assignment.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.util.List;


@Sharded(shardKey = {"id"})
@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "anime_critics_db")
public class AnimeEntity {

    @Id
    @Indexed(unique = true)
    private String _id;
    private String title;
    private String description;
    private List<CriticsReviews> critics_reviews;

}
