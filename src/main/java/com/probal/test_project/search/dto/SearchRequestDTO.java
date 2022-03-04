package com.probal.test_project.search.dto;

import lombok.Data;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;

@Data
public class SearchRequestDTO {

    private List<String> fields;
    private String searchTerm;
    private String sortBy;
    private SortOrder sortOrder;
}
