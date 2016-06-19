
#import <UIKit/UIKit.h>
#import "RCTComponent.h"

@interface RNWarpView : UIView

@property(nonatomic, copy) RCTDirectEventBlock onChartGesture;

-(instancetype) initWithSubview:(UIView*) subview;

@end