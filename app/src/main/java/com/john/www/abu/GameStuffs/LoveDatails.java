package com.john.www.abu.GameStuffs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.john.www.abu.R;

import java.util.Random;

public class LoveDatails extends AppCompatActivity implements View.OnClickListener{

    private int numbergenerated;
    final Random r = new Random();
    String boyName;
    String girlName;

    private TextView boynametxt;
    private TextView girlNametxt;
    private TextView percentage;
    private TextView result;
    private int percentValue = 0;
    private String resultString =" ";

    private Button btn_exit;
    private ProgressDialog progressDialog;

    private LoveDatabaseActivity loveDatabaseActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lovedetails_activity);

        loveDatabaseActivity = new LoveDatabaseActivity(LoveDatails.this);


        boyName = getIntent().getStringExtra(LoveCalculator.BOY);
        girlName = getIntent().getStringExtra(LoveCalculator.GIRL);
        boynametxt = (TextView) findViewById(R.id.boyid);
        girlNametxt = (TextView) findViewById(R.id.girlid);
        percentage = (TextView) findViewById(R.id.percenttxt);
        result = (TextView) findViewById(R.id.resultid);

        btn_exit = (Button) findViewById(R.id.exitlovedetail);
        btn_exit.setOnClickListener(this);
        if (!loveDatabaseActivity.checkUser(boyName, girlName)){
            doThisOncreate();
            LoveModel loveModel = new LoveModel();
            loveModel.setPercent_value(percentValue+ " ");
            loveModel.setMessage_love(resultString);
            loveModel.setGirl_name(girlName);
            loveModel.setBoy_name(boyName);
            loveDatabaseActivity.addtoCart(loveModel);

            percentage.setText(percentValue+"%");
        }else {

       LoveModel loveModel=  loveDatabaseActivity.getindivMessages(boyName, girlName);

       result.setText(loveModel.getMessage_love());
       percentage.setText(loveModel.getPercent_value() +"%");
        }
        new LoadViewTask().execute();

        boynametxt.setText(boyName);
        girlNametxt.setText(girlName);


    }




    private void doThisOncreate() {
        numbergenerated = r.nextInt(15)+1;
        switch (numbergenerated){

            case 1:
                percentValue =1;
                result.setText("gosh, look for another crush");
                resultString = result.getText().toString();
                break;
            case 2:
                percentValue =2;
                result.setText("holy moly, why did you choose her, no wrong option");
                resultString = result.getText().toString();
                break;
            case 3:
                percentValue =3;
                result.setText(" wow, what a bad combination");
                resultString = result.getText().toString();
                break;
            case 4:
                percentValue =4;
                result.setText("dem, it a bad omen please switch");
                resultString = result.getText().toString();
                break;

            case 5:
                percentValue =5;
                result.setText("proabably like you in the future, but not now");
                resultString = result.getText().toString();
                break;
            case 6:
                percentValue =6;
                result.setText("Love is blind, but this one is stupid");
                resultString = result.getText().toString();
                break;

            case 7:
                percentValue =7;
                result.setText("chai nawo ooo, love lorn");
                resultString = result.getText().toString();
                break;
            case 8:
                percentValue =8;
                result.setText("unfortunate for you, she loves another");
                resultString = result.getText().toString();
                break;
            case 9:
                percentValue =9;
                result.setText(" why dont you look for another, am not available ");
                resultString = result.getText().toString();
                break;

            case 10:
                percentValue =10;
                result.setText("bang, bang and bang, no not at all");
                resultString = result.getText().toString();
                break;
            case 11:
                percentValue =11;
                result.setText("this is surely love lorn, am sorry");
                resultString = result.getText().toString();
                break;
            case 12:
                percentValue =12;
                result.setText("what a choice, but menn you are not a match");
                resultString = result.getText().toString();
                break;
            case 13:
                percentValue =13;
                result.setText("there may be love involve, but am not sure, check it out");
                resultString = result.getText().toString();
                break;

            case 14:
                percentValue =14;
                result.setText(" may probably give you a chance, if you try harder");
                resultString = result.getText().toString();
                break;

            case 15:
                percentValue =15;
                result.setText("what if you were not meant to be");
                resultString = result.getText().toString();
                break;

        }
    }

    //To use the AsyncTask, it must be subclassed
    private class LoadViewTask extends AsyncTask<Void, Integer, Void> {
        //Before running code in separate thread
        @Override
        protected void onPreExecute()
        {
            //Create a new progress dialog
            progressDialog = new ProgressDialog(LoveDatails.this);
            //Set the progress dialog to display a horizontal progress bar
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            //Set the dialog title to 'Loading...'
            progressDialog.setTitle("Love");
            //Set the dialog message to 'Loading application View, please wait...'
            progressDialog.setMessage("Calculating possibility of love...");
            //This dialog can't be canceled by pressing the back key
            progressDialog.setCancelable(false);
            //This dialog isn't indeterminate
            progressDialog.setIndeterminate(false);
            //The maximum number of items is 100
            progressDialog.setMax(100);
            //Set the current progress to zero
            progressDialog.setProgress(0);
            //Display the progress dialog
            progressDialog.show();
        }

        //The code to be executed in a background thread.
        @Override
        protected Void doInBackground(Void... params)
        {
            /* This is just a code that delays the thread execution 4 times,
             * during 850 milliseconds and updates the current progress. This
             * is where the code that is going to be executed on a background
             * thread must be placed.
             */
            try
            {
                //Get the current thread's token
                synchronized (this)
                {
                    //Initialize an integer (that will act as a counter) to zero
                    int counter = 0;
                    //While the counter is smaller than four
                    while(counter <= 4)
                    {
                        //Wait 850 milliseconds
                        this.wait(900);
                        //Increment the counter
                        counter++;
                        //Set the current progress.
                        //This value is going to be passed to the onProgressUpdate() method.
                        publishProgress(counter*25);
                    }
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        //Update the progress
        @Override
        protected void onProgressUpdate(Integer... values)
        {

            //set the current progress of the progress dialog
            progressDialog.setProgress(values[0]);
        }

        //after executing the code in the thread
        @Override
        protected void onPostExecute(Void result)
        {
            //close the progress dialog
            progressDialog.dismiss();
            //initialize the View

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.exitlovedetail:

               // startActivity(new Intent(LoveDatails.this, LoveCalculator.class));
                finish();
                break;




        }
    }
}
