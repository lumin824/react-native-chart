# react-native-chart

## 连接项目
rnpm link

## ios 配置
1. Other linker flags 增加-framework Charts
2. Framework Search Paths 增加 $(SRCROOT)/.../node_modules/react-native-chart/ios
3. Embeded Binaries 增加 Charts.framework
4. Enbedded Content Contains Swift Code 设为 YES
