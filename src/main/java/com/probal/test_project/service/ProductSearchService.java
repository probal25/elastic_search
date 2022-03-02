package com.probal.test_project.service;

import com.probal.test_project.dao.ProductDao;
import com.probal.test_project.documents.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSearchService {

    private final ProductDao productDao;

    @Autowired
    public ProductSearchService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void createProductIndexBulk(final List<Product> products) {
        productDao.saveAll(products);
    }

    public void createProductIndex(final Product product) {
        productDao.save(product);
    }
}
