package com.moredian.entrance.porthelper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android_serialport_api.ChangeTool;
import android_serialport_api.SerialPortFinder;
import android_serialport_api.SerialPortUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SerialPort";

    @BindView(R.id.spinner_1)
    Spinner spinner1;
    @BindView(R.id.et_1)
    EditText et1;
    @BindView(R.id.et_2)
    EditText et2;
    @BindView(R.id.receive_area)
    TextView receiveArea;
    @BindView(R.id.bt_open)
    Button btOpen;
    @BindView(R.id.bt_close)
    Button btClose;
    @BindView(R.id.bt_send)
    Button btSend;
    private SerialPortUtils serialPortUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        serialPortUtils = new SerialPortUtils();
        SerialPortFinder finder = new SerialPortFinder();
        String[] attr = finder.getAllDevicesPath();
        spinner1.setAdapter(new SpinnerAdapter(this,attr));

        serialPortUtils.setOnDataReceiveListener(new SerialPortUtils.OnDataReceiveListener() {
            @Override
            public void onDataReceive(byte[] buffer, int size) {
                String receive = ChangeTool.ByteArrToHex(buffer, 0, size);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        receiveArea.setText(receive);
                    }
                });
            }
        });
    }

    @OnClick({R.id.bt_open, R.id.bt_close, R.id.bt_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_open:
                serialPortUtils.openSerialPort(((String)spinner1.getSelectedItem()).trim(),Integer.parseInt(et1.getText().toString()));
                break;
            case R.id.bt_close:
                serialPortUtils.closeSerialPort();
                break;
            case R.id.bt_send:
                serialPortUtils.sendSerialPort(et2.getText().toString());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serialPortUtils.closeSerialPort();
    }
}
