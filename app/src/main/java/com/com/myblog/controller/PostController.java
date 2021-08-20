package com.com.myblog.controller;

import android.util.Log;

import com.com.myblog.controller.dto.CMRespDto;
import com.com.myblog.controller.dto.PostDto;
import com.com.myblog.controller.dto.PostUpdateDto;
import com.com.myblog.model.Post;
import com.com.myblog.service.PostService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// VIEW와 통신하는 중개자
public class PostController {

    private static final String TAG = "PostController";
    private PostService postService = PostService.service;

    public Call<CMRespDto<Post>> write(PostDto postDto){
        return postService.write(postDto);
    }

    public Call<CMRespDto<List<Post>>> findAll(){
        return postService.findAll();
    }

    public Call<CMRespDto<Post>> findById(int postId){
        return postService.findById(postId);
    }

    // 글 삭제하기
    public Call<CMRespDto> deleteById(int postId){
        return postService.deleteById(postId);
    }

    // 글 수정 페이지로 이동하기
//    public CMRespDto<Post> updateForm(){ // 컨텍스트를 전역변수가 아니라 지역변수로 받음 메소드 실행시점에만 뜸
//        return null;
//    }

    public Call<CMRespDto<Post>> update(int postId, PostUpdateDto postUpdateDto){
        return postService.update(postId, postUpdateDto);
    }

    public void initPost(){
        Log.d(TAG, "initPost: ");
        postService.initPost().enqueue(new Callback<CMRespDto<List<Post>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<Post>>> call, Response<CMRespDto<List<Post>>> response) {
                CMRespDto<List<Post>> cm = response.body();
                Log.d(TAG, "onResponse: title :"+cm.getData().get(0).getTitle());
            }

            @Override
            public void onFailure(Call<CMRespDto<List<Post>>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
