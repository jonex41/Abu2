package com.john.www.abu.GameStuffs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.john.www.abu.R;

import java.util.Random;

public class LoveCalculator extends AppCompatActivity implements View.OnClickListener {

    private int numbergenerated;
    private EditText girl_name;
    private EditText boy_name;
    private Button summit_result;


    private TextView calculating;
    private TextView result;
    private String girlName;
    private String boyName;

    public static final String BOY ="boy" ;
    public static final String GIRL = "girl";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.love_calculator_activity);



        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbarlove);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        girl_name = (EditText) findViewById(R.id.girl_edt);

        boy_name = (EditText) findViewById(R.id.boy_edt);

       /* girl_name.setText(" ");
        boy_name.setText(" ");*/
      //  result = (TextView) findViewById(R.id.result);


        summit_result = (Button) findViewById(R.id.btn_calculate_love);
        summit_result.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_calculate_love:
                    girlName = girl_name.getText().toString();
                    boyName = boy_name.getText().toString();
                if( !girlName.isEmpty() && !boyName.isEmpty()){
                    if(!girlName.contains(boyName)) {

                        openActivity(boy_name.getText().toString(), girl_name.getText().toString());
                    }else {
                        Toast.makeText(getApplicationContext(), "Please specify different name", Toast.LENGTH_LONG).show();

                    }
                }else {
                    Toast.makeText(getApplicationContext(), "please make sure all fileds are filled", Toast.LENGTH_LONG).show();

                }
        }



        }



        public void openActivity(String boy, String girl){
            Intent intent = new Intent(LoveCalculator.this, LoveDatails.class);
            intent.putExtra(BOY, boy);
            intent.putExtra(GIRL, girl );
            startActivity(intent);
            girl_name.setText(" ");
            boy_name.setText(" ");

        }

}


