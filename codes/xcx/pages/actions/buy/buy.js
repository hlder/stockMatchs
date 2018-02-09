// pages/actions/buy/buy.js
const appParams = require('../../../utils/appParams.js')
const httpUtil = require('../../../utils/httpUtil.js')
const app = getApp()

var that;
var stockCodeStr=null;
Page({
  /**
   * 页面的初始数据
   */
  data: {
    uiType:0,
    listHolderData:[],//持仓列表
    stockDetail:{}//股票的详细信息
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // options.type//0:买入,1:卖出
    that = this;
    this.setData({
      uiType: options.type
    });
    
    wx.setNavigationBarTitle({
      title: that.data.uiType==0?"买入":"卖出"
    });
    stockCodeStr = options.stockCodeStr;
    //查询持仓
    httpUtil.doPost({
      app: app,
      url: appParams.queryMyHolderInfos,
      data: {
        accountId: '' + options.accountId
      },
      success: function (res) {
        console.log(res.data)
        that.setData({
          hodlerBaseInfo: res.data.hodlerBaseInfo,
          listHolderData: res.data.listData
        });
      }
    });
    that.dataTimer();//执行定时器，定时加载股票信息
  },
  dataTimer: function () {//定时器，定时加载股票信息
    that.loadBuyAndSell5();
    setTimeout(that.dataTimer, 5000);
  },
  loadBuyAndSell5:function(){
    if (stockCodeStr == null) {
      return;
    }
    httpUtil.doPost({
      app: app,
      url: appParams.queryStockInfoByCode,
      data: {
        stockCode: stockCodeStr
      },
      success: function (res) {
        // res.data
        res.data.preClosePx = parseFloat(res.data.preClosePx);
        res.data.dieting = res.data.preClosePx - res.data.preClosePx * 0.1;
        res.data.zhangting = res.data.preClosePx + res.data.preClosePx * 0.1;
        res.data.dieting = res.data.dieting.toFixed(2);
        res.data.zhangting = res.data.zhangting.toFixed(2);

        console.log(res.data)
        that.setData({
          stockDetail:res.data
        });
      }
    });
  },
  onBuyOrSellClick:function(){

  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})