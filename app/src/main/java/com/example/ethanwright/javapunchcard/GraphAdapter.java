package com.example.ethanwright.javapunchcard;

/**
 * Created by ethanwright on 5/28/17.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.solver.SolverVariable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;

import java.util.ArrayList;

public class GraphAdapter extends ArrayAdapter<PieData>{
    private int layoutResource;


    public GraphAdapter(Context context, int layoutResource, ArrayList<PieData> report) {
        super(context, layoutResource, report);
        this.layoutResource = layoutResource;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //4
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_graph, null);
        }

        final PieData p = getItem(position);

        if (p != null) {
            PieChart pieChart;
            pieChart = (PieChart) v.findViewById(R.id.chart);
            String name = p.getDataSet().getLabel();
            Typeface roboto_thin = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Thin.ttf");
//            p.getDataSet().setLabel("");
            pieChart.setData(p);
            pieChart.setCenterText(name);
            pieChart.setCenterTextSize(20);
            pieChart.setDrawEntryLabels(false);
            pieChart.setCenterTextTypeface(roboto_thin);

            pieChart.getData().setValueTextSize(20);
            pieChart.getDescription().setEnabled(false);

            pieChart.getLegend().setWordWrapEnabled(true);
            pieChart.getLegend().setFormSize(20);
            pieChart.getLegend().setForm(Legend.LegendForm.CIRCLE);
            pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            pieChart.getLegend().setTextSize(20);
            pieChart.getLegend().setTypeface(roboto_thin);

            pieChart.setUsePercentValues(true);
            pieChart.setDrawSlicesUnderHole(true);
            ArrayList<PieChart> charts = new ArrayList<>();
            charts.add(pieChart);
            pieChart.invalidate(); // refresh
        }

        return v;
    }

}

