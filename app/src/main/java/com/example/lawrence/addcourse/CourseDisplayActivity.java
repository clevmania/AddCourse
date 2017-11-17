package com.example.lawrence.addcourse;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

/**
 * Created by Lawrence on 5/3/2017.
 */
public class CourseDisplayActivity extends FragmentActivity implements ActionBar.TabListener {
    private ViewPager viewPager;
    private ActionBar actionBar;
    private Pager tabsAdapter;
    private String[] Yr = new String[]{"1st Yr","2nd Yr","3rd Yr", "4th Yr"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_display);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabsAdapter = new Pager(getSupportFragmentManager());
        viewPager.setAdapter(tabsAdapter);
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for(int i=0; i<4; i++){
            actionBar.addTab(actionBar.newTab().setText(Yr[i]).setTabListener(this));
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg) {
                // TODO Auto-generated method stub
                actionBar.setSelectedNavigationItem(arg);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // TODO something
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // TODO something
    }
}
