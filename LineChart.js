import React, { Component } from 'react';
import {
  Text,
  View,
  requireNativeComponent
} from 'react-native';

const LineChartNative = requireNativeComponent('LineChart');

export default class LineChart extends Component {
  render(){
    let { style, onSwipe, ...otherProps } = this.props;
    let backgroundColor = style.backgroundColor || '#fff';
    return (
      <View style={[style,{backgroundColor}]}>
        <LineChartNative style={{flex:1,backgroundColor}} {...otherProps} onChartGesture={(e)=>onSwipe ? onSwipe(e.nativeEvent) :null} />
      </View>
    );
  }
}
