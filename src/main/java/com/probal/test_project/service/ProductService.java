package com.probal.test_project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.probal.test_project.dao.ProductDao;
import com.probal.test_project.documents.Product;
import com.probal.test_project.helper.Indices;
import com.probal.test_project.search.dto.SearchRequestDTO;
import com.probal.test_project.search.util.SearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final ProductDao productDao;
    private final RestHighLevelClient client;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    public ProductService(ProductDao productDao, RestHighLevelClient client) {
        this.client = client;
        this.productDao = productDao;
    }

    public void createProductIndexBulk(final List<Product> products) {
        productDao.saveAll(products);
    }

    public void createProductIndex(final Product product) {
        productDao.save(product);
    }

    public Product findProductById(final String id) {
        return productDao.findById(id).orElse(null);
    }

    public List<Product> search(final SearchRequestDTO searchRequestDTO) {
        final SearchRequest request = SearchUtil.buildSearchRequest(Indices.PRODUCT_INDEX, searchRequestDTO);

        return searchInternal(request);
    }

    public List<Product> getAllProductCreatedSince(final Date date) {
        final SearchRequest request = SearchUtil.buildSearchRequest(
                Indices.PRODUCT_INDEX,
                "createdAt",
                date);
        return searchInternal(request);
    }


    private List<Product> searchInternal(final SearchRequest request) {
        if (request == null) {
            log.error("failed to build search request");
            return Collections.emptyList();
        }

        try {
            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            final SearchHit[] searchHits = response.getHits().getHits();
            final List<Product> products = new ArrayList<>(searchHits.length);
            for (SearchHit searchHit : searchHits) {
                String source = searchHit.getSourceAsString();
                products.add(
                        MAPPER.readValue(source, Product.class)
                );
            }
            log.info("Products found: " + products);
            return products;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
