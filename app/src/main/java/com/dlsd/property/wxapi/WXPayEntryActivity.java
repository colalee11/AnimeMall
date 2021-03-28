package com.dlsd.property.wxapi;

import android.content.Intent;
import android.util.Log;

import androidx.databinding.DataBindingUtil;

import com.dlsd.property.R;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.constant.Constants;
import com.dlsd.property.databinding.PayBinding;
import com.dlsd.property.models.response.DiagnosisResp;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI api;
    private PayReq request;

    private PayBinding binding;

    private String from;

    private String mDiagnosisId;


    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.pay);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        from = getIntent().getStringExtra("from");
        mDiagnosisId = getIntent().getStringExtra("diagnosisId");
        DiagnosisResp.DataBean dataBean = (DiagnosisResp.DataBean) getIntent().getSerializableExtra("model");
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        api.handleIntent(getIntent(), this);
        PayReq request = new PayReq();
        request.appId = Constants.APP_ID;
        request.partnerId = dataBean.getMchId();
        request.prepayId = dataBean.getPrepayId();
        request.packageValue = "Sign=WXPay";
        request.nonceStr = dataBean.getNonceStr();
        request.timeStamp = dataBean.getTimestamp();
        request.sign = dataBean.getSign();
        api.sendReq(request);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.registerApp(Constants.APP_ID);
        api.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d("lsh_pay", "onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                showToast("支付成功");
                toResult();
            } else {
                showToast("支付失败");
            }
            finish();
        } else {
            finish();
        }
    }

    private void toResult() {

    }
}