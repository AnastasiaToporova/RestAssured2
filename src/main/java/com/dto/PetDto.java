package com.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;


@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PetDto {

    private int id;
    private Category category;
    private String name;
    private String[] photoUrls;
    private Tags[] tags;
    private String status;


    public PetDto setId(int id) {
        this.id = id;
        return this;
    }

    public PetDto setCategory(Category category) {
        this.category = category;
        return this;
    }

    public PetDto setName(String name) {
        this.name = name;
        return this;
    }

    public PetDto setPhotoUrls(String[] photoUrl) {
        this.photoUrls = photoUrl;
        return this;
    }

    public PetDto setTags(Tags[] tags) {
        this.tags = tags;
        return this;
    }

    public PetDto setStatus(String status) {
        this.status = status;
        return this;
    }

}
