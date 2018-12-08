package com.plugrobot.shocker;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;

public class Flash {
    private Camera cam;		// Объект камеры
    public boolean isEnabled;	// Включенный-выключенный фонарик
    public Activity activity;

    public Flash (Activity activity){
        this.activity = activity;

        isEnabled = false;
        cam = Camera.open();	// Берем заднюю камеру у устройства
    }

    public boolean hasFlash() {
        // Если мы не можем включить фонарик, то возвращаем false
        return (activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH));
    }

    public void FlashOn() {

        if(hasFlash()) {

            isEnabled = true;


            Parameters p = cam.getParameters();
            p.setFlashMode(Parameters.FLASH_MODE_TORCH);	// Свет

            cam.setParameters(p);
            cam.startPreview();
        }
    }

    public void FlashOff() {

        if(hasFlash()) {

            isEnabled = false;

            cam.stopPreview();
            cam.release();
        }
    }



}
