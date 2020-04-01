# 双色球

## 功能
每一期根据规则挑选出一个理想的投注；并发短信

## 已经梳理出的规则

### 红球
* 近100期，出现>=30次的球，不再选；
* 近100期，出现<=8次的球，直接选；
* 根据历史所有期的出现次数运算，得出每个球的权重，出现的越多，权重越小；
* 已经出现过的结果，直接放弃；

### 蓝球
* 近100期，出现超过15次的球，不再选；
* 近100期，出现少于2次的球，直接选；
* 根据历史所有期次出现的次数运算，得出每个球的权重，出现的越多，权重越小；

### TODO
* 实现：获取近N次开奖中，各个球号的出现频次，分红、蓝
* 实现：获取近N次开奖中，指定球号的出现频次，分红、蓝