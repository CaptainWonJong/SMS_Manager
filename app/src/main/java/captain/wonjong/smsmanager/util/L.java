package captain.wonjong.smsmanager.util;

import android.text.TextUtils;
import android.util.Log;

public class L {
    private static final String TAG = "CaptainWonJong";

    public static void d(String strLog) {

        if (TextUtils.isEmpty(strLog)) {
            strLog = "";
        }
        StackTraceElement ste = new Exception().getStackTrace()[1];
        Log.d(TAG, "[" +ste.getFileName().replace(".java", "") +" / "+ste.getLineNumber() +"]\n" + strLog);
    }

    public static void i(String strLog) {

        if (TextUtils.isEmpty(strLog)) {
            strLog = "";
        }
        StackTraceElement ste = new Exception().getStackTrace()[1];
        Log.i(TAG, "[" +ste.getFileName().replace(".java", "") +" / "+ste.getLineNumber() +"]\n" + strLog);
    }

    public static void w(String strLog) {

        if (TextUtils.isEmpty(strLog)) {
            strLog = "";
        }
        StackTraceElement ste = new Exception().getStackTrace()[1];
        Log.w(TAG, "[" +ste.getFileName().replace(".java", "") +" / "+ste.getLineNumber() +"]\n" + strLog);
    }

    public static void e(String strLog) {

        if (TextUtils.isEmpty(strLog)) {
            strLog = "";
        }
        StackTraceElement ste = new Exception().getStackTrace()[1];
        Log.e(TAG, "[" +ste.getFileName().replace(".java", "") +" / "+ste.getLineNumber() +"]\n" + strLog);
    }
}
