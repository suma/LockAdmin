package com.suma.lockAdmin;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends Activity {
   static final int RESULT_ENABLE = 1;

    DevicePolicyManager mDevicePolicyManager;
    ComponentName mDeviceAdmin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // DevicePolicyManagerを取得する。
        mDevicePolicyManager = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        // ComponentNameを取得する。
        mDeviceAdmin = new ComponentName(this, AdminReceiver.class);
        // デバイス管理を有効にする。
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdmin);
        startActivityForResult(intent, RESULT_ENABLE);

        setContentView(R.layout.test);

        Button lockButton = (Button) findViewById(R.id.lock_button);
        lockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	// 画面ロック
            	mDevicePolicyManager.lockNow();
				mDevicePolicyManager.setMaximumTimeToLock(mDeviceAdmin, 1000 * 60);
            }
        });
    }

    /** ApiDemosそのまま */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_ENABLE:
                if (resultCode == Activity.RESULT_OK) {
                    Log.i("DeviceAdminSample", "Administration enabled!");
                } else {
                    Log.i("DeviceAdminSample", "Administration enable FAILED!");
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
