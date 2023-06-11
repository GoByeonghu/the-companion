package com.gobyeonghu.the_companion;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPager2Adapter extends FragmentStateAdapter {

    private String data0, data1, data2;
    public void setData(String data0, String data1, String data2) {
        this.data0 = data0;
        this.data1 = data1;
        this.data2 = data2;
    }

    public ViewPager2Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                FirstFragment firstFragment = new FirstFragment();
                firstFragment.setData(data0);
                return firstFragment;
            case 1:
                SecondFragment secondFragment = new SecondFragment();
                secondFragment.setData(data1);
                return secondFragment;
            case 2:
                ThirdFragment thirdFragment = new ThirdFragment();
                thirdFragment.setData(data2);
                return thirdFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;       // 페이지 수
    }
}
