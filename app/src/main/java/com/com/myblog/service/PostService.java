package com.com.myblog.service;

import com.com.myblog.config.SessionInterceptor;
import com.com.myblog.controller.dto.CMRespDto;
import com.com.myblog.controller.dto.PostDto;
import com.com.myblog.controller.dto.PostUpdateDto;
import com.com.myblog.model.Post;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

// 인증이 필요 없는 주소 /auth
// 인증이 필요한 주소 /user, /post
public interface PostService {

    @POST("/post")
    Call<CMRespDto<Post>> write( @Body PostDto postDto);

    @PUT("/post/{id}")
    Call<CMRespDto<Post>> update(@Path("id") int id, @Body PostUpdateDto postUpdateDto);

    @DELETE("/post/{id}") // 추후 주소 변경 필요 : /post
    Call<CMRespDto> deleteById(@Path("id") int id);

    // : 앞뒤로 띄어쓰기 하면 안 됨 규칙임
    @GET("/post/{id}") // 추후 주소 변경 필요 : /post
    Call<CMRespDto<Post>> findById(@Path("id") int id);

    // : 앞뒤로 띄어쓰기 하면 안 됨 규칙임
    @GET("/post") // 추후 주소 변경 필요 : /post
    Call<CMRespDto<List<Post>>> findAll();

    // test
    @GET("/init/post")
    Call<CMRespDto<List<Post>>> initPost();

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new SessionInterceptor()).build();

    Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.0.6:8080")
            .build();


    PostService service = retrofit.create(PostService.class);
}
