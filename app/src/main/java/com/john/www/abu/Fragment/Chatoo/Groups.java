package com.john.www.abu.Fragment.Chatoo;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.john.www.abu.GroupsActivity.BasketBall;
import com.john.www.abu.GroupsActivity.Dancing;
import com.john.www.abu.GroupsActivity.Fashion;
import com.john.www.abu.GroupsActivity.FootballActivity;
import com.john.www.abu.GroupsActivity.Movies;
import com.john.www.abu.GroupsActivity.Musics;
import com.john.www.abu.GroupsActivity.NewsGroup;
import com.john.www.abu.GroupsActivity.Politics;
import com.john.www.abu.GroupsActivity.Relationship;
import com.john.www.abu.GroupsActivity.Swimming;
import com.john.www.abu.R;

/**
 * Created by MR AGUDA on 10-Mar-18.
 */

public class Groups extends Fragment implements View.OnClickListener {


    private TextView movies;
    private TextView relationship;
    private TextView musics;
    private TextView dancing;
    private TextView football;
    private TextView swimming;
    private TextView basketball;
    private TextView fashion;
    private TextView news;
    private TextView politics;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragments_group, container, false);

        movies = (TextView) v.findViewById(R.id.group_movies);
        relationship = (TextView) v.findViewById(R.id.group_relationship);
        musics = (TextView) v.findViewById(R.id.group_musics);
        basketball = (TextView) v.findViewById(R.id.group_basketball);
        football = (TextView) v.findViewById(R.id.gorup_football);
        swimming = (TextView) v.findViewById(R.id.group_swimming);
        dancing = (TextView) v.findViewById(R.id.group_dancing);
        fashion =(TextView)v.findViewById(R.id.group_fashion);
        news =(TextView)v.findViewById(R.id.group_news);
        politics =(TextView)v.findViewById(R.id.group_politics);

        movies.setOnClickListener(this);
        relationship.setOnClickListener(this);
        musics.setOnClickListener(this);
        news.setOnClickListener(this);
        dancing.setOnClickListener(this);
        swimming.setOnClickListener(this);
        football.setOnClickListener(this);
        fashion.setOnClickListener(this);
        basketball.setOnClickListener(this);
        fashion.setOnClickListener(this);



        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.group_movies:

               Intent movies = new Intent(getContext(), Movies.class);
               movies.putExtra("class", "Movies");
                startActivity(movies);
                break;
            case R.id.gorup_football:
                Intent football = new Intent(getContext(), FootballActivity.class);
                football.putExtra("class", "Football");

                startActivity(football);
                break;
            case R.id.group_relationship:
                Intent relationship = new Intent(getContext(), Relationship.class);
                relationship.putExtra("class", "Relationship");

                startActivity(relationship);

                break;
            case R.id.group_dancing:
                Intent dancing = new Intent(getContext(), Dancing.class);
                dancing.putExtra("class", "Dancing");

                startActivity(dancing);
                break;
            case R.id.group_basketball:
                Intent basketball = new Intent(getContext(), BasketBall.class);
                basketball.putExtra("class", "Basketball");

                startActivity(basketball);

                break;
            case R.id.group_swimming:
                Intent swimming = new Intent(getContext(), Swimming.class);
                swimming.putExtra("class", "Swimming");
                startActivity(swimming);
                break;
            case R.id.group_musics:
                Intent musics = new Intent(getContext(), Musics.class);
                musics.putExtra("class", "Musics");
                startActivity(musics);


                break;
            case R.id.group_politics:
                Intent politics = new Intent(getContext(), Politics.class);
                politics.putExtra("class", "Politics");
                startActivity(politics);
                break;
            case R.id.group_news:
                Intent news = new Intent(getContext(), NewsGroup.class);
                news.putExtra("class", "News");
                startActivity(news);
                break;

            case R.id.group_fashion:
                Intent fashion = new Intent(getContext(), Fashion.class);
                fashion.putExtra("class", "Fashion");
                startActivity(fashion);
                break;

        }

    }







}
