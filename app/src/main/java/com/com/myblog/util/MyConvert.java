package com.com.myblog.util;

import com.com.myblog.model.Post;
import com.com.myblog.controller.dto.CMRespDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Response;

public class MyConvert {

    public static List<Post> toPostCollect(Response<CMRespDto> response){
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Post>>(){}.getType();
        List<Post> result = gson.fromJson(gson.toJson(response.body().getData()), collectionType);
        return result;
    }
}
