package com.example.saajan.navigation_implementation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.anastr.speedviewlib.SpeedView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Temperature extends AppCompatActivity {

    private DatabaseReference ref,ref2;
    private SpeedView gauge;
    private LineChart chart;
    private PieChart pie_chart,pie_chart1,pie_chart2;
    private TextView temperature;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        init();

        readTemperature();

        drawLineChart();

        drawPieChart();

        drawBarChart();
    }

    private void init() {
        gauge=findViewById(R.id.gauge);
        temperature=findViewById(R.id.temperature);
        chart=findViewById(R.id.chart);
        pie_chart=findViewById(R.id.pie_chart);
        pie_chart1=findViewById(R.id.pie_chart1);
        pie_chart2=findViewById(R.id.pie_chart2);
        barChart = findViewById(R.id.bar);
        gauge.setWithTremble(false);
        gauge.setMarkColor(-1);
    }
    private void readTemperature() {

        ref= FirebaseDatabase.getInstance().getReference("LiveMonitoring/Temperature");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data=dataSnapshot.getValue(String.class);
                Float f=Float.parseFloat(data);
                gauge.setSpeedAt(f);
                temperature.setText("Temperature : " + f +" C");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Failed");
            }
        });


    }

    private void drawLineChart() {

        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);

        String mon[]= {"Mar 04","Mar 05","Mar 06","Mar 07","Mar 08","Mar 09","Mar 10"};

        ArrayList<Entry> yvalues=new ArrayList<>();
        yvalues.add(new Entry(0,60f));
        yvalues.add(new Entry(1,50f));
        yvalues.add(new Entry(2,70f));
        yvalues.add(new Entry(3,30f));
        yvalues.add(new Entry(4,40f));
        yvalues.add(new Entry(5,60f));
        yvalues.add(new Entry(6,70f));

        ArrayList<Entry> xvalues=new ArrayList<>();
        xvalues.add(new Entry(0,55f));
        xvalues.add(new Entry(1,45f));
        xvalues.add(new Entry(2,65f));
        xvalues.add(new Entry(3,25f));
        xvalues.add(new Entry(4,35f));
        xvalues.add(new Entry(5,55f));
        xvalues.add(new Entry(6,60f));

        ArrayList<Entry> zvalues=new ArrayList<>();
        zvalues.add(new Entry(0,50f));
        zvalues.add(new Entry(1,40f));
        zvalues.add(new Entry(2,30f));
        zvalues.add(new Entry(3,20f));
        zvalues.add(new Entry(4,30f));
        zvalues.add(new Entry(5,50f));
        zvalues.add(new Entry(6,45f));

        LineDataSet set1= new LineDataSet(yvalues,"Max Temperature");
        set1.setColor(Color.RED);
        set1.setValueTextColor(Color.WHITE);
        set1.setValueTextSize(10f);

        LineDataSet set2= new LineDataSet(xvalues,"Average Temperature");
        set2.setColor(Color.YELLOW);
        set2.setValueTextColor(Color.WHITE);
        set2.setValueTextSize(10f);

        LineDataSet set3= new LineDataSet(zvalues,"Min Temperature");
        set3.setColor(Color.GREEN);
        set3.setValueTextColor(Color.WHITE);
        set3.setValueTextSize(10f);

        ArrayList<ILineDataSet> datasets= new ArrayList<>();
        datasets.add(set1);
        datasets.add(set2);
        datasets.add(set3);

        LineData data=new LineData(datasets);

        XAxis xaxis=chart.getXAxis();
        xaxis.setValueFormatter(new MyFormatter(mon));
        xaxis.setGranularity(1f);

        chart.animateX(1000);
        chart.getXAxis().setTextColor(-1);
        chart.getLegend().setTextColor(-1);

        chart.setData(data);
    }

    private void drawPieChart() {

        pie_chart.setUsePercentValues(false);
        pie_chart.getDescription().setEnabled(true);
        pie_chart.setExtraOffsets(5,10,5,5);
        pie_chart.setDragDecelerationFrictionCoef(0.9f);
        pie_chart.setDrawHoleEnabled(true);
        pie_chart.setHoleColor(Color.BLACK);
        pie_chart.setTransparentCircleRadius(60f);
        pie_chart.setCenterText(" Average Temperature in Degree Celcius");
        pie_chart.setCenterTextSize(22f);
        pie_chart.setCenterTextColor(Color.WHITE);


        pie_chart1.setUsePercentValues(false);
        pie_chart1.getDescription().setEnabled(true);
        pie_chart1.setExtraOffsets(5,10,5,5);
        pie_chart1.setDragDecelerationFrictionCoef(0.9f);
        pie_chart1.setDrawHoleEnabled(true);
        pie_chart1.setHoleColor(Color.BLACK);
        pie_chart1.setTransparentCircleRadius(60f);
        pie_chart1.setCenterText(" Average Temperature in Degree Celcius");
        pie_chart1.setCenterTextSize(22f);
        pie_chart1.setCenterTextColor(Color.WHITE);

        pie_chart2.setUsePercentValues(false);
        pie_chart2.getDescription().setEnabled(true);
        pie_chart2.setExtraOffsets(5,10,5,5);
        pie_chart2.setDragDecelerationFrictionCoef(0.9f);
        pie_chart2.setDrawHoleEnabled(true);
        pie_chart2.setHoleColor(Color.BLACK);
        pie_chart2.setTransparentCircleRadius(60f);
        pie_chart2.setCenterText(" Average Temperature in Degree Celcius");
        pie_chart2.setCenterTextSize(22f);
        pie_chart2.setCenterTextColor(Color.WHITE);

        ArrayList<PieEntry> val=new ArrayList<>();
        val.add(new PieEntry(34f,"12:00 - 02:00 pm"));
        val.add(new PieEntry(54f,"02:00 - 04:00 pm"));
        val.add(new PieEntry(40f,"04:00 - 06:00 pm"));
        val.add(new PieEntry(45f,"06:00 - 08:00 pm"));

        ArrayList<PieEntry> val1=new ArrayList<>();
        val1.add(new PieEntry(34f,"08:00 - 10:00 pm"));
        val1.add(new PieEntry(54f,"10:00 - 11:59 pm"));
        val1.add(new PieEntry(40f,"12:00 - 02:00 am"));
        val1.add(new PieEntry(45f,"02:00 - 04:00 am"));

        ArrayList<PieEntry> val2=new ArrayList<>();
        val2.add(new PieEntry(34f,"04:00 - 06:00 am"));
        val2.add(new PieEntry(54f,"06:00 - 08:00 am"));
        val2.add(new PieEntry(40f,"08:00 - 10:00 am"));
        val2.add(new PieEntry(45f,"10:00 - 12:00 pm"));


        Description des= new Description();
        des.setText("Swipe <<");
        des.setTextColor(-1);
        des.setTextSize(15f);

        PieDataSet dataSet= new PieDataSet(val,"");
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieDataSet dataSet1= new PieDataSet(val1,"");
        dataSet1.setSliceSpace(1f);
        dataSet1.setSelectionShift(5f);
        dataSet1.setColors(ColorTemplate.PASTEL_COLORS);

        PieDataSet dataSet2= new PieDataSet(val2,"");
        dataSet2.setSliceSpace(1f);
        dataSet2.setSelectionShift(5f);
        dataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data1= new PieData((dataSet));
        data1.setValueTextColor(Color.BLACK);
        data1.setValueTextSize(18f);

        PieData data2= new PieData((dataSet1));
        data2.setValueTextColor(Color.BLACK);
        data2.setValueTextSize(18f);

        PieData data3= new PieData((dataSet2));
        data3.setValueTextColor(Color.BLACK);
        data3.setValueTextSize(18f);


        pie_chart.getLegend().setTextColor(-1);
        pie_chart.setDrawSliceText(false);
        pie_chart.setData(data1);
        pie_chart.getLegend().setTextSize(10f);
        pie_chart.setDescription(des);

        pie_chart1.getLegend().setTextColor(-1);
        pie_chart1.setDrawSliceText(false);
        pie_chart1.setData(data2);
        pie_chart1.setDescription(des);

        pie_chart2.getLegend().setTextColor(-1);
        pie_chart2.setDrawSliceText(false);
        pie_chart2.setData(data3);

    }
    private void drawBarChart() {


        BarDataSet barDataSet = new BarDataSet(getData(), "Average Temperature");
        barDataSet.setBarBorderWidth(0.9f);
        //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(barDataSet);
        barData.setValueTextColor(-1);
        barData.setValueTextSize(15f);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun","July","Aug","Sep","Oct","Nov","Dec"};
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(months);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.animateXY(5000, 5000);
        barChart.invalidate();
        barChart.getXAxis().setTextColor(-1);
        barChart.getLegend().setTextColor(-1);

    }

    private ArrayList getData(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        entries.add(new BarEntry(4f, 70f));
        entries.add(new BarEntry(5f, 60f));
        entries.add(new BarEntry(6f, 30f));
        entries.add(new BarEntry(7f, 10f));
        entries.add(new BarEntry(8f, 37f));
        entries.add(new BarEntry(9f, 42f));
        entries.add(new BarEntry(10f, 25f));
        entries.add(new BarEntry(11f, 23f));
        return entries;
    }
}


class MyFormatter extends ValueFormatter {

    public String valus[];

    public MyFormatter(String[] values) {
        this.valus = values;
    }

    @Override
    public String getAxisLabel( float value, AxisBase axis) {
        return valus[(int)value];
    }
}

