package com.john.www.abu.Activitiesss;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.john.www.abu.R;
import com.theartofdev.edmodo.cropper.CropImage;

public class ReactToSelectedImage extends AppCompatActivity {

    private Uri resultUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_react_to_selected_image);

        ImageButton imageButton = (ImageButton) findViewById(R.id.btnSendpicture);
        ImageView picturegotten = (ImageView) findViewById(R.id.myimageview);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                resultUrl = result.getUri();





               /* }else {
                    Toast.makeText(ChatActivity.this, "Unable to get image", Toast.LENGTH_LONG).show();
                }*/

            }
        }
    }
}
