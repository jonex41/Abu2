package com.john.www.abu.GameStuffs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.john.www.abu.R;

public class GameActivity extends AppCompatActivity  implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbargame);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView loveCalculator = (TextView) findViewById(R.id.love_calculator);
        loveCalculator.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.love_calculator:
                startActivity(new Intent(GameActivity.this, LoveCalculator.class));
        }

    }
}
