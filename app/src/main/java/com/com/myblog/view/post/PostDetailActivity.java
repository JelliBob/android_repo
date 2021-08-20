package com.com.myblog.view.post;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.com.myblog.R;
import com.com.myblog.controller.PostController;
import com.com.myblog.controller.dto.CMRespDto;
import com.com.myblog.model.Post;
import com.com.myblog.view.CustomAppBarActivity;
import com.com.myblog.view.InitActivity;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostDetailActivity extends CustomAppBarActivity implements InitActivity {

    private static final String TAG = "PostDetailActivity";
    // 애플리케이션이 처음부터 끝까지 관리하는 어떤 클래스에 액티비티 컨텐스트를 전달하면 망함
    // 꼭 넘겨야한다면 Context 타입으로 넘겨야함
    private PostDetailActivity mContext = PostDetailActivity.this;

    private MaterialButton btnDelete, btnUpdateForm;
    private TextView tvBox;

    private PostController postController;
    private Post post;
    private int postId;

    @Override
    protected void onResume() {
        super.onResume();
        // 수정 완료가 되면 postId로 다시 데이터 갱신(레트로핏으로 다시 다운받는다는 뜻)
        // finish->resume->다시 다운받기 (직접 구현해보기)
        Log.d(TAG, "onResume: ");
        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        init();
        initLr();
        initSetting();
//        initData();
    }

    @Override
    public void init() {
        postController = new PostController();
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdateForm = findViewById(R.id.btnUpdateForm);
        tvBox = findViewById(R.id.tvBox);
    }

    @Override
    public void initLr() {
        btnUpdateForm.setOnClickListener(v->{
//           CMRespDto<Post> cm = postController.updateForm();

               // intent하고 그림을 이동시키는건 컨트롤러가 아니라 액티비티의 역할임 뷰 관련된 작업이니까 ㅇㅇ
               Intent intent = new Intent(
                       mContext,
                       PostUpdateActivity.class
               );
               intent.putExtra("post",post);
               mContext.startActivity(intent);
        });
        btnDelete.setOnClickListener(v->{
            postController.deleteById(post.getId()).enqueue(new Callback<CMRespDto>() {
                @Override
                public void onResponse(Call<CMRespDto> call, Response<CMRespDto> response) {
                    CMRespDto cm = response.body();
                    if(cm.getCode()==1){
                        // 이전 페이지들 다 삭제하고 PostListActivity만 남게
                        Intent intent = new Intent(
                                mContext,
                                PostListActivity.class
                        );

                        // Login -> List -> Detail
                        // Login -> List
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<CMRespDto> call, Throwable t) {

                }
            });
        });
    }

    @Override
    public void initSetting() {
        onAppBarSettings(true, "Detail");

        // 트럭 데이터 받기
        Intent getIntent = getIntent();
        postId = getIntent.getIntExtra("postId",0);
        if(postId == 0) finish();
    }

    @Override
    public void initData() {
        // 데이터 초기화하기

        postController.findById(postId).enqueue(new Callback<CMRespDto<Post>>() {
            @Override
            public void onResponse(Call<CMRespDto<Post>> call, Response<CMRespDto<Post>> response) {
                CMRespDto<Post> cm = response.body();
                if(cm.getCode()==1){
                    post = cm.getData();
                    tvBox.setText("");
                    tvBox.append("[id] " + cm.getData().getId()+"\n");
                    tvBox.append("[title] " + cm.getData().getTitle()+"\n");
                    tvBox.append("[content] " + cm.getData().getContent()+"\n");
                    tvBox.append("[username] " + cm.getData().getUser().getUsername()+"\n");
                    tvBox.append("[created] " + cm.getData().getCreated()+"\n");
                }
            }

            @Override
            public void onFailure(Call<CMRespDto<Post>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}