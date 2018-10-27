package com.android.mlpj.southerninvestments;

import android.app.Application;
import android.bluetooth.BluetoothSocket;

public class BaseApplication extends Application {

    private static BluetoothSocket mBluetoothSocket;

    public void setBlueToothSocket(BluetoothSocket bluetoothSocket){
        mBluetoothSocket = bluetoothSocket;
    }

    public BluetoothSocket getmBluetoothSocket() {
        return mBluetoothSocket;
    }
}
