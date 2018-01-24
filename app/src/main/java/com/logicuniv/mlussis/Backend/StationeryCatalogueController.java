package com.logicuniv.mlussis.Backend;

import com.logicuniv.mlussis.Model.StationeryCatalogue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by e0231991 on 24/1/2018.
 */

public class StationeryCatalogueController {


        public static ArrayList<StationeryCatalogue> getCatalogue()
        {
            //getCatalogue() JSON Parser get as function
            ArrayList<StationeryCatalogue> alsc = new ArrayList<>();
            StationeryCatalogue scl1 = new StationeryCatalogue("I001","Clips 1", "Clip", "Dozen", "A7", "BANES", "CHEP", "ALPA");
            StationeryCatalogue scl2 = new StationeryCatalogue("I002","Pilot", "Pen", "Each", "B8", "CHEP", "OMEG", "BANES");
            StationeryCatalogue scl3 = new StationeryCatalogue("I003","Cniballkhlasdjhkgkdslfahkgsadgkjlasdkfjaksdjlhfka", "Pen", "Each", "C4","ALPA","BANES","CHEP");
            StationeryCatalogue scl4 = new StationeryCatalogue("I004","Clips 2", "Clip", "Dozen", "A7", "BANES", "CHEP", "ALPA");
            StationeryCatalogue scl5 = new StationeryCatalogue("I005","Clips 3", "Clip", "Dozen", "A7", "BANES", "CHEP", "ALPA");
            StationeryCatalogue scl6 = new StationeryCatalogue("I006","Clips 4", "Clip", "Dozen", "A7", "BANES", "CHEP", "ALPA");
            StationeryCatalogue scl7 = new StationeryCatalogue("I007","Clips 5", "Clip", "Dozen", "A7", "BANES", "CHEP", "ALPA");
            StationeryCatalogue scl8 = new StationeryCatalogue("I008","Clips 6", "Clip", "Dozen", "A7", "BANES", "CHEP", "ALPA");
            StationeryCatalogue scl9 = new StationeryCatalogue("I009","Clips 7", "Clip", "Dozen", "A7", "BANES", "CHEP", "ALPA");
            alsc.add(scl1);
            alsc.add(scl2);
            alsc.add(scl3);
            alsc.add(scl4);
            alsc.add(scl5);
            alsc.add(scl6);
            alsc.add(scl7);
            alsc.add(scl8);
            alsc.add(scl9);
            return alsc;
        }

    public void getCatalogueByCategory(String category)
    {

    }

        public static ArrayList<StationeryCatalogue> searchCatalogue(StationeryCatalogue sc)
        {
            //searchJSONFromUrl url will settle
            ArrayList<StationeryCatalogue> alscc = getCatalogue(); //dummy values
            ArrayList<StationeryCatalogue> alsccfake = new ArrayList<>();

            for(StationeryCatalogue scdummy :alscc)
            {
                if(sc.get("Description").contains(scdummy.get("Description"))==true) {
                    alsccfake.add(scdummy);
                }
            }

            return alsccfake;
        }

        public static StationeryCatalogue searchCatalogueById(String itemNo)
        {
            //searchJSONFromUrl url will settle
            ArrayList<StationeryCatalogue> alscc = getCatalogue(); //dummy values
            StationeryCatalogue sc=null;

            for(StationeryCatalogue scdummy :alscc)
            {
                if(scdummy.get("ItemNo").equals(itemNo)) {
                    sc=scdummy;
                }
            }
            return sc;
        }



    }