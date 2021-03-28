package com.dlsd.property.guard.fragments;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import androidx.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlsd.property.R;
import com.dlsd.property.base.BaseFragment;
import com.dlsd.property.databinding.FragmentGuardHomeBinding;
import com.dlsd.property.guard.adapters.GuardPicAdapter;
import com.dlsd.property.utils.GlideLoadEngine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.app.Activity.RESULT_OK;


/**
 * 首页HomeFragment
 */
public class GuardHomeFragment extends BaseFragment implements View.OnClickListener {

    public static final String SD_APP_DIR_NAME = "GYDir"; //存储程序在外部SD卡上的根目录的名字
    public static final String PHOTO_DIR_NAME = "photo";    //存储照片在根目录下的文件夹名字
    private static final int REQUEST_PERMISSIONS = 1001;
    private final int REQUEST_CODE_CHOOSE_PHOTO_ALBUM = 1;

    private FragmentGuardHomeBinding mBinding;
    private GuardPicAdapter mGuardPicAdapter;
    private ArrayList<String> mPicUrls = new ArrayList<String>();
    private int choosePicPosition = -1;

    public GuardHomeFragment() {
        // Required empty public constructor
    }

    public static GuardHomeFragment newInstance() {
        GuardHomeFragment fragment = new GuardHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_guard_home, container, false);
        return mBinding.getRoot();
    }

    @Override
    protected void init() {
        mGuardPicAdapter = new GuardPicAdapter(getActivity(), mPicUrls);
        mGuardPicAdapter.setOnRecyclerViewItemClickListener(new GuardPicAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(GuardPicAdapter adapter, int position) {
                choosePicPosition = position;
                takePicture();
            }

            @Override
            public void onItemDelete(GuardPicAdapter adapter, int position) {
                mPicUrls.remove(position);
                mGuardPicAdapter.notifyDataSetChanged();
            }
        });
        mBinding.rcvPics.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mBinding.rcvPics.setAdapter(mGuardPicAdapter);
    }

    /**
     * 获取照片
     */
    private void takePicture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0才用动态权限
            getPermissions();
        } else {
            //在这里跳转到手机系统相册里面
            takePicFromSystem();
        }
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * cong 相册多选
     */
    private void takePicFromSystem() {
        Matisse.from(this)
                .choose(MimeType.ofImage(), false)
                .capture(true)  // 使用相机，和 captureStrategy 一起使用
                .captureStrategy(new CaptureStrategy(true, getActivity().getPackageName() + ".FileProvider"))
                .theme(R.style.Matisse_Dracula)
                .countable(true)
                .maxSelectable(3 - mPicUrls.size())
                .addFilter(new Filter() {
                    @Override
                    protected Set<MimeType> constraintTypes() {
                        return new HashSet<MimeType>() {{
                            add(MimeType.PNG);
                        }};
                    }

                    @Override
                    public IncapableCause filter(Context context, Item item) {
                        try {
                            InputStream inputStream = getActivity().getContentResolver().openInputStream(item.getContentUri());
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeStream(inputStream, null, options);
                            int width = options.outWidth;
                            int height = options.outHeight;

//                            if (width >= 500)
//                                return new IncapableCause("宽度超过500px");

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }


                        return null;
                    }
                })
//                .gridExpectedSize((int) getResources().getDimension(R.dimen.imageSelectDimen))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.87f)
                .imageEngine(new GlideLoadEngine())
                .forResult(REQUEST_CODE_CHOOSE_PHOTO_ALBUM);
    }

    List<String> mPermissionList = new ArrayList<>();

    /**
     * 获取动态权限(only  拍照需要的)
     */
    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0才用动态权限
            String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,};
            mPermissionList.clear();//清空没有通过的权限
            //逐个判断你要的权限是否已经通过
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(getActivity(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);//添加还未授予的权限
                }
            }
            //申请权限
            if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSIONS);
            } else {
                takePicFromSystem();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //成功
                    //Toast.makeText(this, "用户授权相机权限", Toast.LENGTH_SHORT).show();
                    takePicFromSystem();
                } else {
                    // 勾选了不再询问
                    showToast("您已拒绝相机权限，无法上传头像，请手动在设置中打开");
                    /**
                     * 跳转到 APP 详情的权限设置页
                     *
                     * 可根据自己的需求定制对话框，点击某个按钮在执行下面的代码
                     */
                    Intent intent = getAppDetailSettingIntent(getActivity());
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri result = null;
            if (requestCode == REQUEST_CODE_CHOOSE_PHOTO_ALBUM && resultCode == RESULT_OK) {
                //图片路径 同样视频地址也是这个 根据requestCode
                List<Uri> pathList = Matisse.obtainResult(data);
                List<String> _List = new ArrayList<>();
                for (Uri _Uri : pathList) {
                    String _Path = getRealFilePath(getActivity(), _Uri);
                    File _File = new File(_Path);
//                    System.out.println("lsh_压缩前图片大小->" + _File.length() / 1024 + "k");
                    _List.add(_Path);
                }
                compress(_List);
                //Toast.makeText(ComplainActivity.this, "" + pathList.size(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void compress(List<String> list) {
        String _Path = getImagesPath();
//        System.out.println("_Path->" + _Path);
        Luban.with(getActivity())
                .load(list)
                .ignoreBy(1024)
                .setTargetDir(_Path)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        //Toast.makeText(ComplainActivity.this, "I'm start", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        //Glide.with(ComplainActivity.this).load(file).into(mView);
//                        System.out.println("lsh_压缩后图片大小->" + file.length() / 1024 + "k");
//                        System.out.println("lsh_getAbsolutePath->" + file.getAbsolutePath());
                        mPicUrls.add(file.getAbsolutePath());
                        mGuardPicAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        e.printStackTrace();
                    }
                }).launch();
    }

    private String getImagesPath() {
        return createPathIfNotExist("/" + SD_APP_DIR_NAME + "/" + PHOTO_DIR_NAME);
    }

    private String createPathIfNotExist(String pPath) {
        boolean sdExist = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
        if (!sdExist) {
            //LogUtil.e("path", "SD卡不存在");
            return null;
        }
        String _AbsolutePath = Environment.getExternalStorageDirectory().getAbsolutePath() + pPath;
//        System.out.println("dbDir->" + _AbsolutePath);
        File dirFile = new File(_AbsolutePath);
        if (!dirFile.exists()) {
            if (!dirFile.mkdirs()) {
                //LogUtil.e("path", "文件夹创建失败");
                return null;
            }
        }
        return _AbsolutePath;
    }

    /**
     * 根据Uri获取文件真实地址
     */
    public static String getRealFilePath(Context context, Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String realPath = null;
        if (scheme == null)
            realPath = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            realPath = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA},
                    null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        realPath = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        if (TextUtils.isEmpty(realPath)) {
            if (uri != null) {
                String uriString = uri.toString();
                int index = uriString.lastIndexOf("/");
                String imageName = uriString.substring(index);
                File storageDir;

                storageDir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES);
                File file = new File(storageDir, imageName);
                if (file.exists()) {
                    realPath = file.getAbsolutePath();
                } else {
                    storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    File file1 = new File(storageDir, imageName);
                    realPath = file1.getAbsolutePath();
                }
            }
        }
        return realPath;
    }

    /**
     * 获取 APP 详情页面intent
     *
     * @return
     */
    public static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }
}
