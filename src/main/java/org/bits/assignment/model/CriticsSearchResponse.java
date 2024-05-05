package org.bits.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.List;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CriticsSearchResponse {

    public HttpStatus status;
    public String respMsg;
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
