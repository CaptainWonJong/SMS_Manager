package captain.wonjong.smsmanager;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;

import captain.wonjong.smsmanager.databinding.ActivityMainBinding;
import captain.wonjong.smsmanager.util.Const;
import captain.wonjong.smsmanager.util.SmsReceiver;

public class MainActivity extends AppCompatActivity {
    private final int    REQUEST_CODE = 1111;
    private final int    FLAGS = 0;
    private final String  TOKEN = "TOKEN";

    private ActivityMainBinding binding;
    private Context mContext;

    private SmsManager mSmsManager;
    private String     mSmsToken = "";
    private BroadcastReceiver mReadSmsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mContext = this;

        mSmsManager = SmsManager.getDefault();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mSmsToken = mSmsManager.createAppSpecificSmsToken(
                    PendingIntent.getBroadcast(
                            mContext,
                            REQUEST_CODE,
                            new Intent(this, SmsReceiver.class),
                            FLAGS
                    )
            );
        }

        initSmsReceiver();
        mContext.registerReceiver(mReadSmsReceiver, new IntentFilter(Const.INTENT_KEY_SMS));
    }

    private void initSmsReceiver() {
        // msg:         전화번호ˬ문자내용
        // content:     문자내용
        // phoneNumber: 전화번호

        mReadSmsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String msg = intent.getStringExtra(Const.EXTRA_SMS_MESSAGE).replace("\n", "");
                String msgPhoneNumber = msg.substring(0, msg.lastIndexOf(Const.SMS_SEPARATOR));
                String msgContent = msg.substring(msg.lastIndexOf(Const.SMS_SEPARATOR) + 1, msg.length());
            }
        };
    }
}
