package com.example.xpn_switch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private ConnectView mConnectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConnectView = findViewById(R.id.btn_VPN);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_discount:
                mConnectView.changeConnectStatus(ConnectView.State.Disconnect);
                break;
            case R.id.btn_connect:
                mConnectView.changeConnectStatus(ConnectView.State.Connecting);
                break;
            case R.id.btn_reconnect:
                mConnectView.changeConnectStatus(ConnectView.State.ReConnecting);
                break;
            case R.id.connect_success:
                mConnectView.changeConnectStatus(ConnectView.State.ToConnectSuccess);
                break;
            case R.id.connect_fail:
                mConnectView.changeConnectStatus(ConnectView.State.Disconnect);
                break;
        }
    }
}
