package org.bits.assignment.model;

import lombok.Data;
import org.bits.assignment.entity.CriticsReviews;

import java.util.List;

@Data
public class CriticsSearchRequest {

    private String _id;
    private String title;
    private String description;
    private List<CriticsReviews> critics_reviews;
}
