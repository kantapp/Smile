package com.kantapp.smile.Utils;

import android.app.Activity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Kantapp Inc. on 7/18/2018.
 */
public class Mobile
{
    public static final String TAG="Mobile.java";
    private static boolean isMailSend=false;
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static boolean sendEmail(final Activity activity, String to, String token) {


        String from = "postmaster@sandbox4046bdb8926d4b0a904d3df8d33dec9d.mailgun.org";
        String subject = "Reset Your Password";
        String url = "https://api.mailgun.net/v3/sandbox4046bdb8926d4b0a904d3df8d33dec9d.mailgun.org/messages";

        String mail = "<div>\n" +
                "        <h1 style=\"text-align: center; width: 100%\">Billion Smile</h1>\n" +
                "        <br>\n" +
                "        <h3>Forgot Your Password?</h3>\n" +
                "        \n" +
                "        <p>If you'd like to reset your password, please click the link below, or copy and paste it into your browser:</p>\n" +
                "        <a href=\"https://kantapp.com/Zeist/BillionSmile/public/newpass/" + token + "\" title=\"Reset Password\" class=\"dont-break-out\"> https://kantapp.com/Zeist/BillionSmile/public/newpass/" + token + "</a>\n" +
                "        <br>\n" +
                "        <br>\n" +
                "        <p>If token not exist then request password reset from Mobile App</p>\n" +
                "        <p>If you do not want to reset your password, please ignore this message and your password will not be changed. If you have any questions or concerns, please contact us at <b>Billion Smile Support</b></p>\n" +
                "        <br>\n" +
                "        The Pocket Team\n" +
                "    </div>";
        HashMap<String, String> param = new HashMap<>();
        param.put("from", "Billion Smile <" + from + ">");
        param.put("to", to);
        param.put("subject", subject);
        param.put("html", mail);
        AndroidNetworking.post(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Basic YXBpOmtleS05ZjU4MTkyOWI0NjhjYWQzZjEwMjZjZTBlY2FlZmFlMA==")
                .addBodyParameter(param)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonObject=new JSONObject(response);
                            String message=jsonObject.getString("message");
                            if (message.contains("Queued. Thank you."))
                            {
                                isMailSend=true;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: "+anError);
                    }
                });
        return isMailSend;
    }



}
