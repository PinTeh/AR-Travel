package cn.imhtb.ar.travel.mapcam;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import cn.imhtb.ar.travel.R;
import cn.imhtb.ar.travel.fregment.FinFragment;
import cn.imhtb.ar.travel.fregment.FooFragment;
import cn.imhtb.ar.travel.fregment.NotScrollViewPager;

;

public class HomeActivity extends AppCompatActivity {


    TypedArray unSelected;

    TypedArray selected;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] titles = {"首页", "更多"};
    private NotScrollViewPager viewPager;
    private ArrayList<CustomTabEntity> customTabEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        selected = getResources().obtainTypedArray(R.array.select);
        unSelected = getResources().obtainTypedArray(R.array.unSelect);

        for (int i = 0; i < titles.length; i++) {
            TabEntity tabEntity = new TabEntity(
                    titles[i],
                    selected.getResourceId(i, 0),
                    unSelected.getResourceId(i, 0)
            );
            customTabEntities.add(tabEntity);
        }

        mFragments.add(new FinFragment());
        mFragments.add(new FooFragment());

        CommonTabLayout tabLayout = (CommonTabLayout) findViewById(R.id.tab_layout);
        viewPager = (NotScrollViewPager) findViewById(R.id.viewPager);
        assert viewPager != null;
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        assert tabLayout != null;
        tabLayout.setTabData(customTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    public class TabEntity implements CustomTabEntity {

        private int id;
        private String title;
        private int selectedIcon;
        private int unSelectedIcon;

        public TabEntity(String title) {
            this.title = title;
        }

        public TabEntity(String title,int id) {
            this.title = title;
            this.id = id;
        }

        public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
            this.title = title;
            this.selectedIcon = selectedIcon;
            this.unSelectedIcon = unSelectedIcon;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return selectedIcon;
        }

        @Override
        public int getTabUnselectedIcon() {
            return unSelectedIcon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
