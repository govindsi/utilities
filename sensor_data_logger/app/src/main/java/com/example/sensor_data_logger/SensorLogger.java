package com.example.sensor_data_logger;


import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;

public class SensorLogger implements SensorEventListener {
    private MainActivity mContext;
    private SensorManager mSensorManager;
    private final Sensor mAccelerometer;
    private final Sensor mGyroscope;
    private final Sensor mMagnetometer;
    private final Sensor mAccelerometer_nc;
    private final Sensor mGyroscope_nc;
    private final Sensor mMagnetometer_nc;
    private static float[] Accel_data = new float[3];
    private static float[] Gyro_data = new float[3];
    private static float[] Mag_data = new float[3];
    private static float[] Accel_data_nc = new float[3];
    private static float[] Gyro_data_nc = new float[3];
    private static float[] Mag_data_nc = new float[3];
    private final static String LOG_TAG = SensorLogger.class.getName();
    public SensorLogger(MainActivity ctx) {

        mContext = ctx;
        mSensorManager = (SensorManager) mContext.getSystemService(ctx.SENSOR_SERVICE);

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscope =  mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mMagnetometer =  mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mAccelerometer_nc =  mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED);
        mGyroscope_nc =  mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        mMagnetometer_nc =  mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);


        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mAccelerometer_nc, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mGyroscope_nc, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagnetometer_nc, SensorManager.SENSOR_DELAY_GAME);

    }
    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {
        long timestamp = sensorEvent.timestamp;
        Sensor eachSensor = sensorEvent.sensor;
        switch (eachSensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                Accel_data[0] = sensorEvent.values[0];
                Accel_data[1] = sensorEvent.values[1];
                Accel_data[2] = sensorEvent.values[2];
                break;

            case Sensor.TYPE_ACCELEROMETER_UNCALIBRATED:
                Accel_data_nc[0] = sensorEvent.values[3];
                Accel_data_nc[1] = sensorEvent.values[4];
                Accel_data_nc[2] = sensorEvent.values[5];
                break;

            case Sensor.TYPE_GYROSCOPE:
                Gyro_data[0] = sensorEvent.values[0];
                Gyro_data[1] = sensorEvent.values[1];
                Gyro_data[2] = sensorEvent.values[2];
                break;

            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                Gyro_data_nc[0] = sensorEvent.values[3];
                Gyro_data_nc[1] = sensorEvent.values[4];
                Gyro_data_nc[2] = sensorEvent.values[5];
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                Mag_data[0] = sensorEvent.values[0];
                Mag_data[1] = sensorEvent.values[1];
                Mag_data[2] = sensorEvent.values[2];
                break;

            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                Mag_data_nc[0] = sensorEvent.values[3];
                Mag_data_nc[1] = sensorEvent.values[4];
                Mag_data_nc[2] = sensorEvent.values[5];
                break;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public static float[] getAccel_cal_data() {
        return Accel_data;
    }

    public static float[] getMag_cal_data() {
        return Gyro_data;
    }

    public static float[] getGyro_cal_data() {
        return Mag_data;
    }
}
