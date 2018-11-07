package com.rs.mobile.wportal.takephoto.cutphoto;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;

import com.rs.mobile.wportal.R;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

/**
 * 动态权限
 */
public abstract class CheckPermission
{
    private Activity activity;

    public CheckPermission(Activity activity)
    {
        this.activity = activity;
    }

    //存储
    public static final int REQUEST_CODE_PERMISSION_STORAGE = 100;
    //相机
    public static final int REQUEST_CODE_PERMISSION_CAMERA = 101;
    //日历
    public static final int REQUEST_CODE_PERMISSION_CALENDAR = 102;
    //定位
    public static final int REQUEST_CODE_PERMISSION_LOCATION = 103;
    //短信
    public static final int REQUEST_CODE_PERMISSION_SMS = 104;
    //联系人
    public static final int REQUEST_CODE_PERMISSION_CONTACTS = 105;
    //打电话,手机状态
    public static final int REQUEST_CODE_PERMISSION_PHONE = 106;
    //麦克风
    public static final int REQUEST_CODE_PERMISSION_MICROPHONE = 107;
    //传感器
    public static final int REQUEST_CODE_PERMISSION_SENSORS = 108;
    //综合
    public static final int REQUEST_CODE_PERMISSION_OTHER = 109;

    /**
     * 检测权限
     */
    public void permission(int permissionType)
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            switch (permissionType)
            {
                case REQUEST_CODE_PERMISSION_STORAGE:
                    AndPermission.with(activity)
                            .requestCode(REQUEST_CODE_PERMISSION_STORAGE)
                            .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE)
                            .callback(permissionListener)
                            // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                            // 这样避免用户勾选不再提示，导致以后无法申请权限。
                            // 你也可以不设置。
                            .rationale(new RationaleListener()
                            {
                                @Override
                                public void showRequestPermissionRationale(int requestCode, Rationale rationale)
                                {
                                    // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                    AndPermission.rationaleDialog(activity, rationale)
                                            .show();
                                }
                            })
                            .start();
                    break;
                case REQUEST_CODE_PERMISSION_CAMERA:
                    AndPermission.with(activity)
                            .requestCode(REQUEST_CODE_PERMISSION_CAMERA)
                            .permission(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                            .callback(permissionListener)
                            // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                            // 这样避免用户勾选不再提示，导致以后无法申请权限。
                            // 你也可以不设置。
                            .rationale(new RationaleListener()
                            {
                                @Override
                                public void showRequestPermissionRationale(int requestCode, Rationale rationale)
                                {
                                    // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                    AndPermission.rationaleDialog(activity, rationale)
                                            .show();
                                }
                            })
                            .start();
                    break;
            }
        }
    }

    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener()
    {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions)
        {
            permissionSuccess();
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions)
        {
            String title = activity.getString(R.string.permission_request_error);
            String message = activity.getString(R.string.permission_image);
            switch (requestCode)
            {
                case REQUEST_CODE_PERMISSION_STORAGE:
                    message = activity.getString(R.string.permission_storage);
                    break;
                case REQUEST_CODE_PERMISSION_CAMERA:
                    message = activity.getString(R.string.permission_image);
                    break;
            }

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(activity, deniedPermissions))
            {
                // 第二种：用自定义的提示语。
                AndPermission.defaultSettingDialog(activity, requestCode)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("OK")
                        .setNegativeButton(activity.getString(R.string.cancel_value), new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                negativeButton();
                            }
                        })
                        .show();
            }
        }
    };

    /**
     * 权限申请成功
     */
    public abstract void permissionSuccess();

    /**
     * 取消按钮
     */
    public void negativeButton()
    {
        activity.finish();
    }
}
