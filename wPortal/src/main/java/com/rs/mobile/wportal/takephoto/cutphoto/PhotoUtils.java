package com.rs.mobile.wportal.takephoto.cutphoto;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.rs.mobile.common.L;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * [从本地选择图片以及拍照工具类，完美适配2.0-5.0版本]
 *
 * @author huxinwu
 * @version 1.0
 * @date 2015-1-7
 **/
public class PhotoUtils {

    private final String tag = PhotoUtils.class.getSimpleName();
    private static Uri imageUri=null;
    /**
     * 裁剪图片成功后返回
     **/
    public static final int INTENT_CROP = 2;
    /**
     * 拍照成功后返回
     **/
    public static final int INTENT_TAKE = 3;
    /**
     * 拍照成功后返回
     **/
    public static final int INTENT_SELECT = 4;

    public  String CROP_FILE_NAME = "crop_file.jpg";

    /**
     **/
    private OnPhotoResultListener onPhotoResultListener;


    public PhotoUtils(OnPhotoResultListener onPhotoResultListener) {
        this.onPhotoResultListener = onPhotoResultListener;
    }


    /**
     * 拍照
     *
     * @param
     * @return
     */
    public void takePicture(Activity activity) {
        try {
            File dir = new File(Environment.getExternalStorageDirectory() + "/wportal");
            CROP_FILE_NAME = "" + System.currentTimeMillis()+".jpg";
            if (!dir.exists())
                dir.mkdirs();

            File outputImage = new File(dir, "output_image.jpg");

            //创建File对象，用于存储拍照后的图片
          //  File outputImage = new File(Environment.getExternalStorageDirectory()+"/","output_image.jpg");
            if(outputImage.exists())
            {
                outputImage.delete();
                try
                {
                    outputImage.createNewFile();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

//           imageUri = Uri.fromFile(outputImage);
            imageUri =  FileProvider.getUriForFile(activity, "com.rs.mobile.wportal.fileprovider", outputImage);
            //每次选择图片吧之前的图片删除
            clearCropFile(buildUri(activity));

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            {
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //通过FileProvider创建一个content类型的Uri
                Uri picurl=buildUri(activity);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,picurl);
            } else
            {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, buildUri2(activity));
            }
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

            if (!isIntentAvailable(activity, intent)) {
                return;
            }
            activity.startActivityForResult(intent, INTENT_TAKE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 选择一张图片
     * 图片类型，这里是image/*，当然也可以设置限制
     * 如：image/jpeg等
     *
     * @param activity Activity
     */
    @SuppressLint("InlinedApi")
    public void selectPicture(Activity activity) {
        try {
            //每次选择图片吧之前的图片删除
            clearCropFile(buildUri(activity));
            CROP_FILE_NAME = "" + System.currentTimeMillis()+".jpg";
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

            if (!isIntentAvailable(activity, intent)) {
                return;
            }
            activity.startActivityForResult(intent, INTENT_SELECT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建uri
     *
     * @param activity
     * @return
     */
    private Uri buildUri(Activity activity) {
        if (CommonUtils.checkSDCard()) {
            File file = new File(Environment.getExternalStorageDirectory()+"/wportal");
            return FileProvider.getUriForFile(activity, "com.rs.mobile.wportal.fileprovider", file).buildUpon().appendPath(CROP_FILE_NAME).build();
        } else {
//            File file = new File(Environment.getExternalStorageDirectory()+"/wportal");
            return Uri.fromFile(activity.getCacheDir()).buildUpon().appendPath(CROP_FILE_NAME).build();
        }
    }
    private Uri buildUri2(Activity activity) {

        if (CommonUtils.checkSDCard()) {
            L.d("565656565");
            File file = new File(Environment.getExternalStorageDirectory()+"/wportal");
            L.d("7878787");
            Uri uri = Uri.fromFile(file).buildUpon().appendPath(CROP_FILE_NAME).build();
            L.d(uri.getPath());
            return uri;
        } else {
//            File file = new File(Environment.getExternalStorageDirectory()+"/wportal");
            return Uri.fromFile(activity.getCacheDir()).buildUpon().appendPath(CROP_FILE_NAME).build();
        }
    }
    /**
     * @param intent
     * @return
     */
    protected boolean isIntentAvailable(Activity activity, Intent intent) {
        PackageManager packageManager = activity.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private boolean corp(Activity activity, Uri uri) {
        L.d("11111111");
        Uri uri2 = uri;
        L.d(uri.getPath());
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        Uri cropuri ;
        File file = new File(uri2.getPath());
        L.d("222222222");
        if (!file.getParentFile().exists())
        {
            file.getParentFile().mkdirs();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            L.d("7777777");
            //TODO:访问相册需要被限制，需要通过FileProvider创建一个content类型的Uri
            uri2 = FileProvider.getUriForFile(activity, "com.rs.mobile.wportal.fileprovider", file);
            L.d("8888888");
            cropuri  = buildUri2(activity);
            L.d("999999999");
            //TODO:裁剪整个流程，估计授权一次就好outputUri不需要ContentUri,否则失败
            //outputUri = FileProvider.getUriForFile(activity, "com.solux.furniture.fileprovider", new File(crop_image));
        } else
        {
            imageUri = Uri.fromFile(file);
            cropuri  = buildUri2(activity);
//            outputUri = Uri.fromFile(new File(crop_image));
        }
        cropIntent.setDataAndType(uri2, "image/*");
        cropIntent.putExtra("crop", "false");
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", 500);
        cropIntent.putExtra("outputY", 500);
        cropIntent.putExtra("noFaceDetection", true);
        cropIntent.putExtra("return-data", false);
        cropIntent.putExtra("circleCrop",true);
        cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, cropuri);
        L.d("33333333333");
        if (!isIntentAvailable(activity, cropIntent)) {
            L.d("232323232");
            return false;
        } else {
            try {

                activity.startActivityForResult(cropIntent, INTENT_CROP);
                L.d("555555555");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * 返回结果处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (onPhotoResultListener == null) {
            Log.e(tag, "onPhotoResultListener is not null");
            return;
        }

        switch (requestCode) {
            //拍照
            case INTENT_TAKE:
                L.d("12121212121");
//                if (new File(buildUri2(activity).getPath()).exists()) {
                    L.d("44444444");
                    if (corp(activity, buildUri2(activity))) {
                        L.d("666666666");
                        return;
                    }
                    onPhotoResultListener.onPhotoCancel();
//                }
                break;

            //选择图片
            case INTENT_SELECT:
                if (data != null && data.getData() != null) {
                    Uri imageUri = data.getData();
                    if (corp(activity, imageUri)) {
                        return;
                    }
                }
                onPhotoResultListener.onPhotoCancel();
                break;

            //截图
            case INTENT_CROP:
                L.d("6666666999999"+"===="+resultCode);
                //&& new File(buildUri(activity).getPath()).exists()
                if (resultCode == Activity.RESULT_OK) {
                    L.d("6689945656123");
                    onPhotoResultListener.onPhotoResult(buildUri(activity));
                }
                break;
        }
    }

    /**
     * 删除文件
     *
     * @param uri
     * @return
     */
    public boolean clearCropFile(Uri uri) {
        if (uri == null) {
            return false;
        }
        Log.i("uri---------",uri.getPath());
        File file = new File(uri.getPath());
        if (file.exists()) {
            boolean result = file.delete();
            if (result) {
                Log.i(tag, "Cached crop file cleared.");
            } else {
                Log.e(tag, "Failed to clear cached crop file.");
            }
            return result;
        } else {
            Log.w(tag, "Trying to clear cached crop file but it does not exist.");
        }

        return false;
    }

    /**
     * [回调监听类]
     *
     * @author huxinwu
     * @version 1.0
     * @date 2015-1-7
     **/
    public interface OnPhotoResultListener {

        void onPhotoResult(Uri uri);

        void onPhotoCancel();
    }

    public OnPhotoResultListener getOnPhotoResultListener() {
        return onPhotoResultListener;
    }

    public void setOnPhotoResultListener(OnPhotoResultListener onPhotoResultListener) {
        this.onPhotoResultListener = onPhotoResultListener;
    }

}
