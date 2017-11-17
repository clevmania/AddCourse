package com.example.lawrence.addcourse;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lawrence on 6/27/2017.
 */
public class DataAdapter extends ArrayAdapter {

    List mList = new ArrayList();
    public DataAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler{
        TextView ti,de,stam,am;
        ImageView im;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        mList.add(object);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View dRow = convertView;
        LayoutHandler layoutHandler;

        if(dRow == null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            dRow = inflater.inflate(R.layout.custom_list_layout,parent,false);
            layoutHandler = new LayoutHandler();
            layoutHandler.ti = (TextView) dRow.findViewById(R.id.tv_list_title);
            layoutHandler.de = (TextView) dRow.findViewById(R.id.tv_list_code);
            layoutHandler.stam = (TextView) dRow.findViewById(R.id.tv_list_ca);
            layoutHandler.am = (TextView) dRow.findViewById(R.id.tv_list_exam);
            layoutHandler.im = (ImageView) dRow.findViewById(R.id.im_grades);
            dRow.setTag(layoutHandler);
        }else {
            layoutHandler = (LayoutHandler) dRow.getTag();

        }
        DataProvider dataProvider = (DataProvider) this.getItem(position);
        layoutHandler.ti.setText(dataProvider.getCourseTitle());
        layoutHandler.de.setText(dataProvider.getCourseCode());
        layoutHandler.stam.setText(String.valueOf(dataProvider.getAssessmeent()));
        layoutHandler.am.setText(Integer.toString(dataProvider.getExam()));

        // For images display
        int grades = dataProvider.getAssessmeent() + dataProvider.getExam();
        String A = "ic_grade_a";
        String B = "ic_grade_b";
        String C = "ic_grade_c";
        String D = "ic_grade_d";
        String E = "ic_grade_e";
        String F = "ic_grade_f";
        Resources myRes = getContext().getResources();
        String myPack = getContext().getPackageName();

        if(grades >= 0 && grades <= 44){
            int res = myRes.getIdentifier(F, "mipmap", myPack);
            layoutHandler.im.setImageResource(res);
        }

        if(grades >= 45 && grades <= 49){
            int res = myRes.getIdentifier(D, "mipmap", myPack);
            layoutHandler.im.setImageResource(res);
        }

        if(grades >= 50 && grades <= 59){
            int res = myRes.getIdentifier(C, "mipmap", myPack);
            layoutHandler.im.setImageResource(res);
        }

        if(grades >= 60 && grades <= 69){
            int res = myRes.getIdentifier(B, "mipmap", myPack);
            layoutHandler.im.setImageResource(res);
        }

        if(grades >= 70 && grades <= 100){
            int res = myRes.getIdentifier(A, "mipmap", myPack);
            layoutHandler.im.setImageResource(res);
        }

        return dRow;
    }

}
