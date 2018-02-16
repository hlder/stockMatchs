// pages/actions/buy/buy.js
const appParams = require('../../../utils/appParams.js')
const httpUtil = require('../../../utils/httpUtil.js')
const app = getApp()

var that;
Page({
  /**
   * 页面的初始数据
   */
  data: {
    selectStockCode: null,
    selectStockCodeStr: null,
    selectStockName: null,

    inputPrice:null,
    inputNumber:null,

    uiType:0,
    listHolderData:[],//持仓列表
    stockDetail: {}//股票的详细信息
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
    if (that.data.selectStockCodeStr == null) {
      return;
    }
    httpUtil.doPost({
      app: app,
      url: appParams.queryStockInfoByCode,
      data: {
        stockCode: that.data.selectStockCodeStr
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
  onSearchInputClick:function(e){

    console.log("点击");
    wx.navigateTo({
      url: '/pages/actions/searchStock/searchStock'
    });
  },
  onBuyOrSellClick: function () {
    this.setNumber(that.data.inputNumber);//设置修改输入的数值

    var urlStr = that.data.uiType == 0 ? appParams.entrustBuyStock : appParams.entrustSellStock

    httpUtil.doPost({
      app: app,
      url: urlStr,
      data: {
      },
      success: function (res) {
        // res.data
      }
    });
  },
  onPriceInput: function (e) {
    console.log("" + e.detail.value);
  },
  onPriceCutClick:function(){//价格减按钮
    console.log("onPriceCutClick");
    var inputPrice = that.data.inputPrice;
    if (inputPrice==null){
      inputPrice=0;
    }else{
      inputPrice = parseFloat(inputPrice);
    }
    inputPrice = inputPrice-0.01;
    if (inputPrice<0){
      inputPrice=0;
    }
    that.setData({
      inputPrice: inputPrice
    });
  },
  onPriceAddClick: function () {//价格加按钮
    console.log("onPriceAddClick");
    var inputPrice = that.data.inputPrice;
    if (inputPrice == null) {
      inputPrice = 0;
    } else {
      inputPrice = parseFloat(inputPrice);
    }
    inputPrice = inputPrice + 0.01;
    if (inputPrice < 0) {
      inputPrice = 0;
    }
    that.setData({
      inputPrice: inputPrice
    });
  },
  setNumber:function(value){
    value = parseInt(value);
    value = parseInt(value / 100) * 100;
    console.log("setNumber:" + value);
    that.setData({
      inputNumber: value
    });
  },
  onNumberInput:function(e){
    console.log("" + e.detail.value);
    that.data.inputNumber = e.detail.value;
    // that.setNumber(e.detail.value);
  },
  onNumberCutClick: function () {//数量减按钮
    console.log("onNumberCutClick:" + that.data.inputNumber);
    var inputNumber = that.data.inputNumber;
    if (inputNumber == null) {
      inputNumber = 0;
    } else {
      inputNumber = parseFloat(inputNumber);
    }
    inputNumber = inputNumber - 100;
    if (inputNumber < 0) {
      inputNumber = 0;
    }
    that.setNumber(inputNumber);
  },
  onNumberAddClick: function () {//数量增按钮
    console.log("onNumberAddClick");
    var inputNumber = that.data.inputNumber;
    if (inputNumber == null) {
      inputNumber = 0;
    } else {
      inputNumber = parseFloat(inputNumber);
    }
    inputNumber = inputNumber + 100;
    if (inputNumber < 0) {
      inputNumber = 0;
    }
    that.setNumber(inputNumber);
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
    this.loadBuyAndSell5();
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