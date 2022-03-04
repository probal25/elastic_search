package com.probal.test_project.search.util;

import com.probal.test_project.search.dto.SearchRequestDTO;
import lombok.NoArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
public class SearchUtil {

    public static SearchRequest buildSearchRequest(final String indexName, final SearchRequestDTO searchRequestDTO) {
        try {
            SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getQueryBuilder(searchRequestDTO));

            if (searchRequestDTO.getSortBy() != null) {
                builder = builder.sort(
                        searchRequestDTO.getSortBy(),
                        searchRequestDTO.getSortOrder() != null ? searchRequestDTO.getSortOrder() : SortOrder.ASC
                );
            }

            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);
            return request;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // method overloaded for range query
    public static SearchRequest buildSearchRequest(final String indexName, final String field, final Date date) {
        try {
            SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getQueryBuilder(field, date));
            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static QueryBuilder getQueryBuilder(final SearchRequestDTO searchRequestDTO) {
        if (searchRequestDTO == null) {
            return null;
        }

        final List<String> fields = searchRequestDTO.getFields();
        if (CollectionUtils.isEmpty(fields)) {
            return null;
        }

        if (fields.size() > 1) {
            MultiMatchQueryBuilder queryBuilder = QueryBuilders
                    .multiMatchQuery(searchRequestDTO.getSearchTerm())
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
                    .operator(Operator.AND);
            fields.forEach(queryBuilder::field);
            return queryBuilder;
        }
        return fields.stream()
                .findFirst()
                .map(field ->
                        QueryBuilders
                                .matchQuery(field, searchRequestDTO.getSearchTerm())
                                .operator(Operator.AND)
                ).orElse(null);
    }

    // for range query
    public static QueryBuilder getQueryBuilder(final String field, final Date date) {
        return QueryBuilders.rangeQuery(field).gte(date);
    }
}
