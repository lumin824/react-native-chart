#import "LineChartManager.h"

#import <MapKit/MapKit.h>

@implementation LineChartManager

RCT_EXPORT_MODULE()

-(UIView* )view
{
    return [[MKMapView alloc] init];
}

@end
