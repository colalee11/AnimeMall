package com.dlsd.property.guard.activitys;

import android.Manifest;
import android.app.Activity;
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
import com.dlsd.property.databinding.ActivityGoodsEditBinding;
import com.dlsd.property.db.Goods;
import com.dlsd.property.db.GoodsType;
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

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.dlsd.property.utils.StatusBarUtil.setStatusBarDarkTheme;

public class GoodsEditActivity extends BaseActivity implements View.OnClickListener {

    private ActivityGoodsEditBinding binding;
    private Goods mGoods;

    public static final String SD_APP_DIR_NAME = "GYDir"; //?????????????????????SD???????????????????????????
    public static final String PHOTO_DIR_NAME = "photo";    //?????????????????????????????????????????????
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_goods_edit);
    }

    @Override
    protected void initView() {
        setStatusBarDarkTheme(this, false);
        mGoods = (Goods) getIntent().getSerializableExtra("goods");
        getAllTypes();
        binding.etGoodsName.setText(mGoods.getGoodsName());
        binding.etGoodsIntroduction.setText(mGoods.getGoodsIntroduction());
        binding.etGoodsPrice.setText("" + mGoods.getGoodsPrice());
        binding.etGoodsCount.setText("" + mGoods.getGoodsCount());

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
                    showToast("?????????????????????");
                    return;
                }
                if (TextUtils.isEmpty(binding.etGoodsIntroduction.getText().toString().trim())) {
                    showToast("?????????????????????");
                    return;
                }
                if (TextUtils.isEmpty(binding.etGoodsPrice.getText().toString().trim()) || Float.parseFloat(binding.etGoodsPrice.getText().toString().trim()) <= 0) {
                    showToast("?????????????????????");
                    return;
                }
                if (TextUtils.isEmpty(binding.etGoodsCount.getText().toString().trim()) || Integer.parseInt(binding.etGoodsCount.getText().toString().trim()) <= 0) {
                    showToast("????????????????????????");
                    return;
                }
                if (mPicUrls.size() == 0) {
                    showToast("???????????????????????????");
                    return;
                }
                if (typeIndex < 0) {
                    showToast("?????????????????????");
                    return;
                }
                mGoods.setGoodsName(binding.etGoodsName.getText().toString().trim());
                mGoods.setGoodsIntroduction(binding.etGoodsIntroduction.getText().toString().trim());
                mGoods.setGoodsPrice(Float.parseFloat(binding.etGoodsPrice.getText().toString().trim()));
                mGoods.setGoodsCount(Integer.parseInt(binding.etGoodsCount.getText().toString().trim()));
                mGoods.setGoodsType(mGoodsTypesDatas.get(typeIndex));
                mGoods.setGoodsSeeCount(0);
                mGoods.update(mGoods.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (null == e) {
                            showToast("????????????");
                            Intent intent = new Intent();
                            intent.putExtra("goods", mGoods);
                            setResult(Activity.RESULT_OK, intent);
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
     * ????????????
     */
    private void takePicture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0??????????????????
            getPermissions();
        } else {
            //??????????????????????????????????????????
            takePicFromSystem();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_goods_type:
                //???????????????
                OptionsPickerView pvOptions = new OptionsPickerBuilder(GoodsEditActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //?????????????????????????????????????????????
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
     * cong ????????????
     */
    private void takePicFromSystem() {
        Matisse.from(this)
                .choose(MimeType.ofImage(), false)
                .capture(true)  // ?????????????????? captureStrategy ????????????
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
//                                return new IncapableCause("????????????500px");

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
     * ??????????????????(only  ???????????????)
     */
    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0??????????????????
            String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,};
            mPermissionList.clear();//???????????????????????????
            //?????????????????????????????????????????????
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);//???????????????????????????
                }
            }
            //????????????
            if (mPermissionList.size() > 0) {//????????????????????????????????????
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
                    //??????
                    //Toast.makeText(this, "????????????????????????", Toast.LENGTH_SHORT).show();
                    takePicFromSystem();
                } else {
                    // ?????????????????????
                    showToast("???????????????????????????????????????????????????????????????????????????");
                    /**
                     * ????????? APP ????????????????????????
                     *
                     * ????????????????????????????????????????????????????????????????????????????????????
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
                //???????????? ?????????????????????????????? ??????requestCode
                List<Uri> pathList = Matisse.obtainResult(data);
                List<String> _List = new ArrayList<>();
                for (Uri _Uri : pathList) {
                    String _Path = getRealFilePath(this, _Uri);
                    File _File = new File(_Path);
//                    System.out.println("lsh_?????????????????????->" + _File.length() / 1024 + "k");
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
                        // TODO ???????????????????????????????????????????????? loading UI
                        //Toast.makeText(ComplainActivity.this, "I'm start", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO ??????????????????????????????????????????????????????
                        //Glide.with(ComplainActivity.this).load(file).into(mView);
//                        System.out.println("lsh_?????????????????????->" + file.length() / 1024 + "k");
//                        System.out.println("lsh_getAbsolutePath->" + file.getAbsolutePath());
                        mPicUrls.add(file.getAbsolutePath());
                        mGuardPicAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO ????????????????????????????????????
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
            //LogUtil.e("path", "SD????????????");
            return null;
        }
        String _AbsolutePath = Environment.getExternalStorageDirectory().getAbsolutePath() + pPath;
//        System.out.println("dbDir->" + _AbsolutePath);
        File dirFile = new File(_AbsolutePath);
        if (!dirFile.exists()) {
            if (!dirFile.mkdirs()) {
                //LogUtil.e("path", "?????????????????????");
                return null;
            }
        }
        return _AbsolutePath;
    }

    /**
     * ??????Uri????????????????????????
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
     * ?????? APP ????????????intent
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
        //?????????????????????BmobExample?????????BmobFileActivity???
        String filePath_mp3 = "/mnt/sdcard/testbmob/test1.png";
        String filePath_lrc = "/mnt/sdcard/testbmob/test2.png";
        final String[] filePaths = new String[2];
        filePaths[0] = filePath_mp3;
        filePaths[1] = filePath_lrc;
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {

            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                //1???files-??????????????????BmobFile???????????????????????????????????????????????????????????????????????????????????????????????????????????????
                //2???urls-?????????????????????url??????
                if (urls.size() == filePaths.length) {//??????????????????????????????????????????????????????
                    //do something
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                showToast("?????????" + statuscode + ",???????????????" + errormsg);
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                //1???curIndex--???????????????????????????????????????
                //2???curPercent--???????????????????????????????????????????????????
                //3???total--???????????????????????????
                //4???totalPercent--???????????????????????????????????????
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
                            int i = 0;
                            for (GoodsType type : mGoodsTypesDatas) {
                                mGoodsTypesStrDatas.add(type.getTypeName());
                                if (mGoods.getGoodsType().getTypeName().equals(type.getTypeName())) {
                                    binding.tvGoodsType.setText(type.getTypeName());
                                    typeIndex = i;
                                }
                                i++;
                            }
                        } else {
                            showToast(getString(R.string.error_msg));
                        }
                        hideLoading();
                    }
                });
    }
}
