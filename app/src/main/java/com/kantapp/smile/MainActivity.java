package com.kantapp.smile;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.kantapp.smile.Fragment.BookmarkFragment;
import com.kantapp.smile.Fragment.HomeFragment;
import com.kantapp.smile.Fragment.ProfileFragment;
import com.kantapp.smile.Fragment.RecentFragment;
import com.rupins.drawercardbehaviour.CardDrawerLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{


    private CardDrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);



        drawer = (CardDrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer.setViewScale(Gravity.START, 0.9f);
        drawer.setRadius(Gravity.START, 35);
        drawer.setViewElevation(Gravity.START, 20);

        getSupportFragmentManager().beginTransaction().replace(R.id.layoutFragment,new HomeFragment()).commit();
        }



    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_camera) {
            fragment= new HomeFragment();

        } else if (id == R.id.nav_gallery) {
            fragment=new RecentFragment();

        } else if (id == R.id.nav_slideshow) {
            fragment=new BookmarkFragment();

        } else if (id == R.id.nav_manage) {
            fragment=new ProfileFragment();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        if (fragment!=null)
        {
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.layoutFragment, fragment);
            ft.commit();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_right_drawer:
//                drawer.openDrawer(Gravity.END);
//                return true;
//
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
