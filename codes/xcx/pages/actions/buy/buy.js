// pages/actions/buy/buy.js
const appParams = require('../../../utils/appParams.js')
const httpUtil = require('../../../utils/httpUtil.js')
const app = getApp()

var that;
var accountId;
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
    canStockNum:0,
    hodlerBaseInfo:{},//持仓的信息，包括可用资金和总资金
    listHolderData:[],//持仓列表
    stockDetail: {}//股票的详细信息
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // options.type//0:买入,1:卖出
    that = this;
    accountId = options.accountId;
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
        var holder = res.data.hodlerBaseInfo;
        // canStockNum
        // if(){}
        that.setData({
          hodlerBaseInfo: holder,
          listHolderData: res.data.listData
        });
        that.setCanStockNum();
      }
    });
    that.dataTimer();//执行定时器，定时加载股票信息
  },
  onHolderItemClick: function (e) {
    console.log(e);
    var item = that.data.listHolderData[e.currentTarget.id];
    that.setData({//直接给上移页面赋值
      selectStockCode: item.stockCode,
      selectStockCodeStr: item.stockCodeStr,
      selectStockName: item.stockName,
      inputPrice: item.nowPrice
    });
    that.setCanStockNum();
  },
  setCanStockNum: function (){
    if (that.data.uiType==0){//买入
      var holder = that.data.hodlerBaseInfo;
      var km = 0;
      if (that.data.inputPrice > 0) {
        km = parseInt(holder.canUseFund / that.data.inputPrice);
        if(km<100){
          km=0;
        }
      }
      that.setData({
        canStockNum: km
      });
    }else{//卖出
      var flag=false;
      var count=0;
      for (var i = 0; i < that.data.listHolderData.length;i++){
        if (that.data.listHolderData[i].stockCode == that.data.selectStockCode){
          flag=true;
          count = that.data.listHolderData[i].canUseNum;
        }
      }
        
      if (flag){
        that.setData({
          canStockNum: count
        });
      }


    }
    console.log("setCanStockNum:......");
    

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

        that.setData({
          stockDetail:res.data
        });
      }
    });
  },
  onSearchInputClick:function(e){
    if (that.data.uiType==0){
      console.log("点击");
      wx.navigateTo({
        url: '/pages/actions/searchStock/searchStock'
      });
    }
  },
  onBuyOrSellItemClick:function(e){
    var price = e.currentTarget.id;
    console.log("price:",price);
    that.setCanStockNum();
    this.setData({
      inputPrice: price
    });
  },
  alertMsg:function(msg){
    wx.showToast({
      title: '' + msg,
      icon: 'none',
      duration: 2000
    })
  },
  onBuyOrSellClick: function () {
    if (that.data.selectStockCode==null){//请选择股票
      that.alertMsg("请选择股票!");
    } else if (that.data.inputPrice == null || that.data.inputPrice<=0) {//请输入价格
      that.alertMsg("请选择价格!");
    } else if (that.data.inputNumber == null || that.data.inputNumber<=0) {//请输入数量
      that.alertMsg("请选择数量!");
    }else{
      this.setNumber(that.data.inputNumber);//设置修改输入的数值
      var urlStr = that.data.uiType == 0 ? appParams.entrustBuyStock : appParams.entrustSellStock

      httpUtil.doPost({
        app: app,
        url: urlStr,
        data: {
          accountId: accountId,
          stockCode: that.data.selectStockCode,
          stockCodeStr: that.data.selectStockCodeStr,
          stockName: that.data.selectStockName,
          entrustPrice: that.data.inputPrice,
          count: that.data.inputNumber
        },
        success: function (res) {
          // res.data
          

        }
      });
    }

    
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
  onBtnClick1:function(){//全仓
    that.chanageInputNumber(that.data.canStockNum);
  },
  onBtnClick2: function () {//半仓
    that.chanageInputNumber(that.data.canStockNum / 2);
  },
  onBtnClick3: function () {//1/3仓
    that.chanageInputNumber(that.data.canStockNum / 3);
  },
  onBtnClick4: function () {//1/4仓
    that.chanageInputNumber(that.data.canStockNum / 4);
  },
  chanageInputNumber:function(tem){
    // var tem = that.data.inputNumber;
    tem=parseInt(tem/100)*100;
    that.setData({
      inputNumber:tem
    });
    
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