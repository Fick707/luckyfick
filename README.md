# 双色球

## 功能
每一期根据规则挑选出一个理想的投注；并发短信

## 已经梳理出的规则

### 红球
#### 历史数据
* 1000次，每个球平均出现181次，1000次时，基本平均；

#### 结论
* 近100期，出现>=30次的球，不再选；
* 近100期，出现<=8次的球，直接选；
* 连接出现6次，不再选；
* 连续缺失22次，直接选；
* 根据历史所有期的出现次数运算，得出每个球的权重，出现的越多，权重越小；
* 已经出现过的结果，直接放弃；

### 蓝球
#### 历史数据
* 1000次，每个球平均出现62.5次，1000次时，基本平均； 

#### 结论
* 近100期，出现超过15次的球，不再选；
* 近100期，出现少于1次的球，直接选；
* 连接出现4次，不再选；
* 连续缺失60次，直接选；
* 根据历史所有期次出现的次数运算，得出每个球的权重，出现的越多，权重越小；

### 总体
* 已经出现过的中一等奖的号码，不再选；
* 二等奖是否重复出现过：
      * 二等出现过，2次；分别相隔13年和10年；

### TODO
* 根据最近缺失次数，不放必选，但添加一定百分比出现次数；
* 根据最近N次，出现次数少于M，则加大随机比重；