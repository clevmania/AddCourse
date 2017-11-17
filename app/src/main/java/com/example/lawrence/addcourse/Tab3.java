package com.example.lawrence.addcourse;

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

/**
 * Created by Lawrence on 4/21/2017.
 */
public class Tab3 extends Fragment {
    ListView listview;
    SQLiteDatabase dbase;
    DBHelper dhelpa;
    Cursor mCursor;
    DataAdapter mDataAdapter;
    DataProvider pro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootViews = inflater.inflate(R.layout.tab3, container, false);
        listview = (ListView) rootViews.findViewById(R.id.lv_tab3);
        mDataAdapter = new DataAdapter(getActivity().getApplicationContext(), R.layout.custom_list_layout);
        listview.setAdapter(mDataAdapter);
        dhelpa = new DBHelper(getActivity().getApplicationContext());
        dbase = dhelpa.getReadableDatabase();
        mCursor = dhelpa.getAllRows(3);

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
        return rootViews;
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
