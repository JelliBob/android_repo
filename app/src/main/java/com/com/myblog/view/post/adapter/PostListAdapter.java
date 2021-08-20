package com.com.myblog.view.post.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.com.myblog.R;
import com.com.myblog.controller.PostController;
import com.com.myblog.controller.dto.CMRespDto;
import com.com.myblog.model.Post;
import com.com.myblog.view.post.PostDetailActivity;
import com.com.myblog.view.post.PostListActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.MyViewHolder> {

    private static final String TAG = "PostListAdapter";
    private PostListAdapter postListAdapter = PostListAdapter.this;
    private PostListActivity mContext;
    private PostController postController = new PostController();

    public PostListAdapter(PostListActivity mContext){
        this.mContext = mContext;
    }

    // 3. 컬렉션
    private List<Post> posts = new ArrayList<>();

    // 4. 컬렉션 데이터 세팅
    public void addItems(List<Post> posts){
        this.posts = posts;
        Log.d(TAG, "addItems: size : "+posts.size());
        notifyDataSetChanged();
    }

    public void addItem(Post post){
        this.posts.add(post);
        notifyDataSetChanged();
        //mContext.mRvScroll();
    }

    public List<Post> getItems(){
        return posts;
    }

    public void removeItem(int index){
        posts.remove(index);
        notifyDataSetChanged();
    }

    // ViewHolder 객체 만드는 친구
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =  layoutInflater.inflate(R.layout.post_list_item, parent, false);

        return new MyViewHolder(view);
    }

    // ViewHolder 데이터 갈아끼우는 친구
    @Override
    public void onBindViewHolder(PostListAdapter.MyViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.setItem(post);
    }

    // 어댑터가 알아서 호출해서 사이즈 2?
    // 화면크기가 600
    // 아이템 크기가 200
    @Override
    public int getItemCount() {
        return posts.size();
    }

    // 1. 뷰홀더 만들기 - 데이터 갈아 끼우는 친구!!
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvPostId, tvPostTitle;

        // 앱 구동시에 발동
        public MyViewHolder(View itemView) {
            super(itemView);
            tvPostId = itemView.findViewById(R.id.tvPostId);
            tvPostTitle = itemView.findViewById(R.id.tvPostTitle);

            initLr();
        }

        private void initLr(){

            itemView.setOnClickListener(v -> {
                // 클릭할때 상세보기 화면으로 인텐트
                Log.d(TAG, "initLr: 클릭됨 "+getAdapterPosition()); // 현재 클릭된 데이터를 어댑터가 가지고 있음
                Post post = posts.get(getAdapterPosition());
                Log.d(TAG, "initLr: post.getId()"+post.getId());
                postController.findById(post.getId()).enqueue(new Callback<CMRespDto<Post>>() {
                    @Override
                    public void onResponse(Call<CMRespDto<Post>> call, Response<CMRespDto<Post>> response) {

                        CMRespDto<Post> cm = response.body();
                        Intent intent = new Intent(
                                mContext,
                                PostDetailActivity.class
                        );
                        intent.putExtra("postId",cm.getData().getId());
                        mContext.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<CMRespDto<Post>> call, Throwable t) {

                    }
                });

            });
        }

        // 앱 구동시에 발동 + 스크롤할 때 발동
        public void setItem(Post post){
            tvPostId.setText(post.getId()+"");
            tvPostTitle.setText(post.getTitle());
        }
    }
}
