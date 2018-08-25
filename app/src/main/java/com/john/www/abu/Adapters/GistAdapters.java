package com.john.www.abu.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.john.www.abu.Fragment.Chatoo.Groups;
import com.john.www.abu.Fragment.AbuGIstooo.Artist;
import com.john.www.abu.Fragment.AbuGIstooo.Gist;

/**
 * Created by MR AGUDA on 10-Mar-18.
 */

public class GistAdapters extends FragmentStatePagerAdapter{

    public GistAdapters(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                Gist gist = new Gist();
                return gist;

            case 1:
                Artist artist =new Artist();
                return artist;
            case 2:
                Groups apdates = new Groups();
                return apdates;

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
                return "Gists";
            case 1:
                return "Artists";
            case 2:
                return "Gists";
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
