package com.example.lawrence.addcourse;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Lawrence on 4/21/2017.
 */
public class Tab1 extends Fragment {
    ListView listview;
    SQLiteDatabase dbase;
    DBHelper dhelpa;
    Cursor mCursor;
    DataAdapter mDataAdapter;
    DataProvider pro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.tab1, container, false);

        listview = (ListView) rootView.findViewById(R.id.lvcustom);
        mDataAdapter = new DataAdapter(getActivity().getApplicationContext(), R.layout.custom_list_layout);
        listview.setAdapter(mDataAdapter);
        dhelpa = new DBHelper(getActivity().getApplicationContext());
        dbase = dhelpa.getReadableDatabase();
        mCursor = dhelpa.getAllRows(1);

        if(mCursor.moveToNext()){
            do {
                String title, code;
                int mester, xam;

                int ti = mCursor.getColumnIndex(dhelpa.COLUMN_TITLE);
                int co = mCursor.getColumnIndex(dhelpa.COLUMN_COS_CODE);
                int caz = mCursor.getColumnIndex(dhelpa.COLUMN_CA_SCORE);
                int xaz = mCursor.getColumnIndex(dhelpa.COLUMN_EXAM_SCORE);
                title = mCursor.getString(ti);
                code = mCursor.getString(co);
                mester = mCursor.getInt(caz);
                xam = mCursor.getInt(xaz);

                pro = new DataProvider(title,code,mester,xam);
                mDataAdapter.add(pro);

            }while(mCursor.moveToNext());
        }
        registerForContextMenu(listview);
            return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
         super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        menu.setHeaderTitle("Choose Action");
        inflater.inflate(R.menu.list_item_action, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete_item:
                //String where = Column_id + "=" + id;
                //db.delete(tablename, where, null);

                return true;
            case R.id.edit_item:

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}

//    public void populateListView(){
//        Cursor cursor = mHelper.getAllRows();
//        String[] fromDB = new String[]{DBHelper.COLUMN_TITLE,DBHelper.COLUMN_COS_CODE,
//                DBHelper.COLUMN_CA_SCORE,DBHelper.COLUMN_EXAM_SCORE};
//
//        int[] toViews = new int[]{R.id.tv_list_title,R.id.tv_list_code,R.id.tv_list_ca,R.id.tv_list_exam};
//        SimpleCursorAdapter cursorAdapter;
//        cursorAdapter = new SimpleCursorAdapter(getActivity(),R.layout.custom_list_layout,cursor,fromDB,toViews,0);
//         ListView myList = (ListView) mActivity.findViewById(R.id.lvcustom);
//        myList.setAdapter(cursorAdapter);
//
//    }


            //mHelper = new DBHelper(getActivity());
//
//        // This guys return context, after all this suffering
//        // i've finally gotten it! Rubblish stupid program
//        //getActivity().getApplicationContext();
//        //getActivity().getBaseContext();
//
//            ListView listView;
//
//            String [] players = {"MTH 111   Elementary Mathematics I","COS 101  Introduction to Computer Science",
//                    "GSP 111    Use of Library and Study skills","STA 131   Inference 1",
//                    "GSP 101    Communication in English", "MTH 121  Elementary Mathematics 2",
//                    "PHY 191    Pratical Physics",
//                    "PHY 115    General Physics for Physical Sciences"
//            };
//            //populateListView();
//
//
//            listView = (ListView) rootView.findViewById(R.id.lvcustom);
////            ArrayAdapter <String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,players);
//            ListAdapter adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,players);
//
//            listView.setAdapter(adapter);