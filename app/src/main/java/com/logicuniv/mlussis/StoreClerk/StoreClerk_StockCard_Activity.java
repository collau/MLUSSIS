package com.logicuniv.mlussis.StoreClerk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.logicuniv.mlussis.R;
import com.logicuniv.mlussis.StationeryCatalogue;

import org.w3c.dom.Text;

import java.util.HashMap;

public class StoreClerk_StockCard_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk__stock_card_);
        Bundle b = getIntent().getExtras();
        HashMap<String, String> sc = (HashMap<String, String>) b.get("invdetails");
        TextView t1 = findViewById(R.id.textViewItemCode);
        t1.setText(sc.get("ItemNo"));
        TextView t2 = findViewById(R.id.textViewItemDesc);
        t2.setText(sc.get("Description"));
        TextView t3 = findViewById(R.id.textViewItemBin);
        t3.setText(sc.get("BinNo"));
        TextView t4 = findViewById(R.id.textViewItemUOM);
        t4.setText(sc.get("Uom"));
        TextView t5 = findViewById(R.id.textViewItemSupp1);
        t5.setText(sc.get("Supplier1"));
        TextView t6 = findViewById(R.id.textViewItemSupp2);
        t6.setText(sc.get("Supplier2"));
        TextView t7 = findViewById(R.id.textViewItemSupp3);
        t7.setText(sc.get("Supplier3"));
    }
}
