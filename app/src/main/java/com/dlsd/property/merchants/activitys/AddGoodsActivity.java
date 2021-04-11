package com.dlsd.property.merchants.activitys;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.dlsd.property.R;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityAddGoodsBinding;
import com.dlsd.property.db.Goods;
import com.dlsd.property.db.GoodsType;
import com.dlsd.property.merchants.adapters.GuardPicAdapter;
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

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.dlsd.property.utils.StatusBarUtil.setStatusBarDarkTheme;

public class AddGoodsActivity extends BaseActivity implements View.OnClickListener {

    private ActivityAddGoodsBinding binding;

    public static final String SD_APP_DIR_NAME = "GYDir"; //存储程序在外部SD卡上的根目录的名字
    public static final String PHOTO_DIR_NAME = "photo";    //存储照片在根目录下的文件夹名字
    private static final int REQUEST_PERMISSIONS = 1001;
    private final int REQUEST_CODE_CHOOSE_PHOTO_ALBUM = 1;

    private ArrayList<GoodsType> mGoodsTypesDatas = new ArrayList<GoodsType>();
    private ArrayList<String> mGoodsTypesStrDatas = new ArrayList<String>();
    private int typeIndex = -1;
    private GuardPicAdapter mGuardPicAdapter;
    private ArrayList<String> mPicUrls = new ArrayList<String>();
    private int choosePicPosition = -1;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_goods);
    }

    @Override
    protected void initView() {
        setStatusBarDarkTheme(this, false);
        getAllTypes();
        binding.tvGoodsType.setOnClickListener(this);
        mGuardPicAdapter = new GuardPicAdapter(this, mPicUrls);
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
        binding.rcvPics.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rcvPics.setAdapter(mGuardPicAdapter);

        binding.titlebar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.etGoodsName.getText().toString().trim())) {
                    showToast("请填写商品名称");
                    return;
                }
                if (TextUtils.isEmpty(binding.etGoodsIntroduction.getText().toString().trim())) {
                    showToast("请填写商品描述");
                    return;
                }
                if (TextUtils.isEmpty(binding.etGoodsPrice.getText().toString().trim()) || Float.parseFloat(binding.etGoodsPrice.getText().toString().trim()) <= 0) {
                    showToast("请填写商品单价");
                    return;
                }
                if (TextUtils.isEmpty(binding.etGoodsCount.getText().toString().trim()) || Integer.parseInt(binding.etGoodsCount.getText().toString().trim()) <= 0) {
                    showToast("请填写商品库存量");
                    return;
                }
                if (mPicUrls.size() == 0) {
                    showToast("请至少上传一张图片");
                    return;
                }
                if (typeIndex < 0) {
                    showToast("请选择商品分类");
                    return;
                }
                Goods g = new Goods();
                g.setGoodsName(binding.etGoodsName.getText().toString().trim());
                g.setGoodsIntroduction(binding.etGoodsIntroduction.getText().toString().trim());
                g.setGoodsPrice(Float.parseFloat(binding.etGoodsPrice.getText().toString().trim()));
                g.setGoodsCount(Integer.parseInt(binding.etGoodsCount.getText().toString().trim()));
                g.setGoodsType(mGoodsTypesDatas.get(typeIndex));
                g.setGoodsSeeCount(0);
                g.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (null == e) {
                            showToast("提交成功");
                            finish();
                        } else {
                            showToast(getString(R.string.error_msg));
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void initData() {

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
        switch (view.getId()) {
            case R.id.tv_goods_type:
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerBuilder(AddGoodsActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        typeIndex = options1;
                        String tx = mGoodsTypesStrDatas.get(options1);
                        binding.tvGoodsType.setText(tx);
                    }
                }).build();
                pvOptions.setPicker(mGoodsTypesStrDatas);
                pvOptions.show();
                break;
        }
    }

    /**
     * cong 相册多选
     */
    private void takePicFromSystem() {
        Matisse.from(this)
                .choose(MimeType.ofImage(), false)
                .capture(true)  // 使用相机，和 captureStrategy 一起使用
                .captureStrategy(new CaptureStrategy(true, getPackageName() + ".FileProvider"))
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
                            InputStream inputStream = getContentResolver().openInputStream(item.getContentUri());
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
                if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);//添加还未授予的权限
                }
            }
            //申请权限
            if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
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
                    Intent intent = getAppDetailSettingIntent(this);
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
                    String _Path = getRealFilePath(this, _Uri);
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
        Luban.with(this)
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


    private void upLoadFiles() {
        showLoading();
        //详细示例可查看BmobExample工程中BmobFileActivity类
        String filePath_mp3 = "/mnt/sdcard/testbmob/test1.png";
        String filePath_lrc = "/mnt/sdcard/testbmob/test2.png";
        final String[] filePaths = new String[2];
        filePaths[0] = filePath_mp3;
        filePaths[1] = filePath_lrc;
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {

            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                //2、urls-上传文件的完整url地址
                if (urls.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成
                    //do something
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                showToast("错误码" + statuscode + ",错误描述：" + errormsg);
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
            }
        });
    }

    private void getAllTypes() {
        showLoading();
        BmobQuery<GoodsType> query = new BmobQuery<GoodsType>();
        query.order("-createdAt")
                .findObjects(new FindListener<GoodsType>() {
                    @Override
                    public void done(List<GoodsType> object, BmobException e) {
                        if (e == null) {
                            mGoodsTypesDatas.clear();
                            mGoodsTypesStrDatas.clear();
                            mGoodsTypesDatas.addAll(object);
                            for (GoodsType type : mGoodsTypesDatas) {
                                mGoodsTypesStrDatas.add(type.getTypeName());
                            }
                        } else {
                            showToast(getString(R.string.error_msg));
                        }
                        hideLoading();
                    }
                });
    }
}
