package captain.wonjong.smsmanager.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context mContext, Intent intent) {
        String action = intent.getAction();

        Bundle bundle = intent.getExtras();
        Object ObjectsPdus[] = (Object[]) bundle.get("pdus");

        SmsMessage smsMessage[] = new SmsMessage[ObjectsPdus.length];

        for (int i = 0; i < ObjectsPdus.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                smsMessage[i] = SmsMessage.createFromPdu((byte[]) ObjectsPdus[i], format);
            } else {
                smsMessage[i] = SmsMessage.createFromPdu((byte[]) ObjectsPdus[i]);
            }
        }

        String origNumber = smsMessage[0].getOriginatingAddress();
        String Message = smsMessage[0].getMessageBody().toString();

        Intent in = new Intent(Const.INTENT_KEY_SMS)
                .putExtra(Const.EXTRA_SMS_MESSAGE, origNumber + Const.SMS_SEPARATOR + Message);
        mContext.sendBroadcast(in);
    }
}