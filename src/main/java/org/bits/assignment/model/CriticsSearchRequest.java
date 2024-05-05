package org.bits.assignment.model;

import lombok.Data;

import java.util.List;

@Data
public class CriticsSearchRequest {

    private String _id;
    private String title;
    private List<String> tags;
    private String description;
    private int pageSzie;
    private int pageNbr;
}
