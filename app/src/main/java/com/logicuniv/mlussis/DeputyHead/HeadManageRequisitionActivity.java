package com.logicuniv.mlussis.DeputyHead;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.logicuniv.mlussis.Backend.EmployeeController;
import com.logicuniv.mlussis.Backend.RequisitionController;
import com.logicuniv.mlussis.Model.Requisition;
import com.logicuniv.mlussis.R;

import java.util.ArrayList;

public class HeadManageRequisitionActivity extends Activity implements AdapterView.OnItemClickListener{

    ListAdapter adapt;
    ListView lv_manageReq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_manage_requisition);
        setTitle("View Pending Requisitions");

        lv_manageReq = findViewById(R.id.listView_managereqlist_deputy);
        View header = getLayoutInflater().inflate(R.layout.header_row_list_managereq_deputy,null);
        lv_manageReq.addHeaderView(header,null,false);

        new AsyncTask<Void, Void, Void>() {

            ArrayList<Requisition> alr;
            @Override
            protected Void doInBackground(Void... params) {
                alr = RequisitionController.getPendingRequisitions();

                adapt = new SimpleAdapter(HeadManageRequisitionActivity.this, alr, R.layout.row_list_managereq_deputy, new String[]{EmployeeController.getEmployeeName("IssuedBy"), "ReqNo", "DateIssued"},
                        new int[]{R.id.textView__managereq_empname, R.id.textView_managereq_reqno, R.id.textView_managereq_reqdate});

                return null;
            }
            protected void onPostExecute(Void result) {
                lv_manageReq.setAdapter(adapt);
                lv_manageReq.setOnItemClickListener(HeadManageRequisitionActivity.this);
            }
        }.execute();

//        lv_manageReq.setAdapter(adapt);
//        lv_manageReq.setOnItemClickListener(this);      //need to change


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Requisition r = (Requisition) parent.getAdapter().getItem(position);
        Intent intent = new Intent(this, ViewPendingRequisitionDetailsActivity.class);
        intent.putExtra("Req",(String)r.get("ReqNo"));

        startActivity(intent);
    }
}