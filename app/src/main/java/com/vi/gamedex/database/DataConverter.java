package com.vi.gamedex.database;

import androidx.room.TypeConverter;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.vi.gamedex.model.Artwork;
import com.vi.gamedex.model.Cover;
import com.vi.gamedex.model.Game;
import com.vi.gamedex.model.Platform;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataConverter {


    // ARTWORK
    @TypeConverter
    public String fromArtworkList(List<Artwork> artworkList) {
        if (artworkList == null) {
            return (null);
        }
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Artwork.class);
        JsonAdapter<List> jsonAdapter = moshi.adapter(type);
        String json = jsonAdapter.toJson(artworkList);
        return json;
    }

    @TypeConverter
    public List<Artwork> toArtworkList(String jsonString) {
        if (jsonString == null) {
            return (null);
        }
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Artwork.class);
        JsonAdapter<List> jsonAdapter = moshi.adapter(type);
        List<Artwork> artworkList = new ArrayList<>();

        try {
           artworkList = jsonAdapter.fromJson(jsonString);
        } catch (IOException e){
            e.printStackTrace();
        }
        return artworkList;
    }

    @TypeConverter
    public String fromPlatformList(List<Platform> platformList) {
        if (platformList == null) {
            return (null);
        }
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Platform.class);
        JsonAdapter<List> jsonAdapter = moshi.adapter(type);
        String json = jsonAdapter.toJson(platformList);
        return json;
    }

    @TypeConverter
    public List<Platform> toPlatformList(String jsonString) {
        if (jsonString == null) {
            return (null);
        }
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Platform.class);
        JsonAdapter<List> jsonAdapter = moshi.adapter(type);
        List<Platform> platformList = new ArrayList<>();

        try {
            platformList = jsonAdapter.fromJson(jsonString);
        } catch (IOException e){
            e.printStackTrace();
        }
        return platformList;
    }


    @TypeConverter
    public String fromCover( Cover cover ) {
        if (cover == null) {
            return (null);
        }
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Cover> jsonAdapter = moshi.adapter(Cover.class);
        String json = jsonAdapter.toJson(cover);
        return json;

    }

    @TypeConverter
    public Cover toCover (String jsonString) {
        if (jsonString == null) {
            return (null);
        }
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Cover> jsonAdapter = moshi.adapter(Cover.class);
        Cover cover = new Cover();

        try {
            cover = jsonAdapter.fromJson(jsonString);
        } catch (IOException e){
            e.printStackTrace();
        }
        return cover;
    }





}

/*
public class DataConverter {

    @TypeConverter
    public String fromCountryLangList(List<CountryLang> countryLang) {
        if (countryLang == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<CountryLang>>() {}.getType();
        String json = gson.toJson(countryLang, type);
        return json;
    }

    @TypeConverter
    public List<CountryLang> toCountryLangList(String countryLangString) {
        if (countryLangString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<CountryLang>>() {}.getType();
        List<CountryLang> countryLangList = gson.fromJson(countryLangString, type);
        return countryLangList;
    }
 }
 */
