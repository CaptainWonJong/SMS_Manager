package captain.wonjong.smsmanager;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import captain.wonjong.smsmanager.util.Const;
import captain.wonjong.smsmanager.util.L;
import captain.wonjong.smsmanager.util.SmsReceiver;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final int     REQUEST_CODE = 1111;
    private final int     FLAGS = 0;

    private Context mContext;

    private SmsManager mSmsManager;
    private String     mSmsToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        mSmsManager = SmsManager.getDefault();
        mContext.registerReceiver(mReadSmsReceiver, new IntentFilter(Const.INTENT_KEY_SMS));
    }


    // msg:         전화번호ˬ문자내용
    // content:     문자내용
    // phoneNumber: 전화번호
    private BroadcastReceiver mReadSmsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive (Context context, Intent intent){
            L.w("onReceive");
            String msg = intent.getStringExtra(Const.EXTRA_SMS_MESSAGE).replace("\n", "");
            String msgPhoneNumber = msg.substring(0, msg.lastIndexOf(Const.SMS_SEPARATOR));
            String msgContent = msg.substring(msg.lastIndexOf(Const.SMS_SEPARATOR) + 1, msg.length());

            ((EditText) findViewById(R.id.et_input_otp)).setText(msgContent);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContext.unregisterReceiver(mReadSmsReceiver);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_send_otp:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mSmsToken = mSmsManager.createAppSpecificSmsToken(
                            PendingIntent.getBroadcast(
                                    mContext,
                                    REQUEST_CODE,
                                    new Intent(this, SmsReceiver.class),
                                    FLAGS
                            )
                    );
                    ((TextView)findViewById(R.id.tv_token)).setText(mSmsToken);
                    L.w(mSmsToken);

                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // 기존 프로세스와 같이 권한 요청 후, 서버에서 SMS 요청
                    // requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 0);
                }
                break;

            case R.id.btn_check_otp:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String getSmsText = ((EditText) findViewById(R.id.et_input_otp)).getText().toString();
                    if (getSmsText.contains(mSmsToken)) {
                        Toast.makeText(mContext, "인증이 완료되었습니다 :)\n토큰번호 : " + mSmsToken, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mContext, "인증에 실패하였습니다 :(\n토큰번호 : " + mSmsToken, Toast.LENGTH_LONG).show();
                    }
                } else {
                    // 기존 프로세스와 같이 인증번호 확인 후 진행
                }
                break;
        }
    }
}
