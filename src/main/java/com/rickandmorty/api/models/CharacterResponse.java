package com.rickandmorty.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterResponse {
    private List<Character> results;
    private String error;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Character {
        private int id;
        private String name;
        private String status;
    }
}