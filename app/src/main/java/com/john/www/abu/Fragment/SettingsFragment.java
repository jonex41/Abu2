package com.john.www.abu.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.john.www.abu.Activitiesss.AddFriendActivity;
import com.john.www.abu.Activitiesss.CreateGroup;
import com.john.www.abu.Activitiesss.ProfileActivity;
import com.john.www.abu.Activitiesss.SelectedMemberOfGroup;
import com.john.www.abu.Activitiesss.SettingsAvtivity;
import com.john.www.abu.DatabaseStuffs.sqlMessage.DatMessage;
import com.john.www.abu.DatabaseStuffs.sqlUsers.DatabaseHelper;
import com.john.www.abu.GameStuffs.GameActivity;
import com.john.www.abu.R;
import com.john.www.abu.Rgistration.SignActivity;


public class SettingsFragment extends Fragment implements View.OnClickListener {


    private static final int PICKFILE_RESULT_CODE = 23;
    private FirebaseAuth mAuth;
    private FragmentManager fm;
    private String userId;


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
         fm = getFragmentManager();

          ImageButton button = (ImageButton)rootView.findViewById(R.id.fragmentclose);
        Button profileFragment = (Button) rootView.findViewById(R.id.profilefragment);
        Button exitfinish = (Button)rootView.findViewById(R.id.exitfinish);

        Button creategroup = (Button)rootView.findViewById(R.id.btncreate_group);
        Button addnewFriend = (Button)rootView.findViewById(R.id.addnew_friend);
        Button game_play = (Button)rootView.findViewById(R.id.games_play);
        Button wifi_action_name = (Button)rootView.findViewById(R.id.wifi_action_name);
                game_play.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        wifi_action_name.setOnClickListener(this);
        addnewFriend.setOnClickListener(this);
        creategroup.setOnClickListener(this);
        exitfinish.setOnClickListener(this);
        Button settingsactivity = (Button) rootView.findViewById(R.id.settingsni);
        settingsactivity.setOnClickListener(this);
        profileFragment.setOnClickListener(this);
        FrameLayout frameLayout = (FrameLayout) rootView.findViewById(R.id.frame);
        addShowHideListenerlayout(frameLayout, fm.findFragmentById(R.id.fragment));
        addShowHideListener(button, fm.findFragmentById(R.id.fragment));
        return rootView;
    }

    void addShowHideListener(final ImageButton button, final Fragment fragment) {

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
               /* ft.setCustomAnimations(R.anim.slide_in_top,
                        R.anim.slide_out_bottom);*/
                if (fragment.isHidden()) {
                    ft.show(fragment);

                } else {
                    ft.hide(fragment);
                }
                ft.commit();
            }
        });
    }
    void addShowHideListenerlayout(final FrameLayout button, final Fragment fragment) {

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
               /* ft.setCustomAnimations(R.anim.slide_in_top,
                        R.anim.slide_out_bottom);*/
                if (fragment.isHidden()) {
                    ft.show(fragment);

                } else {
                    ft.hide(fragment);
                }
                ft.commit();
            }
        });
    }

    void addShowHideListenerlayout( final Fragment fragment) {


                FragmentTransaction ft = getFragmentManager().beginTransaction();
               /* ft.setCustomAnimations(R.anim.slide_in_top,
                        R.anim.slide_out_bottom);*/
                if (fragment.isHidden()) {
                    ft.show(fragment);

                } else {
                    ft.hide(fragment);
                }
                ft.commit();


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.profilefragment:
                addShowHideListenerlayout( fm.findFragmentById(R.id.fragment));
                startActivity(new Intent(getContext(), ProfileActivity.class));
                break;
            case R.id.settingsni:

             //   Intent intent4 = new Intent();
               // intent4.setAction(Intent.ACTION_GET_CONTENT);
              //  intent4.setType("*/*");
             //   startActivityForResult(intent4,PICKFILE_RESULT_CODE);
                startActivity(new Intent(getContext(), SettingsAvtivity.class));
               addShowHideListenerlayout( fm.findFragmentById(R.id.fragment));


                break;
            case R.id.addnew_friend:
              startActivity(new Intent(getContext(), AddFriendActivity.class));
                addShowHideListenerlayout( fm.findFragmentById(R.id.fragment));
                break;
            case R.id.btncreate_group:
                startActivity(new Intent(getContext(), SelectedMemberOfGroup.class));
                addShowHideListenerlayout( fm.findFragmentById(R.id.fragment));

                break;

            case R.id.games_play:

                startActivity(new Intent(getContext(), GameActivity.class));
                addShowHideListenerlayout( fm.findFragmentById(R.id.fragment));
                break;
            case R.id.wifi_action_name:
                addShowHideListenerlayout( fm.findFragmentById(R.id.fragment));
                DatMessage datMessage = new DatMessage(getContext());
                datMessage.deleteAll();
                DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
                databaseHelper.deleteContact();
                mAuth.signOut();
                Intent intent = new Intent(getContext(), SignActivity.class);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();

                break;
        }

    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1212:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                              //  displaySize = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}
