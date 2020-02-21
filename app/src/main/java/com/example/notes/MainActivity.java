package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.notes.Adapters.PageAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private PageAdapter mPageAdapter;
    private ViewPager viewPager;
    DatabaseHelper dbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbs = new DatabaseHelper(this);

        viewPager = (ViewPager)findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void setupViewPager(ViewPager viewpager) {
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Notes(), "Notes");
        adapter.addFragment(new Tasks(), "Tasks");
        viewpager.setAdapter(adapter);

    }

}
