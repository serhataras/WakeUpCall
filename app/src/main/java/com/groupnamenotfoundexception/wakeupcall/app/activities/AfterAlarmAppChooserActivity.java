package com.groupnamenotfoundexception.wakeupcall.app.activities;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.groupnamenotfoundexception.wakeupcall.app.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by User on 25.7.2015.
 */
public class AfterAlarmAppChooserActivity extends ListActivity {

    ApplicationChooserAdapter adapter=null;

    int alarmIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser_layout);

        alarmIndex = getIntent().getExtras().getInt("Alarm Index");

        PackageManager pm=getPackageManager();
        Intent main=new Intent(Intent.ACTION_MAIN, null);

        main.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> launchables=pm.queryIntentActivities(main, 0);
        Collections.sort(launchables,
                new ResolveInfo.DisplayNameComparator(pm));

        adapter=new ApplicationChooserAdapter(pm, launchables);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v,
                                   int position, long id) {
        ResolveInfo launchable = adapter.getItem(position);
        ActivityInfo activity = launchable.activityInfo;
        ComponentName name = new ComponentName(activity.applicationInfo.packageName,
                activity.name);

        getSharedPreferences(getString(R.string.alarm_prefix) + alarmIndex, Context.MODE_PRIVATE).edit()
                .putString(getString(R.string.app_package_name),activity.applicationInfo.packageName).commit();

        finish();

    }

    private class ApplicationChooserAdapter extends ArrayAdapter<ResolveInfo> {

        private PackageManager pm=null;

        ApplicationChooserAdapter(PackageManager pm, List<ResolveInfo> apps) {
            super(AfterAlarmAppChooserActivity.this, R.layout.item_app_chooser, apps);
            this.pm=pm;
        }

        @Override
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            if (convertView==null) {
                convertView=newView(parent);
            }

            bindView(position, convertView);

            return(convertView);
        }

        private View newView(ViewGroup parent) {
            return(getLayoutInflater().inflate(R.layout.item_app_chooser, parent, false));
        }

        private void bindView(int position, View row) {
            TextView label=(TextView)row.findViewById(R.id.label);

            label.setText(getItem(position).loadLabel(pm));

            ImageView icon=(ImageView)row.findViewById(R.id.icon);

            icon.setImageDrawable(getItem(position).loadIcon(pm));
        }
    }

}