/**
 * 页面传值: accountCode 账号id; stockCodeStr 股票代码(带有sh/sz); stockCode 股票代码; stockName 股票名称
 */
//获取应用实例
const appParams = require('../../utils/appParams.js')
const httpUtil = require('../../utils/httpUtil.js')

var category = require('../../utils/category.js');
const kxian = require('../../utils/kxian.js');
const shifen = require('../../utils/shifen.js');
var app = getApp();
var that;
var myTimer;
var canvasId_time = 'timeLine';
var canvasId_time1 = 'timeLine1';
var canvasId_time2 = 'timeLine2';
var canvasId_k = 'kchart';
var canvasId_k1 = 'kchart1';
var canvasId_k2 = 'timeLine2';
var canvasId_week = 'weeklychart';
var canvasId_week1 = 'weeklychart1';
var canvasId_week2 = 'timeLine2';
var canvasId_month = 'monthlychart';
var canvasId_month1 = 'monthlychart1';
var canvasId_month2 = 'timeLine2';
var point = 0.6;

var baseUrl="";
Page({
  data: {
    isTouch: false,
    windowWidth: 0,
    windowHeight: 0,
    imageWidth: 0,
    imageHeight: 180,
    imageKLineWidth: 0,
    klineViewWidth: 0,
    timeViewHeight: 36,
    klineViewHeight: 41,
    currentTab: 0,
    accountCode: '',
    shareTitle: '',
    shareDes: '',
    isAttention: 1,//是否已添加自选 0已加入;1未加入
    stockCodeStr: '', // 股票代码
    stockCode: '',
    stockData: {}, // 个股行情数据 
    urlStr: 'share',
    isIndex: false,
    sell5: [
      {
        price: "",
        name: "卖一",
        entrustCount: ""
      },
      {
        price: "",
        name: "卖二",
        entrustCount: ""
      },
      {
        price: "",
        name: "卖三",
        entrustCount: ""
      },
      {
        price: "",
        name: "卖四",
        entrustCount: ""
      },
      {
        price: "",
        name: "卖五",
        entrustCount: ""
      }
    ],
    buy5: [
      {
        price: "",
        name: "买一",
        entrustCount: ""
      },
      {
        price: "",
        name: "买二",
        entrustCount: ""
      },
      {
        price: "",
        name: "买三",
        entrustCount: ""
      },
      {
        price: "",
        name: "买四",
        entrustCount: ""
      },
      {
        price: "",
        name: "买五",
        entrustCount: ""
      }
    ],
    timeObj: {
      tOpen: 0,   // 昨收
      vals: []    // 分时数据
    },
    kLineObj: {
      tOpen: 0,
      vals: []
    },
    weeklyObj: {
      tOpen: 0,
      vals: []
    },
    monthlyObj: {
      tOpen: 0,
      vals: []
    }

  },
  onLoad: function (options) {
    baseUrl ="https://app.lhyone.com/";
    // 页面初始化 options为页面跳转所带来的参数
    that = this;
    that.setData({
      isShow: false
    })
    // 是否是分享 1为分享
    var channel = 1;


    // 获取当前账户信息
    // that.data.accountCode = category.transformNull(options.accountCode);
    // // that.data.urlStr = category.transformNull(options.urlStr);
    // if (category.transformNull(options.urlStr) == 'index') {
    //   that.data.urlStr = 'index';
    //   that.setData({ isIndex: true });
    //   point = 1;
    // } else {
    //   that.data.urlStr = 'share';
    //   point = 0.6;
    // }

    // 获取股票名称
    var stockName = category.transformNull(options.stockName);
    that.setData({
      shareTitle: stockName,
      shareDes: stockName + '的行情',
      stockCode: options.stockCode,
      stockCodeStr: options.stockCodeStr
    })
    var stockCode = category.transformNull(options.stockCode);

    var title;
    if (category.isNonNull(stockCode)) {
      title = stockName + '(' + stockCode + ')';
    } else {
      title = stockName;
    }
    // 设置nav
    wx.setNavigationBarTitle({
      title: title
    })


    // 获取屏幕宽度, 用来计算图片比例
    wx.getSystemInfo({
      success: function (res) {
        var windowWidth = res['windowWidth']
        var imageViewW = (windowWidth - 10) * point;
        var imageWidth = imageViewW;
        that.setData({
          klineViewWidth: windowWidth,
          klineViewHeight: that.data.klineViewHeight,
          timeViewHeight: that.data.timeViewHeight,
          imageWidth: imageWidth,
          imageKLineWidth: windowWidth - 10
        })
      }
    })

  },

  onShow: function () {
    // 页面显示
    if (app.globalData.isLoginBack) {
      that.requestMyAccountInfo();
      app.globalData.isLoginBack = false;
    }

    that.requestStockDetailsInfo();
    // k线数据
    that.requestTimeLine();
    that.requestKline();
    that.requestWeeklyLine();
    that.requestMonthlyLine();
    // 设置定时器 每30秒请求一次数据
    myTimer = setInterval(function () {
      that.requestStockDetailsInfo()
      // k线图数据
      that.requestTimeLine();
    }, 30000);
  },
  onHide: function () {
    // 页面隐藏
    // 关闭定时器
    myTimer = clearInterval(myTimer);
    console.log(myTimer);
  },
  onUnload: function () {
    // 页面关闭
    // 关闭定时器
    myTimer = clearInterval(myTimer);
    console.log(myTimer);
  },

  onShareAppMessage: function () {
    // 用户点击右上角分享
    return {
      title: that.data.shareTitle, // 分享标题
      desc: that.data.shareDes, // 分享描述 
      path: '/pages/details/stockDetail/stockDetail?isShare=1&channel=shareStockDetail&stockCodeStr=' + that.data.stockCodeStr + '&stockCode=' + that.data.stockCode + '&stockName=' + that.data.shareTitle // 分享路径
    }
  },
  /**
   * K线图 touch
   */
  chartTouchstart: function () {
    console.log('触摸事件start:');
    that.setData({
      isTouch: true
    })
  },
  chartTouchend: function () {//手指离开
    console.log('触摸事件end:');
    var tab = that.data.currentTab;
    if (tab == 0) {
      // 分时
      shifen.clearCanvas(canvasId_time1);
    }
    if (tab == 1) {
      // 日K
      kxian.clearCanvas(canvasId_k1);
    }
    if (tab == 2) {
      // 周K
      kxian.clearCanvas(canvasId_week1);
    }
    if (tab == 3) {
      // 月K
      kxian.clearCanvas(canvasId_month1);
    }
    that.setData({
      isTouch: false
    })
  },
  longTap: function (e) {

  },
  chartTouchmove: function (e) {//触摸移动
    var tab = that.data.currentTab;
    if (tab == 0) {
      // 分时 十字线
      var cur_timeObj = shifen.drawCross(canvasId_time1, that.data.imageWidth, that.data.imageHeight, that.data.timeObj.tOpen, that.data.timeObj.vals, e.touches[0].x, e.touches[0].y);
      // 分时 信息
      if (category.isNonNull(cur_timeObj)) {
        shifen.drawStockInfo(canvasId_time2, that.data.klineViewWidth, that.data.timeViewHeight, cur_timeObj);
      }

    } else if (tab == 1) {
      // 日K 十字线
      var cur_klineObj = kxian.drawCross(canvasId_k1, that.data.imageKLineWidth, that.data.imageHeight, that.data.kLineObj.tOpen, that.data.kLineObj.vals, e.touches[0].x, e.touches[0].y);
      // 日K 信息
      if (category.isNonNull(cur_klineObj)) {
        kxian.drawStockInfo(canvasId_k2, that.data.klineViewWidth, that.data.klineViewHeight, cur_klineObj);
      }

    } else if (tab == 2) {
      // 周K
      var cur_weeklyObj = kxian.drawCross(canvasId_week1, that.data.imageKLineWidth, that.data.imageHeight, that.data.weeklyObj.tOpen, that.data.weeklyObj.vals, e.touches[0].x, e.touches[0].y);
      // 周K 信息
      if (category.isNonNull(cur_weeklyObj)) {
        kxian.drawStockInfo(canvasId_week2, that.data.klineViewWidth, that.data.klineViewHeight, cur_weeklyObj);
      }

    } else if (tab == 3) {
      // 月K
      var cur_monthlyObj = kxian.drawCross(canvasId_month1, that.data.imageKLineWidth, that.data.imageHeight, that.data.monthlyObj.tOpen, that.data.monthlyObj.vals, e.touches[0].x, e.touches[0].y);
      // 月K 信息
      if (category.isNonNull(cur_monthlyObj)) {
        kxian.drawStockInfo(canvasId_month2, that.data.klineViewWidth, that.data.klineViewHeight, cur_monthlyObj);
      }

    }
  },

  // 分时数据
  requestTimeLine: function () {
    that.data.stockCode = that.data.stockCode.replace(/sz/, "");
    that.data.stockCode = that.data.stockCode.replace(/sh/, "");
    var url = baseUrl+'stockapi/today/js/' + that.data.urlStr + '/' + that.data.stockCode + '.js'
    httpUtil.doShifen({
      url: url,
      success: function (res) {
        var data = res.data;
        var tOpen = data.tOpen;
        if (data) {
          var list = data.Data;
          if (list instanceof Array) {

          } else {
            list = [];
          }
          var timeVal = [];
          for (var i = 0; i < list.length; i++) {
            // 数组中的数组
            var dataItem = list[i];
            var timeItem = { price: dataItem[1], time: dataItem[0], tranPrice: dataItem[3], vol: dataItem[2] };
            // 时间格式转换
            var time = dataItem[0].toString();
            if (time.length == 3) {
              var hh = time.slice(0, 1);
              var mm = time.slice(1);
              timeItem.timeStr = hh + ":" + mm;
            }
            if (time.length == 4) {
              var hh = time.slice(0, 2);
              var mm = time.slice(2);
              timeItem.timeStr = hh + ":" + mm;
            }

            // 交易量
            var traNumber = dataItem[2] / 100;
            timeItem.tradeNumStr = category.transformUnit(traNumber);
            if (category.isNonNull(tOpen)) {
              // 涨跌幅
              tOpen = parseFloat(tOpen);
              var changeRate = dataItem[1] / tOpen - 1;
              timeItem.changeRate = changeRate;
              timeItem.changeRateStr = category.transformPercent(changeRate);
              if (changeRate > 0) {
                timeItem.changeRateStr = "+" + timeItem.changeRateStr;
              }
            }
            var temCount = 0;
            for (var j = 0; j <= i; j++) {
              var priceItem = list[j];
              temCount += priceItem[1];
            }
            var average = temCount / (i + 1);
            timeItem.average = category.transformDecimal(average)
            timeItem.averageDiff = average - tOpen;

            timeVal.push(timeItem);
          }

          that.setData({
            timeObj: {
              tOpen: data.tOpen,
              vals: timeVal
            }
          })

          // 分时线
          shifen.onDraw(canvasId_time, that.data.imageWidth, that.data.imageHeight, that.data.timeObj.tOpen, that.data.timeObj.vals);

        }
      }
    });
  },

  requestKline: function () {
    that.data.stockCode = that.data.stockCode.replace(/sz/, "");
    that.data.stockCode = that.data.stockCode.replace(/sh/, "");
    var url = baseUrl+'stockapi/history/js/' + that.data.urlStr + '/' + that.data.stockCode + '/day.js';
    
    httpUtil.doKline({
      app: app,
      url: url,
      data: {},
      success: function (res) {
        // console.log(res.data)

        var data = res.data;
        if (data) {
          var list = data.rows
          if (list instanceof Array) {

          } else {
            list = [];
          }
          var klineVal = that.cal_kLineMA(list);
          
          that.setData({
            kLineObj: {
              tOpen: klineVal[0].yesClosePrice,
              vals: klineVal
            }
          })

          // 绘制日K
          kxian.onDraw(canvasId_k, that.data.imageKLineWidth, that.data.imageHeight, that.data.kLineObj.tOpen, that.data.kLineObj.vals);

        }
      }
    });

  },
  requestWeeklyLine: function () {
    that.data.stockCode = that.data.stockCode.replace(/sz/, "");
    that.data.stockCode = that.data.stockCode.replace(/sh/, "");
    var url = baseUrl+'stockapi/history/js/' + that.data.urlStr + '/' + that.data.stockCode + '/week.js';

    httpUtil.doKline({
      app: app,
      url: url,
      data: {},
      success: function (res) {
        // console.log(res.data)
        var data = res.data;
        if (data) {
          var list = data.rows;
          if (list instanceof Array) {

          } else {
            list = [];
          }
          var klineVal = that.cal_kLineMA(list);

          that.setData({
            weeklyObj: {
              tOpen: klineVal[0].yesClosePrice,
              vals: klineVal
            }
          })
          // 绘制周K
          kxian.onDraw(canvasId_week, that.data.imageKLineWidth, that.data.imageHeight, that.data.weeklyObj.tOpen, that.data.weeklyObj.vals);
        }
      }
    });

  },
  requestMonthlyLine: function () {
    that.data.stockCode = that.data.stockCode.replace(/sz/, "");
    that.data.stockCode = that.data.stockCode.replace(/sh/, "");
    var url = baseUrl+'stockapi/history/js/' + that.data.urlStr + '/' + that.data.stockCode + '/month.js';
    httpUtil.doKline({
      app: app,
      url: url,
      data: {},
      success: function (res) {
        // console.log(res.data)
        var data = res.data;
        if (data) {
          var list = data.rows;
          if (list instanceof Array) {

          } else {
            list = [];
          }
          var klineVal = that.cal_kLineMA(list);
          that.setData({
            monthlyObj: {
              tOpen: klineVal[0].yesClosePrice,
              vals: klineVal
            }
          })
          // 月K
          kxian.onDraw(canvasId_month, that.data.imageKLineWidth, that.data.imageHeight, that.data.monthlyObj.tOpen, that.data.monthlyObj.vals);
        }
      }
    });
   
  },

  // 统一处理 日k / 周K / 月K 接口数据
  cal_kLineMA: function (list) {
    var klineVal = new Array();
    for (var i = 0; i < list.length; i++) {
      var dataItem = list[i];
      var item = { closePrice: dataItem[2], date: dataItem[0], maxPrice: dataItem[3], minPrice: dataItem[4], openPrice: dataItem[1], tradeNum: dataItem[5], tradePrice: dataItem[6], yesClosePrice: dataItem[7] };
      // 开/收/高/低 与昨收比较
      item.openDiff = dataItem[1] - dataItem[7];
      item.closeDiff = dataItem[2] - dataItem[7];
      item.maxDiff = dataItem[3] - dataItem[7];
      item.minDiff = dataItem[4] - dataItem[7];
      // 交易量
      var traNumber = dataItem[5];
      item.tradeNumStr = category.transformUnit(traNumber);
      // 涨跌幅
      var changeRate = dataItem[2] / dataItem[7] - 1;
      item.changeRate = changeRate;
      item.changeRateStr = category.transformPercent(changeRate);
      if (changeRate > 0) {
        item.changeRateStr = "+" + item.changeRateStr;
      }
      var change = dataItem[2] - dataItem[7];
      item.change = change;
      item.changeStr = category.transformDecimal(change);
      if (change > 0) {
        item.changeStr = "+" + item.changeStr;
      }
      // 振幅
      var zRate = (dataItem[3] - dataItem[4]) / dataItem[7];
      item.zRate = zRate;
      item.zRateStr = category.transformPercent(zRate);

      // 计算MA5
      if (i >= 4) {
        var temCount = 0;
        for (var j = 0; j < 5; j++) {
          var closePrice = list[i - j];
          temCount += closePrice[2];
        }
        var ma5 = category.transformDecimal(temCount / 5);
        var ma5_str = "MA5: " + ma5.toString();
        item.ma5 = ma5_str;
      }
      // 计算MA10
      if (i >= 9) {
        var temCount = 0;
        for (var j = 0; j < 10; j++) {
          var closePrice = list[i - j];
          temCount += closePrice[2];
        }
        var ma10 = category.transformDecimal(temCount / 10);
        var ma10_str = "MA10: " + ma10.toString();
        item.ma10 = ma10_str;
      }
      // 计算MA20
      if (i >= 19) {
        var temCount = 0;
        for (var j = 0; j < 20; j++) {
          var closePrice = list[i - j];
          temCount += closePrice[2];
        }
        var ma20 = category.transformDecimal(temCount / 20);
        var ma20_str = "MA20: " + ma20.toString();
        item.ma20 = ma20_str;
      }
      klineVal.push(item);
    }
    return klineVal;
  },

  /**
   * 请求我的账户信息
   */
  requestMyAccountInfo: function () {
    // util.showTheLoading();

  },




  /**
   * 切换tab
   */
  switchNav: function (e) {
    console.log('个股行情切换tab:', e.target.dataset.current);
    if (that.data.currentTab == e.target.dataset.current) {
      return false;
    } else {
      that.setData({
        currentTab: e.target.dataset.current
      })
    }
  },
  swiperChange: function (e) {
    that.setData({
      currentTab: e.detail.current
    });
  },

  /**
   * 请求个股行情
   * stockCodeStr: 股票代码
   */
  requestStockDetailsInfo: function () {
    wx.showNavigationBarLoading();
    var stockCodeStr = category.transformNull(that.data.stockCodeStr);
    httpUtil.doPost({
      app: app,
      url: appParams.queryStockInfoByCode,
      data: {
        stockCode: stockCodeStr
      },
      success: function (res) {
        console.log(res.data);

        var data = res.data;
        if (data) {
          wx.hideNavigationBarLoading();
          console.log("返回:" + data);
          data.nowPrice = data.lastPx;
          // 今开
          data.todayStartPri = category.transformDecimal(data.openPx, 2)
          // 昨收
          data.yestodEndPri = category.transformDecimal(data.preClosePx, 2);
          // 差值
          var currentChange = parseFloat(data.lastPx) - parseFloat(data.preClosePx);
          data['currentChangeNum'] = parseFloat(currentChange);
          data['currentChange'] = category.transformDecimal(data['currentChangeNum'], 2);
          if (data.currentChangeNum > 0) {
            data.currentChange = '+' + data.currentChange;
          }
          // 涨跌幅
          var changeRate = parseFloat(data.lastPx) / parseFloat(data.preClosePx) - 1;
          data['changeRate'] = category.transformPercent(changeRate);
          if (changeRate > 0) {
            data.changeRate = '+' + data.changeRate;
          }
          // 成交量
          var traNumber = data.tradeVolume / 100;
          data.traNumber = category.transformUnit(traNumber);

          // 成交额
          data.traAmount = category.transformUnit(data.tradeValue);
          // 最高
          data.todayMax = category.transformDecimal(data.highPx);
          // 最低
          data.todayMin = category.transformDecimal(data.lowPx);
          // 卖5
          var tradeSell5 = data.sell;
          for (var i = 0; i < tradeSell5.length; i++) {
            var item = tradeSell5[i];
            item.price = category.transformDecimal(item.tradePx, 3);
            item.entrustCount = parseInt(item.tradeNum / 100);
            var dValue = parseFloat(item.tradePx) - parseFloat(data.yestodEndPri);
            item['dValue'] = dValue;
          }
          // 买5
          var tradeBuy5 = data.buy;
          for (var i = 0; i < tradeBuy5.length; i++) {
            var item = tradeBuy5[i];
            item.price = category.transformDecimal(item.tradePx, 3);
            item.entrustCount = parseInt(item.tradeNum / 100);
            var dValue = parseFloat(item.tradePx) - parseFloat(data.yestodEndPri);
            item['dValue'] = dValue;
          }
          that.setData({
            // isAttention: data.isAttention,
            stockData: data,
            buy5: data.buy,
            sell5: data.sell
          })
        }
      }
    });
  },
  // 买入 / 卖出
  buyAction: function () {
    console.log("点击买入");
  },
  sellAction: function () {
    console.log("点击卖出");
  }
})