package zhangphil.iosdialog.widget;

import android.app.Dialog;
import android.content.Context;
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

import zhangphil.iosdialog.R;

public class AlertInvoiceDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private ImageView iv_ele;
    private ImageView iv_paper;
    private ImageView iv_no;
    private LinearLayout lly_ele;
    private LinearLayout lly_paper;
    private LinearLayout lly_no;
    private Button btn_neg;
    private Button btn_pos;
    private ImageView img_line;
    private Display display;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int type = -1;

    public AlertInvoiceDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public AlertInvoiceDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_invoice_alertdialog, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);

        iv_ele = (ImageView) view.findViewById(R.id.iv_ele);
        iv_paper = (ImageView) view.findViewById(R.id.iv_paper);
        iv_no = (ImageView) view.findViewById(R.id.iv_no);

        lly_ele = (LinearLayout) view.findViewById(R.id.lly_ele);
        lly_paper = (LinearLayout) view.findViewById(R.id.lly_paper);
        lly_no = (LinearLayout) view.findViewById(R.id.lly_no);

        lly_paper.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 2;
                iv_ele.setImageResource(R.drawable.circular001);
                iv_paper.setImageResource(R.drawable.circular002);
                iv_no.setImageResource(R.drawable.circular001);
            }
        });

        lly_no.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 3;
                iv_ele.setImageResource(R.drawable.circular001);
                iv_paper.setImageResource(R.drawable.circular001);
                iv_no.setImageResource(R.drawable.circular002);
            }
        });

        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = (ImageView) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LayoutParams.WRAP_CONTENT));

        return this;
    }

    public AlertInvoiceDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public AlertInvoiceDialog setPositiveButton(String text, final OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public AlertInvoiceDialog setNegativeButton(String text,
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

    public AlertInvoiceDialog setEleInvoiceClick( final OnClickListener listener) {

        lly_ele.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_ele.setImageResource(R.drawable.circular002);
                iv_paper.setImageResource(R.drawable.circular001);
                iv_no.setImageResource(R.drawable.circular001);
                listener.onClick(v);
            }
        });
        return this;
    }

    public AlertInvoiceDialog setPaperInvoiceClick(final OnClickListener listener) {

        lly_paper.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_ele.setImageResource(R.drawable.circular001);
                iv_paper.setImageResource(R.drawable.circular002);
                iv_no.setImageResource(R.drawable.circular001);
                listener.onClick(v);
            }
        });
        return this;
    }

    public AlertInvoiceDialog setNoInvoiceClick( final OnClickListener listener) {

        lly_no.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_ele.setImageResource(R.drawable.circular001);
                iv_paper.setImageResource(R.drawable.circular001);
                iv_no.setImageResource(R.drawable.circular002);
                listener.onClick(v);
            }
        });
        return this;
    }

    private void setLayout() {

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


}
