package com.logicuniv.mlussis.DepartmentRep;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.logicuniv.mlussis.Backend.DisbursementDetailController;
import com.logicuniv.mlussis.Backend.StationeryCatalogueController;
import com.logicuniv.mlussis.Model.*;
import com.logicuniv.mlussis.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisbursementListFragment extends Fragment {

    ArrayList<DisbursementDetail> disburse=null;

    TableLayout table;

    boolean firstRowAdded = false;
    public DisbursementListFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_disbursement_list, container, false);

        table = (TableLayout)v.findViewById(R.id.table_deptRep_disbursement_list);

        printDisbursementTable();

        return v;
    }

    public void printDisbursementTable()
    {
        //clear arraylist
        if(disburse!=null) {
            disburse.clear();

            int count = table.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = table.getChildAt(i);
                if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
            }
            firstRowAdded=false;
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                disburse = DisbursementDetailController.getCurrentDisbursementDetailsForDepartment();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {

                TableRow firstrow = (TableRow) LayoutInflater.from(getActivity()).inflate(R.layout.row_disbursement_list, null);
                //populate table header view if there is values in disburse
                if (!disburse.isEmpty()&& firstRowAdded==false){
                    ((TextView) firstrow.findViewById(R.id.textView_deptRep_disbursement_list_item)).setText("Item Description.");
                    ((TextView) firstrow.findViewById(R.id.textView_deptRep_disbursement_list_item)).setTypeface(null, Typeface.BOLD);
                    ((TextView) firstrow.findViewById(R.id.textView_deptRep_disbursement_list_qty)).setText("Rec. Qty");
                    ((TextView) firstrow.findViewById(R.id.textView_deptRep_disbursement_list_qty)).setTypeface(null,Typeface.BOLD);
                    table.addView((firstrow));
                    firstRowAdded=true;
                }
                if (disburse != null) {
                    for (DisbursementDetail d : disburse) {
                        // Inflate your row "template" and fill out the fields.
                        TableRow row = (TableRow) LayoutInflater.from(getActivity()).inflate(R.layout.row_disbursement_list, null);
                        ((TextView) row.findViewById(R.id.textView_deptRep_disbursement_list_item)).setText(d.get("Description"));
                        ((TextView) row.findViewById(R.id.textView_deptRep_disbursement_list_item)).setPadding(0,15,0,15);
                        ((TextView) row.findViewById(R.id.textView_deptRep_disbursement_list_qty)).setText(d.get("Received"));
                        ((TextView) row.findViewById(R.id.textView_deptRep_disbursement_list_qty)).setPadding(0,15,0,15);

                        table.addView(row);
                    }
                    table.requestLayout();
                }
            }

        }.execute();

    }

}
