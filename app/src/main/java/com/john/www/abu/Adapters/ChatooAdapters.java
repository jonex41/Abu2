package com.john.www.abu.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.john.www.abu.Fragment.Chatoo.Groups;
import com.john.www.abu.Fragment.Chatoo.Chat;
import com.john.www.abu.Fragment.Chatoo.Search;

/**
 * Created by MR AGUDA on 18-Jan-18.
 */

public class ChatooAdapters extends FragmentStatePagerAdapter {
    public ChatooAdapters(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Chat chatsFragment = new Chat();
                return chatsFragment;
            case 1:
                Groups locate = new Groups();
                return locate;
            case 2:
                Search search = new Search();
                return search;

            default:
                return PlaceholderFragment.newInstance(position + 1);
        }

    }

    @Override
    public int getCount() {
        return 3;
    }


    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Chats";
            case 1:
                return "Groups";
            case 2:
                return "Search";
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
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

    }
}
