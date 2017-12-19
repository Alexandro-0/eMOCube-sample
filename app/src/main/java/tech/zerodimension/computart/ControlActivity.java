package tech.zerodimension.computart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tech.zerodimension.computart.ble.ControlActivityTemplate;
import tech.zerodimension.emocube.R;

/**
 * 繼承 ControlActivityTemplate，以獲取連接、傳送功能。
 * */
public class ControlActivity extends ControlActivityTemplate {

    public String deviceName, deviceAddress;
    public Button connectBtn;
    public TextView deviceNameTx, statusTx;

    /**
     * 進入activity 是否自動連接
     */
    @Override
    public boolean autoConnect() {
        return false;
    }

    /**
     * 設要連接的mac address
     */
    @Override
    public String getMacAddress() {
        return deviceAddress;
    }

    /**
     * 連線到指定device完成
     */
    @Override
    public void deviceConnected() {
        connectBtn.setText(getString(R.string.close_ble_connection));
        deviceNameTx.setText(deviceName);
        statusTx.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control2);

        deviceAddress = getIntent().getStringExtra("address");
        deviceName = getIntent().getStringExtra("name");

        if (deviceAddress == null || deviceName == null) {
            finish();
            return;
        }

        connectBtn = findViewById(R.id.connectBtn);
        deviceNameTx = findViewById(R.id.deviceNameTx);
        statusTx = findViewById(R.id.statusTx);

        getSupportActionBar().setTitle(deviceName);
    }

    public void onConnectBle(View view) {
        if (!mConnected) {
            connectBtn.setText(getString(R.string.connecting));
            startScanService();
        } else finish();
    }

    public void onClickPlay(View view) {
        if (!mConnected) {
            statusTx.setText(getString(R.string.not_connected));
            return;
        }
        sendDataToBLE("0"); // 傳送指令
        statusTx.setText(getString(R.string.status_start_button));
    }

    public void onClickStop(View view) {
        if (!mConnected) {
            statusTx.setText(getString(R.string.not_connected));
            return;
        }
        sendDataToBLE("1"); // 傳送指令
        statusTx.setText(getString(R.string.status_stop_button));
    }

    public void onClickSave(View view) {
        if (!mConnected) {
            statusTx.setText(getString(R.string.not_connected));
            return;
        }
        sendDataToBLE("2"); // 傳送指令
        statusTx.setText(getString(R.string.status_save_button));
    }

    public void onClickSlide(View view) {
        if (!mConnected) {
            statusTx.setText(getString(R.string.not_connected));
            return;
        }
        sendDataToBLE("3"); // 傳送指令
        statusTx.setText(getString(R.string.status_slide_button));
    }

    public void onClickSlowDown(View view) {
        if (!mConnected) {
            statusTx.setText(getString(R.string.not_connected));
            return;
        }
        sendDataToBLE("6"); // 傳送指令
        statusTx.setText(getString(R.string.status_lowDown_button));
    }

    public void onClickSpeedUp(View view) {
        if (!mConnected) {
            statusTx.setText(getString(R.string.not_connected));
            return;
        }
        sendDataToBLE("7"); // 傳送指令
        statusTx.setText(getString(R.string.status_speedUp_button));
    }

    public void onClickRestart(View view) {
        if (!mConnected) {
            statusTx.setText(getString(R.string.not_connected));
            return;
        }
        sendDataToBLE("40"); // 傳送指令
        statusTx.setText(getString(R.string.status_restart_button));
    }

    public void onClickClose(View view) {
        if (!mConnected) {
            statusTx.setText(getString(R.string.not_connected));
            return;
        }
        sendDataToBLE("5"); // 傳送指令
        statusTx.setText(getString(R.string.status_close_button));
    }
}
