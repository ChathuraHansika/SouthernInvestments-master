package com.android.mlpj.southerninvestments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private UserLocalStore mUserLocalStore;
    private SQLLiteHelper mSqlLiteHelper;
    private TextView tvNavheaderName;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    //printer
    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    Button mScan, mPrint, mDisc;
    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUserLocalStore = new UserLocalStore(this);
        mSqlLiteHelper = new SQLLiteHelper(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        tvNavheaderName = headerView.findViewById(R.id.tv_nav_header_salesrep_name);
        tvNavheaderName.setText(mUserLocalStore.getUserDetails().getName());


        navigationView.setCheckedItem(R.id.nav_home);
        setTitle("Dashboard");
        Fragment fragment = new DashboardFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment, "DASH_BOARD_FRAGMENT").commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            //checking whether user is in the dashboard
            DashboardFragment dashboardFragment = (DashboardFragment) fragmentManager.findFragmentByTag("DASH_BOARD_FRAGMENT");
            if (dashboardFragment != null && dashboardFragment.isVisible()) {
                if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
                {
                    super.onBackPressed();
                    return;
                }
                else { Toast.makeText(getBaseContext(), "Tap back button twice in order to exit", Toast.LENGTH_SHORT).show(); }

                mBackPressed = System.currentTimeMillis();
            }else{
                //if not in dashboard
                Fragment fragment = new DashboardFragment();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment, "DASH_BOARD_FRAGMENT").commit();
            }
        }
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
        }else if(id==R.id.nav_connect_printer){
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                Toast.makeText(MainActivity.this, "Your device do not support bluetooth", Toast.LENGTH_SHORT).show();
            } else {
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(
                            BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent,
                            REQUEST_ENABLE_BT);
                } else {
                    Intent connectIntent = new Intent(MainActivity.this,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent,
                            REQUEST_CONNECT_DEVICE);
                }
            }
        }

            else if (id == R.id.nav_my_loans) {
//            setTitle("Issue Loan");
//            Fragment fragment = new LoanIssueNicSearchFragment();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
//
        }else if(id == R.id.nav_pocket_money) {
            Toast.makeText(this, "PocketMoney", Toast.LENGTH_SHORT).show();

//            pocket money

        }else if (id == R.id.nav_logout) {
            mUserLocalStore.clearUser();
            mUserLocalStore.setUserLoggedIn(false, "");
            mSqlLiteHelper.removeAll();

            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onActivityResult(int mRequestCode, int mResultCode,
                                 Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        switch (mRequestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (mResultCode == Activity.RESULT_OK) {
                    Bundle mExtra = mDataIntent.getExtras();
                    String mDeviceAddress = mExtra.getString("DeviceAddress");
                    mBluetoothDevice = mBluetoothAdapter
                            .getRemoteDevice(mDeviceAddress);
                    mBluetoothConnectProgressDialog = ProgressDialog.show(this,
                            "Connecting...", mBluetoothDevice.getName() + " : "
                                    + mBluetoothDevice.getAddress(), true, false);
                    connect();
                }
                break;

            case REQUEST_ENABLE_BT:
                if (mResultCode == Activity.RESULT_OK) {
                    Intent connectIntent = new Intent(MainActivity.this,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(MainActivity.this, "Failed enabling bluetooth", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void connect(){
        try {
            mBluetoothSocket = mBluetoothDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothSocket.connect();
            ((BaseApplication)getApplicationContext()).setBlueToothSocket(mBluetoothSocket);
            mBluetoothConnectProgressDialog.dismiss();
            Toast.makeText(this, "Printer Connected", Toast.LENGTH_SHORT).show();
        } catch (IOException eConnectException) {
            mBluetoothConnectProgressDialog.dismiss();
            Toast.makeText(this, "Failed to connect " + eConnectException.getMessage(), Toast.LENGTH_LONG).show();
            closeSocket(mBluetoothSocket);
            return;
        }catch (Exception e){
            Toast.makeText(this, "No Device", Toast.LENGTH_LONG).show();
        }
    }

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Toast.makeText(this, "socket closed", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Toast.makeText(this, "couldn't close socket", Toast.LENGTH_SHORT).show();
        }
    }

    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + UnicodeFormatter.byteToHex(b[k]));
        }

        return b[3];
    }

    public byte[] sel(int val) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putInt(val);
        buffer.flip();
        return buffer.array();
    }
}
