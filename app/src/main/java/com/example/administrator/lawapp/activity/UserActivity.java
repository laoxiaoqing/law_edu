package com.example.administrator.lawapp.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.ui.CircleImageView;
import com.example.administrator.lawapp.utils.CacheUtils;
import com.example.administrator.lawapp.utils.Constants;
import com.example.administrator.lawapp.utils.HttpUtils;
import com.example.administrator.lawapp.utils.ImageUtils;
import com.example.administrator.lawapp.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.xutils.http.RequestParams;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.io.File;
import java.io.IOException;




public class UserActivity extends Activity {
    private LinearLayout ll_head;
    private CircleImageView image;
    private TextView tvUserName;
    private TextView tvEmail;
    private static final int REQUEST_CHOOSE_IMAGE = 0x01;//相册
    private static final int TAKE_PHOTO = 0x11;//相机
    private static final int CROP_PHOTO = 0x99;//裁剪
    private static final int REQUEST_WRITE_EXTERNAL_PERMISSION_GRANT = 0xff;
    public String image_path;
    private File file;
    public Uri uri;
    private File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user);
        initView();


    }

    private void initView() {
        tvUserName=(TextView) findViewById(R.id.tv_user_name);
        String user_name = CacheUtils.getString(this,"user_name");
        String user_email = CacheUtils.getString(this,"user_email");
        tvUserName.setText(user_name);
        tvEmail=(TextView) findViewById(R.id.tv_email);
        tvEmail.setText(user_email);
        image = (CircleImageView) findViewById(R.id.image);
        ll_head = (LinearLayout) findViewById(R.id.ll_head);
        ll_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypeDialog();
            }
        });
    }

    private void showTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {//相册
            @Override
            public void onClick(View v) {
                prepareToOpenAlbum();//进入相册方法
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//相机
                getPicFromCamera();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    private void getPicFromCamera() {
        //用于保存调用相机拍照后所生成的文件
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), "output.png");
        try {
            if (tempFile.exists()){
                tempFile.delete();//删除
            }
            tempFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(UserActivity.this, "com.example.administrator.lawapp.activity", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, TAKE_PHOTO);
    }

    private void prepareToOpenAlbum() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_PERMISSION_GRANT);
        } else {
            openAlbum();
        }
    }

    //打开相册
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            //获取图片的uri
            uri = data.getData();
            //图片的绝对路径
            image_path = ImageUtils.getRealPathFromUri(this, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(image_path);
            image.setImageBitmap(bitmap);
            file = new File(image_path);
            //选取完图片后调用上传方法，将图片路径放入参数中
            sendInfoToServer(file);
        } else if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {//相机
                //用相机返回的照片去调用剪裁也需要对Uri进行处理
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri contentUri = FileProvider.getUriForFile(UserActivity.this, "com.example.administrator.lawapp.activity", tempFile);
                    cropPhoto(contentUri);
                } else {
                    cropPhoto(Uri.fromFile(tempFile));
                }
        } else if (requestCode == CROP_PHOTO && resultCode == RESULT_OK) {//剪裁
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                //在这里获得了剪裁后的Bitmap对象，可以用于上传
                Bitmap imageBit = bundle.getParcelable("data");
                //设置到ImageView上
                image.setImageBitmap(imageBit);
                //也可以进行一些保存、压缩等操作后上传
//                    String path = saveImage("crop", image);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void sendInfoToServer(File file) {
        //接口地址
        String urlAddress = Constants.IMAGECASESURL;
        uploadFile(file);
    }

    private void uploadFile(File file) {
        LogUtil.e(file.getPath());
        RequestParams params = new RequestParams(Constants.IMAGECASESURL);
        params.setMultipart(true);
        params.addBodyParameter("file", file);
        params.setConnectTimeout(3000);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求成功" + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("请求失败" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("请求onCancelled" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("请求结束");
            }
        });
    }


    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
// aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
// outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_PHOTO);
    }

}
