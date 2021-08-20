package com.com.myblog.view.post;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.com.myblog.R;
import com.com.myblog.controller.PostController;
import com.com.myblog.controller.dto.CMRespDto;
import com.com.myblog.model.Post;
import com.com.myblog.view.CustomAppBarActivity;
import com.com.myblog.view.InitActivity;
import com.com.myblog.view.post.adapter.PostListAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostListActivity extends CustomAppBarActivity implements InitActivity {

    private static final String TAG = "PostListActivity";
    private PostListActivity mContext = PostListActivity.this;

    private RecyclerView rvPostList;
    private RecyclerView.LayoutManager rvLayoutManager;

    private PostListAdapter postListAdapter;
    private PostController postController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        init();
        initLr();
        initSetting();
        initAdapter();
        initData();
    }

    @Override
    public void init() {
        postController = new PostController();
        rvPostList = findViewById(R.id.rvPostList);
    }

    @Override
    public void initLr() {

    }

    @Override
    public void initSetting() {
        onAppBarSettings("Home");
    }

    @Override
    public void initAdapter() {
        rvLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        rvPostList.setLayoutManager(rvLayoutManager);

        postListAdapter = new PostListAdapter(mContext);
        rvPostList.setAdapter(postListAdapter);
    }

    @Override
    public void initData() {
        postController.findAll().enqueue(new Callback<CMRespDto<List<Post>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<Post>>> call, Response<CMRespDto<List<Post>>> response) {
                CMRespDto<List<Post>> cm = response.body();
                if(cm.getCode() == 1){
                    postListAdapter.addItems(cm.getData());
                }
            }

            @Override
            public void onFailure(Call<CMRespDto<List<Post>>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}