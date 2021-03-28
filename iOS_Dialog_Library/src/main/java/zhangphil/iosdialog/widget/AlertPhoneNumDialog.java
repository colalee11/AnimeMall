package zhangphil.iosdialog.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import zhangphil.iosdialog.R;

public class AlertPhoneNumDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_title;
    private TextView txt_msg;
    private Button btn_neg;
    private Button btn_pos;
    private ImageView img_line;
    private Display display;
    private MyScrollView scrollMsg;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;
    private boolean ablePosBtn = true;

    public AlertPhoneNumDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public AlertPhoneNumDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_phonenum_alertdialog, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);

        txt_msg.setVisibility(View.GONE);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = (ImageView) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);
        scrollMsg = (MyScrollView) view.findViewById(R.id.scroll_msg);
        scrollMsg.setOnScrollChangeListener(new MyScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollToStart() {

            }

            @Override
            public void onScrollToEnd() {
                setAblePosBtn(true);
            }
        });
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LayoutParams.WRAP_CONTENT));

        return this;
    }

    public AlertPhoneNumDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txt_title.setText("");
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    public AlertPhoneNumDialog setMsgGravuty(int gravityType) {
        txt_msg.setGravity(gravityType);
        return this;
    }

    public AlertPhoneNumDialog setMsgSize(int size) {
        txt_msg.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
        return this;
    }

    public AlertPhoneNumDialog setMsgPadding(int left, int top, int right, int bottom) {
        txt_msg.setPadding(left, top, right, bottom);
        return this;
    }

    public AlertPhoneNumDialog setMsg(Spanned msg) {
        showMsg = true;
        if ("".equals(msg)) {
            txt_msg.setText("内容");
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }

    public AlertPhoneNumDialog setMsg(String msg) {
        showMsg = true;
        if ("".equals(msg)) {
            txt_msg.setText("内容");
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }

    public AlertPhoneNumDialog setPositiveable(boolean bool) {
        ablePosBtn = bool;
        if (ablePosBtn) {
            btn_pos.setTextColor(context.getResources().getColor(R.color.actionsheet_green));
        } else {
            btn_pos.setTextColor(context.getResources().getColor(R.color.actionsheet_9));
        }
        return this;
    }

    public AlertPhoneNumDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public AlertPhoneNumDialog setBtnTextSize(int size) {
        btn_pos.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
        btn_neg.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
        return this;
    }

    public AlertPhoneNumDialog setPositiveButton(String text,
                                                 final OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ablePosBtn) {
                    listener.onClick(v);
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "请认真阅读完须知", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return this;
    }


    public AlertPhoneNumDialog setNegativeButton(String text,
                                                 final OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public AlertPhoneNumDialog setGravity(int gravity) {
        txt_msg.setGravity(gravity);
        return this;
    }

    private void setLayout() {
        if (!showTitle && !showMsg) {
            txt_title.setText("提示");
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            txt_msg.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText("确定");
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
            btn_pos.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }

    public boolean isAblePosBtn() {
        return ablePosBtn;
    }

    public void setAblePosBtn(boolean ablePosBtn) {
        this.ablePosBtn = ablePosBtn;
        if (ablePosBtn) {
            btn_pos.setTextColor(context.getResources().getColor(R.color.actionsheet_green));
        } else {
            btn_pos.setTextColor(context.getResources().getColor(R.color.actionsheet_9));
        }
    }
}
