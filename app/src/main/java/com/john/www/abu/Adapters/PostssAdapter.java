package com.john.www.abu.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.john.www.abu.Fragment.Post000.BOB;
import com.john.www.abu.Fragment.Post000.BOD;
import com.john.www.abu.Fragment.Post000.PostFragment;

/**
 * Created by MR AGUDA on 10-Mar-18.
 */

public class PostssAdapter extends FragmentStatePagerAdapter {


    public PostssAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                PostFragment post =new PostFragment();
                return post;

            case 1:
                BOD bod = new BOD();
                return bod;
            case 2:
                BOB bob = new BOB();
                return bob;

            default:
                return ChatooAdapters.PlaceholderFragment.newInstance(position + 1);
        }

    }

    @Override
    public int getCount() {
        return 3;
    }


    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Posts";
            case 1:
                return "BOD";
            case 2:
                return "BOB";
        }
        return null;
    }

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ChatooAdapters.PlaceholderFragment newInstance(int sectionNumber) {
            ChatooAdapters.PlaceholderFragment fragment = new ChatooAdapters.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

    }
}
