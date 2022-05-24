package com.example.allindianewsapp.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.allindianewsapp.Tabnavigation.AbpNewsFragment;
import com.example.allindianewsapp.Tabnavigation.IndiaNewsFragment;
import com.example.allindianewsapp.Tabnavigation.aajTakNewsFragment;

public class fragmentadapter extends FragmentPagerAdapter {
    public fragmentadapter(@NonNull FragmentManager fm) {
        super(fm);

    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new aajTakNewsFragment();
            case 1: return new AbpNewsFragment();
            case 2: return new IndiaNewsFragment();

            default:return new aajTakNewsFragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title=null;
        if (position==0){
            title ="Aaj Tak";
        } if (position==1){
            title ="Abp News";
        }if (position==2){
            title ="India News";
        }

        return title;
    }
}
