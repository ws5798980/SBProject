package com.rs.mobile.common.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rs.mobile.common.qrcode.CameraManager;
import com.rs.mobile.common.qrcode.CameraPreview;

import java.io.IOException;
import java.lang.reflect.Field;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
@SuppressWarnings("deprecation")
public class CaptureActivity extends Activity {
    private Camera mCamera;
    private CameraPreview mPreview;
    Camera.Parameters mParameters;
    private Handler autoFocusHandler;
    private CameraManager mCameraManager;

    private FrameLayout scanPreview;
    private Button scanRestart;
    private RelativeLayout scanContainer;
    private RelativeLayout scanCropView;
    private ImageView scanLine;

    private Rect mCropRect = null;
    private boolean barcodeScanned = false;
    private boolean previewing = true;
    private boolean openFlag = false;
    private ImageScanner mImageScanner = null;

    static {
        System.loadLibrary("iconv");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissionForNormal();
        } else {
            setContentView(com.rs.mobile.common.R.layout.activity_capture);

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            findViewById();
            initViews();
        }
    }

    private void findViewById() {
        scanPreview = (FrameLayout) findViewById(com.rs.mobile.common.R.id.capture_preview);
        scanRestart = (Button) findViewById(com.rs.mobile.common.R.id.capture_restart_scan);
        scanContainer = (RelativeLayout) findViewById(com.rs.mobile.common.R.id.capture_container);
        scanCropView = (RelativeLayout) findViewById(com.rs.mobile.common.R.id.capture_crop_view);
        scanLine = (ImageView) findViewById(com.rs.mobile.common.R.id.capture_scan_line);
        findViewById(com.rs.mobile.common.R.id.openlight).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                openLight(openFlag);
            }
        });

        findViewById(com.rs.mobile.common.R.id.btn_cancel_scan).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

    }

    /**
     * 开光闪光灯
     *
     * @param isOpen
     */
    private void openLight(boolean isOpen) {
        if (!isOpen) {
            openFlag = true;
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(mParameters);
        } else {
            openFlag = false;
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(mParameters);
        }
    }

    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 123);
    }

    //正常获取权限
    private void checkPermissionForNormal() {
        //判断是否同意此权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        } else {
            setContentView(com.rs.mobile.common.R.layout.activity_capture);

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            findViewById();
            initViews();
        }
    }

    //权限的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 100: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            setContentView(com.rs.mobile.common.R.layout.activity_capture);

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            findViewById();
                            initViews();
                        } else {
                            Toast.makeText(this, "Don't have permissions.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                } else {
                    Toast.makeText(this, "Don't have permissions.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }

    private void initViews() {
        try {
            mImageScanner = new ImageScanner();
            mImageScanner.setConfig(0, Config.X_DENSITY, 3);
            mImageScanner.setConfig(0, Config.Y_DENSITY, 3);
            autoFocusHandler = new Handler();
            mCameraManager = new CameraManager(this);
            try {
                mCameraManager.openDriver();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCamera = mCameraManager.getCamera();
            mParameters = mCamera.getParameters();
            mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
            scanPreview.addView(mPreview);

        } catch (Exception e) {
//			Toast.makeText(this, "无调用相机!请打开相应权限!", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);


//			goToAppSetting();
        }

        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.85f);
        animation.setDuration(3000);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.REVERSE);
        scanLine.startAnimation(animation);
    }

    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    PreviewCallback previewCb = new PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Size size = camera.getParameters().getPreviewSize();

            // 这里需要将获取的data翻转一下，因为相机默认拿的的横屏的数据
            byte[] rotatedData = new byte[data.length];
            for (int y = 0; y < size.height; y++) {
                for (int x = 0; x < size.width; x++)
                    rotatedData[x * size.height + size.height - y - 1] = data[x
                            + y * size.width];
            }

            // 宽高也要调整
            int tmp = size.width;
            size.width = size.height;
            size.height = tmp;

            initCrop();

            Image barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(rotatedData);
            barcode.setCrop(mCropRect.left, mCropRect.top, mCropRect.width(),
                    mCropRect.height());

            int result = mImageScanner.scanImage(barcode);
            String resultStr = null;

            if (result != 0) {
                SymbolSet syms = mImageScanner.getResults();
                for (Symbol sym : syms) {
                    resultStr = sym.getData();
                }
            }


//			ZBarDecoder zBarDecoder = new ZBarDecoder();
//			String result = zBarDecoder.decodeCrop(rotatedData, size.width,
//					size.height, mCropRect.left, mCropRect.top,
//					mCropRect.width(), mCropRect.height());

            if (!TextUtils.isEmpty(resultStr)) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                barcodeScanned = true;

                Intent resultIntent = new Intent();

                Bundle bundle = new Bundle();

                bundle.putString("result", resultStr);

                resultIntent.putExtras(bundle);

                CaptureActivity.this.setResult(RESULT_OK, resultIntent);
                finish();
            }
        }
    };

    // Mimic continuous auto-focusing
    AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };

    /**
     * 初始化截取的矩形区域
     */
    private void initCrop() {
        int cameraWidth = mCameraManager.getCameraResolution().y;
        int cameraHeight = mCameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
