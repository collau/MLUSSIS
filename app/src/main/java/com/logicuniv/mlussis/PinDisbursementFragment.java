package com.logicuniv.mlussis;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PinDisbursementFragment extends Fragment {


    public PinDisbursementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pin_disbursement, container, false);
        Disbursement d = Disbursement.getCurrentDisbursementForDepartment("1");     //get parameter from login details when it is set up
        if (d != null) {
            TextView tv_Dept = v.findViewById(R.id.textView_deptRep_PinNumber);
            //getDepartmentName(d.get("DeptCode"));     //to initialise this method later
            tv_Dept.setText(d.get("Pin"));

        }

        return v;
    }

}