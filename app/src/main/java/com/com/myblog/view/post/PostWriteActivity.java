package com.com.myblog.view.post;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.com.myblog.R;
import com.com.myblog.config.SessionUser;
import com.com.myblog.controller.PostController;
import com.com.myblog.controller.dto.CMRespDto;
import com.com.myblog.controller.dto.PostDto;
import com.com.myblog.model.Post;
import com.com.myblog.view.CustomAppBarActivity;
import com.com.myblog.view.InitActivity;
import com.com.myblog.view.post.adapter.PostListAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostWriteActivity extends CustomAppBarActivity implements InitActivity {

    private static final String TAG = "PostWriteActivity";
    private PostWriteActivity mContext = PostWriteActivity.this;

    private MaterialButton btnWrite;
    private TextInputEditText tfTitle, tfContent;

    private PostController postController;
    private PostListActivity postListActivity = new PostListActivity();
    private PostListAdapter postListAdapter = new PostListAdapter(postListActivity);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_write);

        init();
        initLr();
        initSetting();
    }

    @Override
    public void init() {
        postController = new PostController();
        btnWrite = findViewById(R.id.btnWrite);
        tfTitle = findViewById(R.id.tfTitle);
        tfContent = findViewById(R.id.tfContent);
    }

    @Override
    public void initLr() {
        btnWrite.setOnClickListener(v->{
            PostDto postDto =
                    new PostDto(
                            tfTitle.getText().toString(),
                            tfContent.getText().toString()
                    );

            Post post1 = new Post();
            post1.setTitle(postDto.getTitle());
            post1.setContent(postDto.getContent());
            post1.setUser(SessionUser.user);
            postListAdapter.addItem(post1);


            postController.write(postDto).enqueue(new Callback<CMRespDto<Post>>() {
                @Override
                public void onResponse(Call<CMRespDto<Post>> call, Response<CMRespDto<Post>> response) {
                    CMRespDto<Post> cm = response.body();

                    Log.d(TAG, "onResponse: 글쓰기 버튼 클릭 "+cm.getData());
                    if(cm.getCode() == 1){
                        Intent intent = new Intent(
                                mContext,
                                PostListActivity.class
                        );
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<CMRespDto<Post>> call, Throwable t) {

                }
            });
        });
    }

    @Override
    public void initSetting() {
        onAppBarSettings(true, "Write");
    }
}