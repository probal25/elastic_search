package com.probal.test_project.documents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.probal.test_project.helper.Indices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.Date;

@Document(indexName = Indices.PRODUCT_INDEX)
//@Setting(settingPath = "static/es-settings.json")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Text, name = "name")
    private String name;

    @Field(type = FieldType.Double, name = "price")
    private Double price;

    @Field(type = FieldType.Integer, name = "quantity")
    private Integer quantity;

    @Field(type = FieldType.Keyword, name = "category")
    private String category;

    @Field(type = FieldType.Text, name = "desc")
    private String desc;

    @Field(type = FieldType.Keyword, name = "manufacturer")
    private String manufacturer;

    @Field(type = FieldType.Date, name = "createdAt")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;


}
