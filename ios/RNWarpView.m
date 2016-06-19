
#import <Foundation/Foundation.h>

#import "RNWarpView.h"

@implementation RNWarpView

-(instancetype) initWithSubview:(id)subview
{
    self = [super init];
    if(self){
        [subview setFrame:CGRectMake(0, 0, 100, 100)];
        [self addSubview:subview];
        
        UISwipeGestureRecognizer* swipeGestureRecognizer = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(onChartGestureHandle:)];
        
        [self addGestureRecognizer:swipeGestureRecognizer];
    }
    return self;
}

-(void)onChartGestureHandle:(UISwipeGestureRecognizer*)swipeGestureRecognizer
{
    if(self.onChartGesture){
        self.onChartGesture(@{});
    }
}

-(void)layoutSubviews
{
    [self.subviews[0] setFrame:CGRectMake(0, 0, self.frame.size.width, self.frame.size.height)];
}

@end