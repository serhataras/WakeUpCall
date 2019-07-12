package com.groupnamenotfoundexception.wakeupcall.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.groupnamenotfoundexception.wakeupcall.app.R;
import com.groupnamenotfoundexception.wakeupcall.app.managers.DailySleepManager;
import com.todddavies.components.progressbar.ProgressWheel;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Alchemistake on 27/07/15.
 */
public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> {
    Activity activity;
    LineChartData dataSound, dataMovement;
    DailySleepManager dailySleepManager;

    public StatsAdapter(Activity activity) {
        this.activity = activity;

        StringTokenizer stringTokenizerSound = new StringTokenizer(
                activity.getSharedPreferences(activity.getString(R.string.alarms_master), Context.MODE_PRIVATE).
                        getString(activity.getString(R.string.collection_sound), ""), ","),
                stringTokenizerMovement = new StringTokenizer(activity.getSharedPreferences(
                        activity.getString(R.string.alarms_master), Context.MODE_PRIVATE)
                        .getString(activity.getString(R.string.collection_movement), ""), ",");

        List<PointValue> listSound = new ArrayList<PointValue>();
        List<PointValue> listMovement = new ArrayList<PointValue>();

        int count = 0;
        while (stringTokenizerSound.hasMoreTokens()) {
            listMovement.add(new PointValue(count, -1 * Float.parseFloat(stringTokenizerMovement.nextToken())));
            listSound.add(new PointValue(count, (float) (20 * Math.log10(Double.parseDouble(stringTokenizerSound.nextToken())))));
            count++;
        }

        // Daily Sleep
        dailySleepManager = new DailySleepManager(activity);

        // Sound
        Line lineSound = new Line(listSound).setColor(Color.BLUE).setCubic(true);

        dataSound = new LineChartData();
        dataSound.setAxisXBottom(Axis.generateAxisFromRange(-10000, 10000, 1));

        lineSound.setStrokeWidth(3);

        List<Line> linesSound = new ArrayList<Line>();
        linesSound.add(lineSound);

        dataSound.setLines(linesSound);

        // Movement
        Line lineMovement = new Line(listMovement).setColor(Color.RED).setCubic(true);

        dataMovement = new LineChartData();
        dataMovement.setAxisXBottom(Axis.generateAxisFromRange(-10000, 10000, 1));

        lineMovement.setStrokeWidth(3);

        List<Line> linesMovement = new ArrayList<Line>();
        linesMovement.add(lineMovement);

        dataMovement.setLines(linesMovement);
    }

    @Override
    public StatsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stats_line_graph, parent, false));
        }
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stats_daily_weekly, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == 0) {
            LineChartView lineChart = (LineChartView) holder.itemView.findViewById(R.id.lineChart);
            TextView title = (TextView) holder.itemView.findViewById(R.id.graphTitle);

            lineChart.setInteractive(false);
            lineChart.setZoomType(ZoomType.HORIZONTAL);
            lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);

            if (position == 0) {
                lineChart.setLineChartData(dataSound);
                title.setText("Sound / Time Chart");
            } else {
                lineChart.setLineChartData(dataMovement);
                title.setText("Movement / Time Chart");
            }
        } else {
            ImageView smileyFace = (ImageView) holder.itemView.findViewById(R.id.dailySleepSmiley);
            ProgressWheel weeklySleepWheel = (ProgressWheel) holder.itemView.findViewById(R.id.weeklySleep);

            smileyFace.setImageResource(R.drawable.nodata);

            if (dailySleepManager.getSmileyFace() == -1) {
                smileyFace.setImageResource(R.drawable.smileyface3);
            } else if (dailySleepManager.getSmileyFace() == 0) {
                smileyFace.setImageResource(R.drawable.smileyface2);
            } else if (dailySleepManager.getSmileyFace() == 1) {
                smileyFace.setImageResource(R.drawable.smileyface1);
            }

            long weeklySleep = activity.getSharedPreferences(activity.getString(R.string.alarms_master), Context.MODE_PRIVATE).getLong(activity.getString(R.string.weekly_sleep),0);
            weeklySleepWheel.setText( (int)(((double) weeklySleep / 3600000)*10)/10.0 + " Hours Slept");
            weeklySleepWheel.setProgress((int) weeklySleep / (8 * 7 * 10000));
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 2)
            return 0;
        else
            return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
