package com.azhar.m_advisor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.azhar.m_advisor.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPager viewPager;
    FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//
//        tabLayout = findViewById(R.id.tabLayoutId);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPagerId);

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addFragment(new AllPlaylistFragment(), "All");
        viewPagerAdapter.addFragment(new ScheduledPlaylistFragment(), "Scheduled");
        viewPager.setAdapter(viewPagerAdapter);
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//
//        adapter = new FragmentAdapter(fragmentManager, getLifecycle());
//        viewPager.setAdapter(adapter);
//
//        tabLayout.addTab(tabLayout.newTab().setText("All"));
//        tabLayout.addTab(tabLayout.newTab().setText("SCHEDULED"));
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//
//                tabLayout.selectTab(tabLayout.getTabAt(position));
//            }
//        });
    }

//    public void goToScplaylist(View view) {
//        //============= Fragment ===============
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fragment_container, new ScheduledPlaylistFragment())
//                .commit();
//    }
//
//    public void goToAllplaylist(View view) {
//        //============= Fragment ===============
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fragment_container, new AllPlaylistFragment())
//                .commit();
//    }
}