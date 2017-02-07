package com.ronviet.ron.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.ronviet.ron.R;
import com.ronviet.ron.adapters.PagerAdapter;
import com.ronviet.ron.fragments.DrinkingFragment;
import com.ronviet.ron.fragments.FoodFragment;
import com.ronviet.ron.fragments.OthersFragment;
import com.ronviet.ron.models.PendingOrder;
import com.ronviet.ron.models.TableInfo;
import com.ronviet.ron.utils.Constants;
import com.ronviet.ron.utils.SharedPreferenceUtils;

public class ProductActivity extends BaseActivity {

    private PagerAdapter mPagerAdapter;
    private TableInfo mTableInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        mContext = this;
        mTableInfo = (TableInfo) getIntent().getSerializableExtra(Constants.EXTRA_TABLE);
        mTableSelection = mTableInfo;
        initLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PendingOrder pendingOrder = SharedPreferenceUtils.getPendingOrderFromList(mContext, mTableSelection.getId());
        if(pendingOrder != null) {
            setTotal(pendingOrder.tongTien);
        } else {
            setTotal(0);
        }
    }

    private void initLayout()
    {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setTabBar(tabLayout);

        initHeader();
        setTitle(getString(R.string.title_add_list) + " - " + mTableSelection.getName());
    }

    private void setupViewPager(ViewPager viewPager) {
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mPagerAdapter.addFrag(new FoodFragment().newInstance(mTableInfo), getString(R.string.food));
        mPagerAdapter.addFrag(new DrinkingFragment().newInstance(mTableInfo), getString(R.string.drink));
        mPagerAdapter.addFrag(new OthersFragment().newInstance(mTableInfo), getString(R.string.others));
        viewPager.setAdapter(mPagerAdapter);
    }

    private void setTabBar(TabLayout tabLayout)
    {
        TextView tabFood = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFood.setText(getString(R.string.food));
        tabFood.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_food, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabFood);

        TextView tabDrink = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabDrink.setText(getString(R.string.drink));
        tabDrink.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_drink, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabDrink);

        TextView tabOther = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOther.setText(getString(R.string.others));
        tabOther.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_other, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabOther);
    }
}
