package com.lumin824.chart;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * Created by lumin on 16/6/16.
 */
public class ChartGestureEvent extends Event<ChartGestureEvent> {
    private WritableMap event;
    public ChartGestureEvent(int viewTag, long timestampMs, WritableMap event) {
        super(viewTag, timestampMs);
        this.event = event;
    }

    @Override
    public String getEventName() {
        return "topChartGesture";
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), this.event);
    }
}
