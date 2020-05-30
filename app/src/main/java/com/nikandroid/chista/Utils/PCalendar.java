package com.nikandroid.chista.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class PCalendar {

    Context context;
    public PCalendar(Context c){
        context=c;
    }
    int ghermez,tire0;
    TextView oldtext;

    public ArrayList<ArrayList<String>>  get_month(int mo){


        ///curent day miladi
        Date ccfb = Calendar.getInstance().getTime();

        Calendar tt = Calendar.getInstance();
        tt.setTime(ccfb);
        tt.add(Calendar.MONTH,mo);
        ccfb=tt.getTime();

        SimpleDateFormat mformat = new SimpleDateFormat("yyyy/MM/dd");
        String cdatem=mformat.format(ccfb);
        Log.e("curent date miladi",mformat.format(ccfb));


        //get curent month shamsi
        PersianDate cdate = new PersianDate(ccfb);
        PersianDateFormat montformatjalali = new PersianDateFormat("m");
        Integer mname=Integer.parseInt(montformatjalali.format(cdate));//

        PersianDateFormat yearformatjalali = new PersianDateFormat("Y");
        Integer yname=Integer.parseInt(yearformatjalali.format(cdate));//

        PersianDateFormat dayformatjalali = new PersianDateFormat("d");
        Integer dname=Integer.parseInt(dayformatjalali.format(cdate));//


        Log.e("sal",yname+"");
        Log.e("mah",mname+"");
        Log.e("rooz",dname+"");


        PersianDateFormat labelformat = new PersianDateFormat("F y");
        PersianDateFormat dayofstartformat = new PersianDateFormat("w");

        String label=labelformat.format(cdate);




        //get first date miladi and last date miladi from month shamsi

        //// first day month
        PersianDate datem = new PersianDate();
        int[] c=datem.toGregorian(yname,mname,1);
        String fdate=c[0]+"/"+c[1]+"/"+c[2];
        Log.e("firstday",fdate+"");
        //// first day month




        //// last day month
        int[] e=datem.toGregorian(yname,mname,31);
        String edate=e[0]+"/"+e[1]+"/"+e[2];
        Log.e("eirstday",edate+"");
        //// last day month




        String dtStart =c[0]+"/"+c[1]+"/"+c[2];

        SimpleDateFormat ffmm = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cc = Calendar.getInstance();
        int dayofstart=0;
        try {
            Date dt = ffmm.parse(dtStart);
            cc.setTime(dt);


            dayofstart=convert_startday_to_jalali(cc.get(Calendar.DAY_OF_WEEK));
        } catch (ParseException q) {
            q.printStackTrace();
        }

        //int dayofstart=Integer.parseInt(dayofstartformat.format(cdate));




        ArrayList<ArrayList<String>> tmp=new ArrayList<ArrayList<String>>();

        tmp.add(fill_daysname("شنبه"));
        tmp.add(fill_daysname("یکشنبه"));
        tmp.add(fill_daysname("دوشنبه"));
        tmp.add(fill_daysname("سه شنبه"));
        tmp.add(fill_daysname("چهارشنبه"));
        tmp.add(fill_daysname("پنج شنبه"));
        tmp.add(fill_daysname("جمعه"));

        Log.e("dayofstart",dayofstart+"");
        for (int i=0;i<dayofstart;i++){
            ArrayList<String> d1=new ArrayList<String>();
            d1.add("");//0
            d1.add("");//1
            d1.add("");//2
            d1.add("");//3
            d1.add("");//4
            d1.add("");//5
            d1.add(" ");//6
            d1.add("");//7
            d1.add(label);//8
            d1.add("e");//9
            tmp.add(d1);
        }


        PersianDate fordates = new PersianDate();
        //for between firstdate and last date

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");




        for(int i=0;i<cdate.getMonthLength();i++){

            ArrayList<String> row=new ArrayList<String>();

            if(i>0){
                cc.add(Calendar.DATE, 1);
            }
            String fdmiladi=sdf1.format(cc.getTime());
            row.add(fdmiladi);// full date miladi //0
            String[] dmm=fdmiladi.split("/");
            int[] p=datem.toJalali(Integer.parseInt(dmm[0]),Integer.parseInt(dmm[1]),Integer.parseInt(dmm[2]));
            row.add(dmm[0]);//1 sal miladi
            row.add(dmm[1]);//2 mah miladi
            row.add(dmm[2]);//3 rooz miladi
            row.add(p[0]+"");//4 sal shamsi
            row.add(p[1]+"");//5 mah shamsi
            row.add(p[2]+"");//6 roz shamsi
            row.add(p[0]+"/"+p[1]+"/"+p[2]);//full date shapsi //7
            row.add(label);//Label //8
            row.add("d");//type //9

            tmp.add(row);


        }

        return tmp;


    }
    public int convert_startday_to_jalali(int v){
        if(v==7){ return 0; }
        if(v==6){ return 6; }
        if(v==5){ return 5; }
        if(v==4){ return 4; }
        if(v==3){ return 3; }
        if(v==2){ return 2; }
        if(v==1){ return 1; }
        return 0;
    }
    public ArrayList<String> fill_daysname(String name){
        ArrayList<String> d1=new ArrayList<String>();
        d1.add("");//0
        d1.add("");//1
        d1.add("");//2
        d1.add("");//3
        d1.add("");//4
        d1.add("");//5
        d1.add(name);//6
        d1.add("");//7
        d1.add("");//8
        d1.add("l");//9
        return d1;
    }







}
