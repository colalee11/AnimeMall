package com.dlsd.property.merchants.adapters;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dlsd.property.R;
import com.dlsd.property.databinding.ItemGuardPicBinding;
import com.dlsd.property.utils.GlideRoundTransform;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by LSH ON 2020/11/17
 * Describe:  图片适配器
 */
public class GuardPicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mDatas;
    private OnRecyclerViewItemClickListener listener;

    public GuardPicAdapter(Context context, ArrayList<String> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGuardPicBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_guard_pic, parent, false);
        return new GuardPicAdapter.GuardPicHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GuardPicHolder viewHolder = (GuardPicHolder) holder;
        if (mDatas.size() == position) {
            viewHolder.binding.ivPic.setImageResource(R.drawable.pic_add);
            viewHolder.binding.ivDelete.setVisibility(View.GONE);
        } else {
            String url = mDatas.get(position);
            int degreee = readBitmapDegree(url);
            Bitmap bitmap = createBitmap(url);
            if (bitmap != null) {
                if (degreee == 0) {
                    Glide.with(mContext)
                            .load(bitmap)
                            .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                            .transform(new GlideRoundTransform(mContext.getApplicationContext(), 5))
                            .into(viewHolder.binding.ivPic);
                } else {
                    Glide.with(mContext)
                            .load(rotateBitmap(degreee, bitmap))
                            .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                            .transform(new GlideRoundTransform(mContext.getApplicationContext(), 5))
                            .into(viewHolder.binding.ivPic);
                }
            }

            viewHolder.binding.ivDelete.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size() >= 3 ? 3 : mDatas.size() + 1;
    }


    private class GuardPicHolder extends RecyclerView.ViewHolder {

        ItemGuardPicBinding binding;

        private GuardPicHolder(ItemGuardPicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemDelete(GuardPicAdapter.this, getAdapterPosition());
                }
            });
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(GuardPicAdapter.this, getAdapterPosition());
                }
            });
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClicked(GuardPicAdapter adapter, int position);

        void onItemDelete(GuardPicAdapter adapter, int position);
    }

    private static int readBitmapDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    private Bitmap createBitmap(String path) {
        if (path == null) {
            return null;
        }

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 1;
        opts.inJustDecodeBounds = false;// 这里一定要将其设置回false，因为之前我们将其设置成了true
        //opts.inPurgeable = true;
        //opts.inInputShareable = true;
        opts.inDither = false;
        opts.inPurgeable = true;
        FileInputStream is = null;
        Bitmap bitmap = null;
        try {
            is = new FileInputStream(path);
            bitmap = BitmapFactory.decodeFileDescriptor(is.getFD(), null, opts);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bitmap;
    }

    public static Bitmap rotateBitmap(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (resizedBitmap != bitmap && bitmap != null && !bitmap.isRecycled()) {
            try {
                bitmap.recycle();
            } catch (Exception e) {
            }
        }
        return resizedBitmap;
    }
}
