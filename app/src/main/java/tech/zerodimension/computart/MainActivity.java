package tech.zerodimension.computart;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import tech.zerodimension.computart.ble.DeviceRowView;
import tech.zerodimension.computart.ble.ScanActivityTemplate;
import tech.zerodimension.computart.preference.PrefRW;
import tech.zerodimension.emocube.R;

/**
 * 繼承 ScanActivityTemplate，以獲取掃描藍芽的功能。
 * */
public class MainActivity extends ScanActivityTemplate {

    private LinearLayout scannedDeviceLayout    //已連接過的藍芽設備列表
            , newDeviceLayout;   //尚未連接過的藍芽設備列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scannedDeviceLayout = findViewById(R.id.scannedDeviceLayout);
        newDeviceLayout = findViewById(R.id.newDeviceLayout);

        getSupportActionBar().setTitle(R.string.main_title);
    }

    private TextView createNoneDeviceView() {
        TextView info = new TextView(this);
        info.setText(getString(R.string.ble_device_not_fount));
        info.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        int l = getResources().getDimensionPixelOffset(R.dimen.size_l);
        info.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.sp_s));
        info.setPadding(l, l, l, l);
        info.setGravity(Gravity.CENTER);
        return info;
    }

    /**
     * 掃描到藍芽裝置
     */
    @Override
    public void setBluetoothScannedListView() {
        scannedDeviceLayout.removeAllViews();   // 清空已連接過的藍芽設備列表
        newDeviceLayout.removeAllViews();       // 清空尚連接過的藍芽設備列表
        for (BluetoothDevice device : mBluetoothDevices) {
            // 已讀取過
            if (!PrefRW.read(this, device.getAddress()).equals("")) {
                scannedDeviceLayout.addView(new DeviceRowView(this, device.getName(), device.getAddress(), new DeviceRowView.iDeviceRowView() {
                    @Override
                    public void rowClicked(String name, String address) {
                        Intent in = new Intent(MainActivity.this, ControlActivity.class);
                        in.putExtra("name", name);
                        in.putExtra("address", address);

                        // save id to pref
                        PrefRW.write(MainActivity.this, address, "exist");

                        startActivity(in);
                    }
                }));
            } else {
                // 還沒讀取過
                newDeviceLayout.addView(new DeviceRowView(this, device.getName(), device.getAddress(), new DeviceRowView.iDeviceRowView() {
                    @Override
                    public void rowClicked(String name, String address) {
                        Intent in = new Intent(MainActivity.this, ControlActivity.class);
                        in.putExtra("name", name);
                        in.putExtra("address", address);

                        // save id to pref
                        PrefRW.write(MainActivity.this, address, "exist");

                        startActivity(in);
                    }
                }));
            }
        }
        if (scannedDeviceLayout.getChildCount() == 0)
            scannedDeviceLayout.addView(createNoneDeviceView());
        if (newDeviceLayout.getChildCount() == 0)
            newDeviceLayout.addView(createNoneDeviceView());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_alarm:
                setBluetoothScannedListView();
                mBluetoothDevices.clear();
                scanBluetoothList(true);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
