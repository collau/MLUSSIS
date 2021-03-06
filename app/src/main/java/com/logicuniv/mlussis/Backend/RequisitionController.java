package com.logicuniv.mlussis.Backend;

import android.util.Log;
import android.widget.TableLayout;

import com.logicuniv.mlussis.Model.Requisition;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by e0231991 on 24/1/2018.
 */

public class RequisitionController {

    static ArrayList<Requisition> alr = new ArrayList<Requisition>();

    public static boolean addRequisition(Requisition r) {
        Boolean result = false;

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonRequisition = new JSONObject();
        String response;

        try {
            //put requisition into a JSONObject "jsonRequisition"
            jsonRequisition.put("ReqNo", r.get("ReqNo"));
            jsonRequisition.put("IssuedBy", r.get("IssuedBy"));
            jsonRequisition.put("DateIssued", r.get("DateIssued"));
            jsonRequisition.put("ApprovedBy", r.get("ApprovedBy"));
            jsonRequisition.put("DateReviewed", r.get("DateReviewed"));
            jsonRequisition.put("Status", r.get("Status"));
            jsonRequisition.put("Remarks", r.get("Remarks"));

            //put JSONObject "jsonRequisitionDetail" and sessionID into a JSONObject "jsonObject"
            jsonObject.put("sessionID", LoginController.getSessionID((App.getAppContext())));
            jsonObject.put("addRequisition", jsonRequisition.toString());

            //result of passing the "jsonObject".toString() into the WCF Server
            response = JSONParser.postStream(App.WCFServer + "AddRequisition", jsonObject.toString());

            result = response.trim().equals("true");
        } catch (Exception e) {
            Log.e("AddRequisition", e.getMessage());
            result = false;
        }

        return result;
    }

    public static String CreateNewRequisition() {
        String result = null;
        JSONObject jsonObject = new JSONObject();

        try {
            //put JSONObject "jsonRequisitionDetail" and sessionID into a JSONObject "jsonObject"
            jsonObject.put("sessionID", LoginController.getSessionID((App.getAppContext())));

            Log.d("addRequisitionAndGetReq", App.WCFServer + "AddRequisitionAndGetReqNo");
            Log.d("addRequisitionAndGetReq", jsonObject.toString());

            //result of passing the "jsonObject".toString() into the WCF Server
            result = JSONParser.postStream(App.WCFServer + "AddRequisitionAndGetReqNo", jsonObject.toString()).trim();

        } catch (Exception e) {
            Log.e("AddRequisition", e.getMessage());
        }

        return result;
    }


    public static ArrayList<Requisition> getPendingRequisitions() {
        ArrayList<Requisition> result = new ArrayList<>();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonResult;
        String sessionEmpNo = LoginController.GetLoggedInEmployeeNumber(App.getAppContext());

        try {
            jsonObject.put("sessionID", LoginController.getSessionID(App.getAppContext()));
            jsonObject.put("sessionEmpNo", sessionEmpNo);

            jsonResult = new JSONArray(JSONParser.postStream(App.WCFServer + "PendingRequisitions",jsonObject.toString()));
            Log.e("jsonResultReq",jsonResult.toString());
            for (int i = 0; i < jsonResult.length(); i++) {
                result.add(new Requisition(
                        jsonResult.getJSONObject(i).getString("ReqNo"),
                        jsonResult.getJSONObject(i).getString("IssuedBy"),
                        jsonResult.getJSONObject(i).getString("DateIssued"),
                        jsonResult.getJSONObject(i).getString("ApprovedBy"),
                        jsonResult.getJSONObject(i).getString("DateReviewed"),
                        jsonResult.getJSONObject(i).getString("Status"),
                        jsonResult.getJSONObject(i).getString("Remarks")));
            }
        } catch (Exception e) {
            Log.e("PendingRequisitions", e.getMessage());
        }

        return result;
    }


    public static Requisition getRequisitionById(String reqNo) {
        Requisition result = null;
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonResult;

        try {
            jsonObject.put("sessionID", LoginController.getSessionID(App.getAppContext()));
            jsonObject.put("reqNo", reqNo);

            jsonResult = new JSONObject(JSONParser.postStream(App.WCFServer + "GetRequisitionById", jsonObject.toString()));

            result = new Requisition(
                    jsonResult.getString("ReqNo"),
                    jsonResult.getString("IssuedBy"),
                    jsonResult.getString("DateIssued"),
                    jsonResult.getString("ApprovedBy"),
                    jsonResult.getString("DateReviewed"),
                    jsonResult.getString("Status"),
                    jsonResult.getString("Remarks"));

        } catch (Exception e) {
            Log.e("RequisitionById", e.getMessage());
        }

        return result;
    }

    public static boolean updateRequisition(Requisition rUpdated) {
        Boolean result = false;

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonRequisition = new JSONObject();
        String response;

        try {
            jsonRequisition.put("ReqNo", rUpdated.get("ReqNo"));
            jsonRequisition.put("IssuedBy", rUpdated.get("IssuedBy"));
            jsonRequisition.put("DateIssued", rUpdated.get("DateIssued"));
            jsonRequisition.put("ApprovedBy", rUpdated.get("ApprovedBy"));
            jsonRequisition.put("Status", rUpdated.get("Status"));
            jsonRequisition.put("Remarks", rUpdated.get("Remarks"));


            jsonObject.put("sessionID", LoginController.getSessionID(App.getAppContext()));
            jsonObject.putOpt("updatedRequisition", jsonRequisition);

            response = JSONParser.postStream(App.WCFServer + "UpdateRequisition", jsonObject.toString());

            result = response.trim().contains("true");
        } catch (Exception e) {
            Log.e("UpdateRequisition.", e.getMessage());
            result = false;
        }

        return result;
    }


    public static boolean removeRequisition(Requisition rRemove) {

        Boolean result = false;

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonRequisition = new JSONObject();
        String response;

        try {
            //put requisition details into a JSONObject "jsonRequisitionDetail"
            jsonRequisition.put("ReqNo", rRemove.get("ReqNo"));
            jsonRequisition.put("ItemNo", rRemove.get("ItemNo"));
            jsonRequisition.put("Qty", rRemove.get("Qty"));

            //put JSONObject "jsonRequisitionDetail" and sessionID into a JSONObject "jsonObject"
            jsonObject.put("sessionID", LoginController.getSessionID((App.getAppContext())));
            jsonObject.put("removeRequisition", jsonRequisition.toString());

            //result of passing the "jsonObject".toString() into the WCF Server
            response = JSONParser.postStream(App.WCFServer + "RemoveRequisition", jsonObject.toString());

            result = response.trim().equals("true");
        } catch (Exception e) {
            Log.e("RemoveRequisition", e.getMessage());
            result = false;
        }

        return result;

    }


}
