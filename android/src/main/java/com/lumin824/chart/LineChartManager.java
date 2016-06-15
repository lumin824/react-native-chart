package com.lumin824.chart;

import android.graphics.Color;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class LineChartManager extends SimpleViewManager<LineChart>{

  @Override
  public String getName() {
    return "LineChart";
  }

  @Override
  protected LineChart createViewInstance(ThemedReactContext reactContext) {
    LineChart chart = new LineChart(reactContext);

    XAxis xAxis = chart.getXAxis();
    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    xAxis.setDrawAxisLine(false);
    xAxis.setDrawGridLines(false);

    YAxis leftAxis = chart.getAxisLeft();
    leftAxis.resetAxisMinValue();
    leftAxis.enableGridDashedLine(2.0f, 2.0f, 0);

    YAxis rightAxis = chart.getAxisRight();
    rightAxis.setEnabled(false);

    Legend legend = chart.getLegend();
    legend.setEnabled(false);

    chart.setScaleEnabled(false);
    chart.setDragEnabled(false);
    chart.setHighlightPerDragEnabled(false);
    chart.setDoubleTapToZoomEnabled(false);
    chart.setPinchZoom(false);
    chart.setBackgroundColor(Color.WHITE);
    chart.setDescription("");


    return chart;
  }

  // {xVals:[],dataSet:[{yVals:[]},{}]}
  @ReactProp(name="data")
  public void setData(LineChart chart, ReadableMap map){
    ReadableArray xArray = map.getArray("xVals");
    ArrayList<String> xVals = new ArrayList<String>();
    for(int i = 0; i < xArray.size(); i++){
      xVals.add(xArray.getString(i));
    }
    LineData data = new LineData(xVals);

    ReadableArray yArray = map.getArray("dataSet");
    for(int i = 0; i < yArray.size(); i++){
      ReadableMap y = yArray.getMap(i);
      ReadableArray yValArray = y.getArray("yVals");

      ArrayList<Entry> entries = new ArrayList<Entry>();
      for(int j = 0; j < yValArray.size(); j++){
        Entry entry = new Entry((float)yValArray.getDouble(j),j);
        entries.add(entry);
      }

      String label = y.getString("label");
      LineDataSet dataSet = new LineDataSet(entries,label);

      ReadableArray colorsArray = y.getArray("colors");
      if(colorsArray != null && colorsArray.size() > 0){
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for(int j = 0; j < colorsArray.size(); j++){
          colors.add(colorsArray.getInt(j));
        }
        dataSet.setColors(colors);
      }

      //double circleRadius = y.getDouble("circleRadius");
      dataSet.setDrawValues(false);
      dataSet.setCircleRadius(2);
      dataSet.setDrawCircleHole(false);

      data.addDataSet(dataSet);
    }
    chart.setData(data);
    chart.invalidate();
  }

  @ReactProp(name="backgroundColor")
  public void setBackgroundColor(LineChart chart, String v){
    chart.setBackgroundColor(Color.parseColor(v));
  }

  @ReactProp(name="description")
  public void setDescription(LineChart chart, String v){
    chart.setDescription(v);
  }

  @ReactProp(name="legend")
  public void setLegend(LineChart chart, ReadableMap map){
    Legend legend = chart.getLegend();
    if(map.hasKey("enabled")) legend.setEnabled(map.getBoolean("enabled"));
  }

//  @ReactProp(name="xAxis")
//  public void setXAxis(LineChart chart, ReadableMap map){
//    XAxis xAxis = chart.getXAxis();
//    String position = map.getString("position");
//    switch(position){
//      case "bottom": xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); break;
//      case "top": xAxis.setPosition(XAxis.XAxisPosition.TOP); break;
//    }
//  }
}
