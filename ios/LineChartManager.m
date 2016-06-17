#import "LineChartManager.h"

#import <Charts/Charts-Swift.h>

@implementation LineChartManager

RCT_EXPORT_MODULE()

//RCT_EXPORT_VIEW_PROPERTY(backgroundColor, UIColor);

RCT_REMAP_VIEW_PROPERTY(description, descriptionText, NSString);

RCT_CUSTOM_VIEW_PROPERTY(data, NSDictionary, LineChartView)
{
  NSDictionary* map = (NSDictionary*) json;
  NSArray* xArray = map[@"xVals"];
  LineChartData* data = [[LineChartData alloc]initWithXVals:xArray];

  NSArray* yArray = map[@"dataSet"];

  for(NSDictionary* y in yArray){
    NSArray* yValArray = y[@"yVals"];

    NSMutableArray<ChartDataEntry*> * entries = [[NSMutableArray alloc] init];
    for(int j = 0; j < yValArray.count; j++){

      double val = [[yValArray objectAtIndex:j] doubleValue];
      ChartDataEntry* entry = [[ChartDataEntry alloc]initWithValue:val xIndex:j];
      [entries addObject:entry];
    }

    NSString* label = y[@"label"];
    LineChartDataSet* dataset = [[LineChartDataSet alloc] initWithYVals:entries label:label];

    NSArray* colorsArray = y[@"colors"];
    if(colorsArray != nil){
      NSMutableArray* colors = [[NSMutableArray alloc]init];
      for(int i = 0; i < colorsArray.count; i++)
      {
        colors[i]  = [RCTConvert UIColor: colorsArray[i] ];
      }
      dataset.colors = colors;
    }
    dataset.drawValuesEnabled = false;
    dataset.circleRadius = 2;
    dataset.drawCircleHoleEnabled = false;
    [data addDataSet:dataset];
  }
  [view setData:data];

}

RCT_CUSTOM_VIEW_PROPERTY(xAxis, NSDictionary, LineChartView)
{
//  NSString* position = json[@"position"];
//  NSArray* positionArray = [NSArray arrayWithObjects:@"bottom",@"top", nil];
//  switch ([positionArray indexOfObject:position]) {
//    case 0: view.xAxis.labelPosition = XAxisLabelPositionBottom; break;
//    case 1: view.xAxis.labelPosition = XAxisLabelPositionTop; break;
//  }
}

RCT_CUSTOM_VIEW_PROPERTY(legend, NSDictionary, LineChartView)
{
  ChartLegend* legend = view.legend;
  id enabled = json[@"enabled"];
  if(enabled) legend.enabled = [enabled boolValue];
}
- (UIView *)view
{

  LineChartView* view = [[LineChartView alloc] init];

  view.xAxis.labelPosition = XAxisLabelPositionBottom;
  view.xAxis.drawAxisLineEnabled = false;
  view.xAxis.drawGridLinesEnabled = false;

  view.leftAxis.startAtZeroEnabled = false;
  view.leftAxis.gridLineDashLengths = [NSArray arrayWithObject:[NSNumber numberWithFloat:2.0f]];
  view.leftAxis.valueFormatter = [[NSNumberFormatter alloc] init];
  view.leftAxis.valueFormatter.numberStyle = NSNumberFormatterDecimalStyle;

  view.rightAxis.enabled = false;

  view.legend.enabled = false;

  view.scaleXEnabled = view.scaleYEnabled = false;
  view.dragEnabled = false;
  view.highlightPerDragEnabled = false;
  view.doubleTapToZoomEnabled = false;
  view.pinchZoomEnabled = false;
  view.backgroundColor = UIColor.redColor;
  view.descriptionText = @"";

  return view;
  //return [[MKMapView alloc] init];
}

@end
