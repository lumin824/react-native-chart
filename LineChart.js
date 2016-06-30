import React, { Component } from 'react';
import {
  Text,
  View,
  requireNativeComponent,
  processColor
} from 'react-native';

const LineChartNative = requireNativeComponent('LineChart');

export default class LineChart extends Component {

  processColors(data){
    if(!data) return data;

    data = JSON.parse(JSON.stringify(data));
    if(data.dataSet){
      data.dataSet = data.dataSet.map(o=>{
        if(o.colors && o.colors.length){
          console.log(o.colors);
          o.colors = o.colors.map(processColor);
        }
        return o;
      });
    }
    return data;
  }

  render(){
    let { style, onSwipe, data, ...otherProps } = this.props;
    data = this.processColors(data);
    let backgroundColor = style.backgroundColor || '#fff';
    return (
      <View style={[style,{backgroundColor}]}>
        <LineChartNative style={{flex:1,backgroundColor}} data={data} {...otherProps} onChartGesture={(e)=>onSwipe ? onSwipe(e.nativeEvent) :null} />
      </View>
    );
  }
}
