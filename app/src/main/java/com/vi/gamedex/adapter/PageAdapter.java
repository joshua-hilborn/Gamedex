package com.vi.gamedex.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.vi.gamedex.ui.DiscoverFragment;
import com.vi.gamedex.ui.SearchFragment;
import com.vi.gamedex.ui.WishlistFragment;

public class PageAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;

    public PageAdapter(FragmentManager fm, int numTabs){
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numberOfTabs = numTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new DiscoverFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new WishlistFragment();
            default:
                return null;
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //return super.getPageTitle(position);
        switch (position){
            case 0:
                return "Discover";
            case 1:
                return "Search";
            case 2:
                return "Wishlist";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
