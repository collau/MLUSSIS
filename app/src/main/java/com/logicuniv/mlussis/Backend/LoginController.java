package com.logicuniv.mlussis.Backend;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.logicuniv.mlussis.LoginActivity;
import com.logicuniv.mlussis.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.logicuniv.mlussis.Backend.JSONParser.readStream;

/**
 * Created by xavie on 24-01-2018.
 */

public class LoginController {

    private static ArrayList<String> currentRoles = new ArrayList<String>();
    public static final int LOGOUT_CODE = Integer.MIN_VALUE;

    public static boolean IsLoggedInUserInRole(Context context, String role)
    {
        if(getSessionID(context).toString().length()>3) {
            return currentRoles.contains(role);
        }else {
            return  false;
        }
    }

    public static boolean AuthenticateCredentials(Context context, String username, String password) {
        boolean result = false;
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonResponse;

        try {
            jsonObject.put("user", username);
            jsonObject.put("pass", password);

            Log.d("LoginController", App.WCFServer + "login");
            Log.d("LoginController", jsonObject.toString());
            jsonResponse = new JSONObject(JSONParser.postStream(
                    App.WCFServer + "login",
                    jsonObject.toString()).trim());

            setSessionID(context, jsonResponse.getString("SessionID"));

            if (getSessionID(context).length() > 3) {
                result = true;
                Log.d("LoginController", "Session ID Sucessfully Accquired");
            } else {
                Log.d("LoginController", "Could not Get Session ID");
            }
        } catch (Exception e) {
            Log.e("LoginController", e.getMessage());
        }

        return result;
    }

    public static ArrayList<String> GetRolesFromCurrentSessionId(Context context) {
        ArrayList<String> result = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonResponse;

        try {
            jsonObject.put("sessionID", getSessionID(context));

            Log.d("LoginController", App.WCFServer + "GetRolesFromSession");
            Log.d("LoginController", jsonObject.toString());

            jsonResponse = new JSONArray(JSONParser.postStream(
                    App.WCFServer + "GetRolesFromSession",
                    jsonObject.toString()).trim());

            for(int i = 0; i < jsonResponse.length(); i++)
            {
                result.add(jsonResponse.getString(i));
            }

            Log.d("LoginController", jsonResponse.toString());
        } catch (Exception e) {
            Log.e("LoginController", e.getMessage());
        }

        currentRoles = result;

        return result;
    }

    public static boolean IsCurrentSessionValid(Context context) {
        boolean result = false;
        JSONObject jsonObject = new JSONObject();
        String response;

        try {
            jsonObject.put("sessionID", getSessionID(context));

            if(getSessionID(context).length() >  3) {
                Log.d("LoginController", App.WCFServer + "checkSession");
                Log.d("LoginController", jsonObject.toString());

                response = JSONParser.postStream(
                        App.WCFServer + "checkSession",
                        jsonObject.toString()).trim();

                Log.d("LoginController", response);

                result = response.equals("true");
            }else{
                result = false;
            }
        } catch (Exception e) {
            Log.e("LoginController", e.getMessage());
        }

        return result;
    }

    private static void setSessionID(Context context, String sessionID) {
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        Log.d("LoginController", "Setting Session ID to :" + sessionID);
        editor.putString("SessionID", sessionID);
        editor.apply();
    }

    public static String getSessionID(Context context) {
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(context);
        Log.d("LoginController", "Reading Session ID :'" + pref.getString("SessionID", "0") + "'");
        return pref.getString("SessionID", "0");
    }

    public static void Logout(Context context, Activity currActivity) {
        setSessionID(context, "0");
        currentRoles.clear();

        Log.d("MainActivity", "Session Invalid, Need to login");
        currActivity.setResult(LOGOUT_CODE);
        currActivity.finish();
    }

    public static String GetLoggedInEmployeeNumber(Context context)
    {
        String result = "";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("sessionID", getSessionID(context));

            Log.d("LoginController", App.WCFServer + "GetEmployeeIDFromSession");
            Log.d("LoginController", jsonObject.toString());

            result = JSONParser.postStream(
                    App.WCFServer + "GetEmployeeIDFromSession",
                    jsonObject.toString()).trim();
            result = result.substring(1, result.length()-1);

            Log.d("LoginController", result);
        } catch (Exception e) {
            Log.e("LoginController", e.getMessage());
        }

        return result;
    }

    public static boolean IsServerPresent()
    {
        String result = JSONParser.getStream(App.WCFServer + "test");

        return result.contains("test");
    }
}
