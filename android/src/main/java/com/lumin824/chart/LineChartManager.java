package com.lumin824.chart;

import android.graphics.Color;
import android.view.MotionEvent;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.common.SystemClock;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Nullable;

public class LineChartManager extends SimpleViewManager<LineChart>{

  @Override
  public String getName() {
    return "LineChart";
  }

  @Override
  protected LineChart createViewInstance(final ThemedReactContext reactContext) {
    final LineChart chart = new LineChart(reactContext);

    XAxis xAxis = chart.getXAxis();
    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    xAxis.setDrawAxisLine(false);
    xAxis.setDrawGridLines(false);

    YAxis leftAxis = chart.getAxisLeft();
    leftAxis.resetAxisMinValue();
    leftAxis.enableGridDashedLine(4.0f, 4.0f, 0);
    leftAxis.setSpaceBottom(10f);
    leftAxis.setSpaceTop(10f);
    leftAxis.setGranularityEnabled(true);
    leftAxis.setGranularity(1.0f);

    YAxis rightAxis = chart.getAxisRight();
    rightAxis.setEnabled(false);

    Legend legend = chart.getLegend();
    legend.setEnabled(false);

    chart.setScaleEnabled(false);
    chart.setDragEnabled(false);
    chart.setHighlightPerDragEnabled(false);
    chart.setDoubleTapToZoomEnabled(false);
    chart.setPinchZoom(false);
    chart.setDescription("");


    return chart;
  }

  @Override
  protected void addEventEmitters(final ThemedReactContext reactContext, final LineChart chart) {

    chart.setOnChartGestureListener(new OnChartGestureListener(){

      @Override
      public void onChartGestureStart(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture) {

      }

      @Override
      public void onChartGestureEnd(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture) {

      }

      @Override
      public void onChartLongPressed(MotionEvent motionEvent) {

      }

      @Override
      public void onChartDoubleTapped(MotionEvent motionEvent) {

      }

      @Override
      public void onChartSingleTapped(MotionEvent motionEvent) {

      }

      @Override
      public void onChartFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        int verticalMinDistance = 20;
        String direction = "";

        float moveX = e1.getX() - e2.getX();
        float moveY = e1.getY() - e2.getY();

        if(Math.abs(moveX) > Math.abs(moveY)){
          if(Math.abs(moveX) > verticalMinDistance){
            if(moveX > 0){
              direction="left";
            }else{
              direction="right";
            }
          }
        }else{
          if(Math.abs(moveY) > verticalMinDistance){
            if(moveY > 0){
              direction="up";
            }else{
              direction="down";
            }
          }
        }

        if(!direction.isEmpty()){
          EventDispatcher eventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();

          WritableMap event = Arguments.createMap();
          event.putString("direction", direction);

          eventDispatcher.dispatchEvent(new ChartGestureEvent(chart.getId(), SystemClock.nanoTime(), event));
        }

      }

      @Override
      public void onChartScale(MotionEvent motionEvent, float v, float v1) {

      }

      @Override
      public void onChartTranslate(MotionEvent motionEvent, float v, float v1) {

      }
    });
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
        float yVal = .0f;
        switch(yValArray.getType(j)){
          case String: yVal = Float.valueOf(yValArray.getString(j)); break;
          case Number: yVal = (float) yValArray.getDouble(j); break;
        }

        Entry entry = new Entry(yVal,j);
        entries.add(entry);
      }

      String label = "";
      if(y.hasKey("label")) label = y.getString("label");
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


  @Nullable
  @Override
  public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.<String, Object>builder()
            .put("topChartGesture", MapBuilder.of("registrationName", "onChartGesture"))
            .build();
  }
}
