package com.probal.test_project.controller;

import com.probal.test_project.documents.Product;
import com.probal.test_project.search.dto.SearchRequestDTO;
import com.probal.test_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void index(@RequestBody final Product product) {
        productService.createProductIndex(product);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product findProductById(@PathVariable final String id) {
        return productService.findProductById(id);
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<Product> genericProductSearch(@RequestBody final SearchRequestDTO searchRequestDTO) {
        return this.productService.search(searchRequestDTO);
    }

    @ResponseBody
    @RequestMapping(value = "/search/{date}", method = RequestMethod.GET)
    public List<Product> getAllProductCreatedSince(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return this.productService.getAllProductCreatedSince(date);
    }

    @ResponseBody
    @RequestMapping(value = "/generic_search/{date}", method = RequestMethod.POST)
    public List<Product> productGenericSearch(@RequestBody final SearchRequestDTO searchRequestDTO,
                                              @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return this.productService.productGenericSearch(searchRequestDTO, date);
    }
}
