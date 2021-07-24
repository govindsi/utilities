package com.example.sensor_data_logger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private SensorLogger mSensorLogger;
    private TextView accel_dataX_tv, accel_dataY_tv, accel_dataZ_tv;
    private TextView mag_dataX_tv, mag_dataY_tv, mag_dataZ_tv;
    private TextView gyro_dataX_tv, gyro_dataY_tv, gyro_dataZ_tv;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_resources();
        mSensorLogger = new SensorLogger(this);
        update_sensor_data();
    }

    private void init_resources() {

        accel_dataX_tv = (TextView) findViewById(R.id.accel_ax);
        accel_dataY_tv = (TextView) findViewById(R.id.accel_ay);
        accel_dataZ_tv = (TextView) findViewById(R.id.accel_az);

        mag_dataX_tv = (TextView) findViewById(R.id.mag_hx);
        mag_dataY_tv = (TextView) findViewById(R.id.mag_hy);
        mag_dataZ_tv = (TextView) findViewById(R.id.mag_hz);

        gyro_dataX_tv = (TextView) findViewById(R.id.gyro_pitch);
        gyro_dataY_tv = (TextView) findViewById(R.id.gyro_roll);
        gyro_dataZ_tv = (TextView) findViewById(R.id.gyro_yaw);
    }

    private void update_sensor_data() {

        final float[] acc_data = SensorLogger.getAccel_cal_data();
        final float[] gyro_data = SensorLogger.getMag_cal_data();
        final float[] mag_data = SensorLogger.getGyro_cal_data();


        // update current screen (activity)
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                accel_dataX_tv.setText(String.format(Locale.US, "%.3f", acc_data[0]));
                accel_dataY_tv.setText(String.format(Locale.US, "%.3f", acc_data[1]));
                accel_dataZ_tv.setText(String.format(Locale.US, "%.3f", acc_data[2]));


                mag_dataX_tv.setText(String.format(Locale.US, "%.3f", mag_data[0]));
                mag_dataY_tv.setText(String.format(Locale.US, "%.3f", mag_data[1]));
                mag_dataZ_tv.setText(String.format(Locale.US, "%.3f", mag_data[2]));


                gyro_dataX_tv.setText(String.format(Locale.US, "%.3f", gyro_data[0]));
                gyro_dataY_tv.setText(String.format(Locale.US, "%.3f", gyro_data[1]));
                gyro_dataZ_tv.setText(String.format(Locale.US, "%.3f", gyro_data[2]));

            }
        });

        // determine display update rate (100 ms)
        final long displayInterval = 100;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                update_sensor_data();
            }
        }, displayInterval);
    }
}