package com.john.www.abu.Fragment.Post000;


import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.john.www.abu.Adapters.PostAdapter_holder;
import com.john.www.abu.GlideApp;

import com.john.www.abu.PostStuffs.PostCreateDialog;
import com.john.www.abu.PostStuffs.models.PostModel;

import com.john.www.abu.R;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by MR AGUDA on 10-Mar-18.
 */

public abstract class PostListFragment extends Fragment {

    private View mRootVIew;
    private PostAdapter_holder mPostAdapter;
    private RecyclerView mPostRecyclerView;
    private LinearLayoutManager mManager;
    private FloatingActionButton fab;
    private List<PostModel> postModels = new ArrayList<PostModel>();




    public PostListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootVIew = inflater.inflate(R.layout.posts, container, false);
        fab = (FloatingActionButton) mRootVIew.findViewById(R.id.fab);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /* PostCreateDialog dialog = new com.john.www.abu.PostStuffs.PostCreateDialog();
                dialog.show(getFragmentManager(), null);*/
              String doc = " ";
              String value = getString(doc);
              if(value != null) {
                  Intent post = new Intent(getActivity(), PostCreateDialog.class);
                  post.putExtra("just", value);
                  startActivity(post);
              }else {
                  startActivity(new Intent(getActivity(), PostCreateDialog.class));
              }
            }
        });
        mPostAdapter = new PostAdapter_holder(getActivity(), postModels);
        this.FetchData();
        init(container);

        return mRootVIew;
    }

    private void init(ViewGroup container) {


        mManager = new LinearLayoutManager(container.getContext());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mPostRecyclerView = (RecyclerView) mRootVIew.findViewById(R.id.recyclerview_post);
        mPostRecyclerView.setLayoutManager(mManager);
        mPostRecyclerView.setAdapter(mPostAdapter);

        mPostRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Boolean reachedBottom = !recyclerView.canScrollVertically(1);


                if(dy>0) {

                    fab.setVisibility(View.GONE);
                }else{
                    fab.setVisibility(View.VISIBLE);
                }

            }
        });



    }


    private void FetchData(){

            Query query = getQuery();

            query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    postModels.clear();
                    if(e!=null){
                        Toast.makeText(getContext(), "Error"+e, Toast.LENGTH_SHORT).show();
                        return;

                    }


                    for(QueryDocumentSnapshot queryDocumentSnapshot :queryDocumentSnapshots){

                        PostModel postModel = queryDocumentSnapshot.toObject(PostModel.class);
                        if(postModel != null){


                            postModels.add(postModel);
                            mPostAdapter.notifyDataSetChanged();
                        }
                    }

                }
            });



    }



    public abstract Query getQuery();
    public abstract String getString(String values);
}



