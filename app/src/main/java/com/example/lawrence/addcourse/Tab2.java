package com.example.lawrence.addcourse;

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
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Lawrence on 4/21/2017.
 */
public class Tab2 extends Fragment {
    ListView listview;
    SQLiteDatabase dbase;
    DBHelper dhelpa;
    Cursor mCursor;
    DataAdapter mDataAdapter;
    DataProvider pro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootViewd  =inflater.inflate(R.layout.tab2, container, false);

        listview = (ListView) rootViewd.findViewById(R.id.lv_2yr_1st);
        mDataAdapter = new DataAdapter(getActivity().getApplicationContext(), R.layout.custom_list_layout);
        listview.setAdapter(mDataAdapter);
        dhelpa = new DBHelper(getActivity().getApplicationContext());
        dbase = dhelpa.getReadableDatabase();
        mCursor = dhelpa.getAllRows(2);

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

        return rootViewd;
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

//        if(cursor.moveToFirst()){
//            do {
//                String title = cursor.getString(1);
//                String code = cursor.getString(2);
//                int ca = cursor.getInt(6);
//                int xam = cursor.getInt(7);
//                buffer.append(title + "\t"+code+ "\t"+ca+ "\t"+xam+ "\n"  );
//            }while (cursor.moveToNext());
//        }