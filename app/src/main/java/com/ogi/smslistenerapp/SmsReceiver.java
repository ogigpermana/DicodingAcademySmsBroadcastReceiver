package com.ogi.smslistenerapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    final String TAG = SmsReceiver.class.getSimpleName();

    public SmsReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent){
        final Bundle bundle = intent.getExtras();
        try{
            if (bundle != null){
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                if (pdusObj != null){
                    for (Object aPdusObj : pdusObj){
                        SmsMessage currentMessage = getIncomingMessage(aPdusObj, bundle);
                        String sendNum = currentMessage.getDisplayOriginatingAddress();
                        String message = currentMessage.getDisplayMessageBody();
                        Log.d(TAG, "senderNum: " + sendNum + "; message: " + message);
                        Intent showSmsIntent = new Intent(context, SmsReceiverActivity.class);
                        showSmsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_NO, sendNum);
                        showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_MESSAGE, message);
                        context.startActivity(showSmsIntent);
                    }
                }else {
                    Log.d(TAG, "onReceive: SMS is null");
                }
            }
        }catch (Exception e){
            Log.d(TAG, "Exception smsReceiver" + e);
        }
    }

    private SmsMessage getIncomingMessage(Object object, Bundle bundle){
        SmsMessage currentSMS;
        if (Build.VERSION.SDK_INT >= 23){
            String format = bundle.getString("format");
            currentSMS = SmsMessage.createFromPdu((byte[]) object, format);
        }else {
            currentSMS = SmsMessage.createFromPdu((byte[]) object);
        }
        return currentSMS;
    }
}
