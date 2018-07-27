package com.example.nifteam.AndroidUserDemoApp2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.nifteam.AndroidUserDemoApp2.Fragment.AnonymousFragment;
import com.example.nifteam.AndroidUserDemoApp2.Fragment.EmailPwdFragment;
import com.example.nifteam.AndroidUserDemoApp2.Fragment.IDPwdFragment;
import com.nifty.cloud.mb.core.NCMB;

public class MainActivity extends AppCompatActivity {

    //This is our viewPager
    private ViewPager viewPager;
    IDPwdFragment idPwdFragment;
    EmailPwdFragment emailPwdFragment;
    AnonymousFragment anonymousFragment;
    TextView title;

    MenuItem prevMenuItem;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    title.setText(getResources().getText(R.string.id_pw_title));
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    title.setText(getResources().getText(R.string.email_pw_title));
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    title.setText(getResources().getText(R.string.anonymous_title));
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NCMB.initialize(this.getApplicationContext(),"「YOUR_NCMB_APPLICATION_KEY」", "「YOUR_NCMB_CLIENT_KEY」");

        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        title=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        title.setText(getResources().getText(R.string.id_pw_title));

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        for (int i = 0; i < navigation.getChildCount(); i++) {
            View viewItem = navigation.getChildAt(i);
            viewItem.setY(-32);
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //Disable ViewPager Swipe
       viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        idPwdFragment = new IDPwdFragment();
        emailPwdFragment = new EmailPwdFragment();
        anonymousFragment = new AnonymousFragment();
        adapter.addFragment(idPwdFragment);
        adapter.addFragment(emailPwdFragment);
        adapter.addFragment(anonymousFragment);
        viewPager.setAdapter(adapter);
    }
}
