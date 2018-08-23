package com.android.mlpj.southerninvestments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private UserLocalStore mUserLocalStore;
    private SQLLiteHelper mSqlLiteHelper;
    private boolean backPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUserLocalStore = new UserLocalStore(this);
        mSqlLiteHelper = new SQLLiteHelper(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
        setTitle("Dashboard");
        Fragment fragment = new DashboardFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment, "DASH_BOARD_FRAGMENT").commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            //checking whether user is in the dashboard
            DashboardFragment dashboardFragment = (DashboardFragment) fragmentManager.findFragmentByTag("DASH_BOARD_FRAGMENT");
            if (dashboardFragment != null && dashboardFragment.isVisible()) {
                if(backPressedOnce){
                    //close application
                    super.onBackPressed();
                    return;
                }else{
                    backPressedOnce = true;
                    Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
                }
            }else{
                //if not in dashboard
                Fragment fragment = new DashboardFragment();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment, "DASH_BOARD_FRAGMENT").commit();
            }
        }
    }

    public void setBackPressedOnce(){
        backPressedOnce = false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.nav_profile) {
            setTitle("Profile Details");
            Fragment fragment = new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();

            //fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
        } else if(id==R.id.nav_home){
            setTitle("Dashboard");
            Fragment fragment = new DashboardFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
        }

            else if (id == R.id.nav_my_loans) {
//            setTitle("Issue Loan");
//            Fragment fragment = new LoanIssueNicSearchFragment();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
//
        } else if (id == R.id.nav_logout) {
            mUserLocalStore.clearUser();
            mUserLocalStore.setUserLoggedIn(false);
            mSqlLiteHelper.removeAll();

            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
