#import "RCTViewManager.h"
#import <Charts/Charts-Swift.h>

#import "RNWarpView.h"

@interface LineChartManager : RCTViewManager

@end

@implementation LineChartManager

RCT_EXPORT_MODULE()

//RCT_EXPORT_VIEW_PROPERTY(backgroundColor, UIColor);

RCT_REMAP_VIEW_PROPERTY(description, descriptionText, NSString);
RCT_EXPORT_VIEW_PROPERTY(onChartGesture, RCTDirectEventBlock)

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
        
        double val = [yValArray[j][@"val"] doubleValue];
        int xIdx = [yValArray[j][@"xIndex"] intValue];
      ChartDataEntry* entry = [[ChartDataEntry alloc]initWithValue:val xIndex:xIdx];
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
    LineChartView* chartView = view.subviews[0];
  [chartView setData:data];

}

RCT_CUSTOM_VIEW_PROPERTY(xAxis, NSDictionary, LineChartView)
{
    LineChartView* chartView = view.subviews[0];
    
    if([json objectForKey:@"labelsToSkip"]){
        int val = [[json valueForKey:@"labelsToSkip"] intValue];
        [chartView.xAxis setLabelsToSkip:val];
    }
    
//  NSString* position = json[@"position"];
//  NSArray* positionArray = [NSArray arrayWithObjects:@"bottom",@"top", nil];
//  switch ([positionArray indexOfObject:position]) {
//    case 0: view.xAxis.labelPosition = XAxisLabelPositionBottom; break;
//    case 1: view.xAxis.labelPosition = XAxisLabelPositionTop; break;
//  }
}

RCT_CUSTOM_VIEW_PROPERTY(leftAxis, NSDictionary, LineChartView)
{
    LineChartView* chartView = view.subviews[0];
    
    if([json objectForKey:@"axisMinValue"]){
        double val = [[json valueForKey:@"axisMinValue"] doubleValue];
        [chartView.leftAxis setAxisMinValue:val];
    }
    
    if([json objectForKey:@"axisMaxValue"]){
        double val = [[json valueForKey:@"axisMaxValue"] doubleValue];
        [chartView.leftAxis setAxisMaxValue:val];
    }
    
    if([json objectForKey:@"labelCount"]){
        int val = [[json valueForKey:@"labelCount"] intValue];
        [chartView.leftAxis setLabelCount:val];
    }
    
}

RCT_CUSTOM_VIEW_PROPERTY(legend, NSDictionary, LineChartView)
{
  LineChartView* chartView = view.subviews[0];
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

  ChartYAxis* leftAxis = view.leftAxis;
  [leftAxis resetCustomAxisMin];
  leftAxis.gridLineDashLengths = [NSArray arrayWithObject:[NSNumber numberWithFloat:2.0f]];
  leftAxis.valueFormatter = [[NSNumberFormatter alloc] init];
  leftAxis.valueFormatter.numberStyle = NSNumberFormatterDecimalStyle;
  leftAxis.spaceBottom = 0.1f;
  leftAxis.spaceTop = 0.1f;
  leftAxis.granularityEnabled = true;
  leftAxis.granularity = 1.0f;

  view.rightAxis.enabled = false;

  view.legend.enabled = false;

  view.scaleXEnabled = view.scaleYEnabled = false;
  view.dragEnabled = false;
  view.highlightPerDragEnabled = false;
  view.doubleTapToZoomEnabled = false;
  view.pinchZoomEnabled = false;
  view.descriptionText = @"";
    
  return [[RNWarpView alloc]initWithSubview:view];
}



@end
