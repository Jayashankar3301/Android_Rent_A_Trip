package com.example.jayashankar.rentataxi.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayashankar on 2017-10-19.
 */

public class dataList {
    public static List<getSet> getData(String[] strip_id, String[] strip_from, String[] strip_to, String[] strip_date, String[] strip_time) {


        List<getSet> data = new ArrayList<>();
        for (int i = 0; i < strip_id.length; i++) {
            //Log.d("dispalysource",src[0]);
            getSet d = new getSet();
            d.setStrip_date(strip_date[i]);
            d.setStrip_id(strip_id[i]);
            d.setStrip_from(strip_from[i]);
            d.setStrip_to(strip_to[i]);
            d.setStrip_time(strip_time[i]);

            //d.setIcon(icons[i]);
            data.add(d);

        }
        return data;
    }

    public static List<getSet> getsearchData(String[] strip_id, String[] strip_from, String[] strip_to, String[] time) {


        List<getSet> data = new ArrayList<>();
        for (int i = 0; i < strip_id.length; i++) {
            //Log.d("dispalysource",src[0]);
            getSet d = new getSet();
            d.setStrip_time(time[i]);
            d.setStrip_id(strip_id[i]);
            d.setStrip_from(strip_from[i]);
            d.setStrip_to(strip_to[i]);


            //d.setIcon(icons[i]);
            data.add(d);

        }
        return data;
    }

    public static List<getSet> getsearchDataDetails(String[] locations) {


        List<getSet> data = new ArrayList<>();
        for (int i = 0; i < locations.length; i++) {
            //Log.d("dispalysource",src[0]);
            getSet d = new getSet();
            d.setCheck_location(locations[i]);


            //d.setIcon(icons[i]);
            data.add(d);

        }
        return data;
    }

    public static List<getSet> getDriverrequest(String[] from, String[] to, String[] tfrom, String[] tto, String[] username, String[] phone, String[] date, String[] reqId) {


        List<getSet> data = new ArrayList<>();
        for (int i = 0; i < from.length; i++) {
            //Log.d("dispalysource",src[0]);
            getSet d = new getSet();
            d.setFrom(from[i]);
            d.setTo(to[i]);
            d.setTfrom(tfrom[i]);
            d.setTto(tto[i]);
            d.setUsername(username[i]);
            d.setPhone(phone[i]);
            d.setReqId(reqId[i]);


            d.setDate(date[i]);
            //d.setIcon(icons[i]);
            data.add(d);

        }
        return data;
    }

    public static List<getSet> getViewrequest(String[] from, String[] to, String[] date, String[] reqId, String seat[]) {


        List<getSet> data = new ArrayList<>();
        for (int i = 0; i < from.length; i++) {
            //Log.d("dispalysource",src[0]);
            getSet d = new getSet();
            d.setFrom(from[i]);
            d.setTo(to[i]);
            d.setReqId(reqId[i]);
            d.setSeat(seat[i]);

            d.setDate(date[i]);
            //d.setIcon(icons[i]);
            data.add(d);

        }
        return data;
    }
}


