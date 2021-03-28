package zhangphil.iosdialog.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import android.text.Spanned;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import zhangphil.iosdialog.R;

public class AlertQRCodeDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_msg;
    private ImageView img_barcode;
    private ImageView img_qrcode;
    private Display display;
    private boolean showMsg = false;
    private boolean showQRCode = false;
    private boolean showBarCode = false;

    public AlertQRCodeDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public AlertQRCodeDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_qrcode_alertdialog, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
        img_qrcode = (ImageView) view.findViewById(R.id.img_qrcode);
        img_barcode = (ImageView) view.findViewById(R.id.img_barcode);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LayoutParams.WRAP_CONTENT));

        return this;
    }

    public AlertQRCodeDialog setShowBarCodeImage(@Nullable Bitmap barBitmap) {
        showBarCode = true;
        if (null != barBitmap) {
            img_barcode.setImageBitmap(barBitmap);
        }
        return this;
    }

    public AlertQRCodeDialog setShowQRCodeImage(@Nullable Bitmap qrBitmap) {
        showQRCode = true;
        if (null != qrBitmap) {
            img_qrcode.setImageBitmap(qrBitmap);
        }
        return this;
    }


    public AlertQRCodeDialog setMsgGravuty(int gravityType) {
        txt_msg.setGravity(gravityType);
        return this;
    }

    public AlertQRCodeDialog setMsg(Spanned msg) {
        showMsg = true;
        if ("".equals(msg)) {
            txt_msg.setText("内容");
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }

    public AlertQRCodeDialog setMsg(String msg) {
        showMsg = true;
        if ("".equals(msg)) {
            txt_msg.setText("内容");
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }

    public AlertQRCodeDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public AlertQRCodeDialog setGravity(int gravity) {
        txt_msg.setGravity(gravity);
        return this;
    }

    private void setLayout() {
        if (showMsg) {
            txt_msg.setVisibility(View.VISIBLE);
        }
        if (showBarCode) {
            img_barcode.setVisibility(View.VISIBLE);
        }
        if (showQRCode) {
            img_qrcode.setVisibility(View.VISIBLE);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }


}
