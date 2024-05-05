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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "anime_critics_db")
public class CriticsEntity {

    @Id
    @Indexed(unique = true)
    private String _id;
    private String title;
    private String description;
    private String mediaType;
    private List<String> tags;
    private String startYr;
    private String sznOfRelease;
    private String eps;
    private List<String> contentWarn;
    private Boolean ongoing;
    private String finishYr;
    private List<String> studios;
    private Integer watched;
    private Integer watching;
    private Integer wantWatch;
    private Integer dropped;
    private Integer rating;
    private Integer votes;

}
