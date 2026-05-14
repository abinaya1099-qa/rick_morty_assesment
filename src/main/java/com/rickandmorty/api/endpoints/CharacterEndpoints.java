package com.rickandmorty.api.endpoints;

public final class CharacterEndpoints {
    public static final String CHARACTERS = "character";

    public static String byId(String id) {
        return CHARACTERS + "/" + id;
    }

    private CharacterEndpoints() {}
}