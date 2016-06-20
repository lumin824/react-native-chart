
#import <Foundation/Foundation.h>

#import "RNWarpView.h"

@implementation RNWarpView

-(instancetype) initWithSubview:(id)subview
{
    self = [super init];
    if(self){
        [self addSubview:subview];
        
        UISwipeGestureRecognizer* swipeGestureRecognizer = nil;
        
        swipeGestureRecognizer = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(onSwipeGestureHandle:)];
        swipeGestureRecognizer.direction = UISwipeGestureRecognizerDirectionRight;
        [self addGestureRecognizer:swipeGestureRecognizer];
        
        swipeGestureRecognizer = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(onSwipeGestureHandle:)];
        swipeGestureRecognizer.direction = UISwipeGestureRecognizerDirectionLeft;
        [self addGestureRecognizer:swipeGestureRecognizer];
        
        swipeGestureRecognizer = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(onSwipeGestureHandle:)];
        swipeGestureRecognizer.direction = UISwipeGestureRecognizerDirectionUp;
        [self addGestureRecognizer:swipeGestureRecognizer];
        
        swipeGestureRecognizer = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(onSwipeGestureHandle:)];
        swipeGestureRecognizer.direction = UISwipeGestureRecognizerDirectionDown;
        [self addGestureRecognizer:swipeGestureRecognizer];
        
    }
    return self;
}

-(void)onSwipeGestureHandle:(UISwipeGestureRecognizer*)swipeGestureRecognizer
{
    NSString* direction = @"";
    if(self.onChartGesture){
        switch(swipeGestureRecognizer.direction){
            case UISwipeGestureRecognizerDirectionRight: direction = @"right"; break;
            case UISwipeGestureRecognizerDirectionLeft: direction = @"left"; break;
            case UISwipeGestureRecognizerDirectionUp: direction = @"up"; break;
            case UISwipeGestureRecognizerDirectionDown: direction = @"down"; break;
        }
        self.onChartGesture(@{@"direction":direction});
    }
}

-(void)layoutSubviews
{
    [self.subviews[0] setFrame:CGRectMake(0, 0, self.frame.size.width, self.frame.size.height)];
}

@end