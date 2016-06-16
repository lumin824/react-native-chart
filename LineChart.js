import React, { Component } from 'react';
import {
  Text,
  View,
  requireNativeComponent
} from 'react-native';

const LineChartNative = requireNativeComponent('LineChart');

export default class LineChart extends Component {
  render(){
    return (
      <LineChartNative {...this.props} />
    );
  }
}
