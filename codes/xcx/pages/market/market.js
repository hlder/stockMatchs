// pages/market/market.js
const httpUtil = require('../../utils/httpUtil.js')
const appParams = require('../../utils/appParams.js')
const category = require('../../utils/category.js')
// const encoding = require('../../utils/encoding.js')

const app = getApp()
var that;

Page({

  /**
   * 页面的初始数据
   */
  data: {
    stockIndexs: [{}],
    stockUpRank:[],
    stockDownRank:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    that=this;
  },
  //指数的item的点击
  onIndexClick:function(e){
    var index = e.currentTarget.id;
    var temObj = that.data.stockIndexs[index];
    console.log("点击:", temObj);
    wx.navigateTo({
      url: '/pages/stockDetail/stockDetail?stockCode=' + temObj.codeStr + '&stockCodeStr=' + temObj.codeStr + '&&stockName=' + temObj.name+"&isIndex=true"
    })
  },
  //涨跌幅item的点击
  onDownItemClick: function (e) {
    var index = e.currentTarget.id;
    var temObj = that.data.stockDownRank[index];
    wx.navigateTo({
      url: '/pages/stockDetail/stockDetail?stockCode=' + temObj.code + '&stockCodeStr=' + temObj.symbol + '&&stockName=' + temObj.name
    })
  },
  onUpItemClick: function (e) {
    var index = e.currentTarget.id;
    var temObj = that.data.stockUpRank[index];
    wx.navigateTo({
      url: '/pages/stockDetail/stockDetail?stockCode=' + temObj.code + '&stockCodeStr=' + temObj.symbol + '&&stockName=' + temObj.name
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    httpUtil.doPost({
      app: app,
      url: appParams.urlSinaStockIndexList,
      data: {
      },
      fail: function (e) {
        wx.hideLoading()
      },
      success: function (res) {
        wx.hideLoading()
        var data=that.doStockIndex(res.data);
        that.setData({
          stockIndexs: data
        });
      }
    });

    httpUtil.doPost({
      app: app,
      url: appParams.urlSinaStockUpRanking,
      data: {},
      success: function (res) {
        var obj = res.data.replace(/(\s*?{\s*?|\s*?,\s*?)(['"])?([a-zA-Z0-9]+)(['"])?:/g, '$1"$3":');
        that.setData({
          stockUpRank: JSON.parse(obj)
        });
      }
    });
    httpUtil.doPost({
      app: app,
      url: appParams.urlSinaStockDownRanking,
      data: {},
      success: function (res) {
        var obj = res.data.replace(/(\s*?{\s*?|\s*?,\s*?)(['"])?([a-zA-Z0-9]+)(['"])?:/g, '$1"$3":');
        that.setData({
          stockDownRank: JSON.parse(obj)
        });
        
      }
    });
  },
  doStockIndex: function (data) {
    var reArr=[];

    var tem = data.split(";\n");
    for (var i = 0; i < tem.length; i++) {
      var str = tem[i];
      if (str == null || str == "") {
        break;
      }
      var temArr = str.split("\"");
      var arr = temArr[1].split(",");

      var codeStr = temArr[0].split("hq_str_")[1].replace("=", "");//股票代码
      var name = arr[0];//股票名称
      var price = arr[3];//当前价格
      var yesPrice = arr[2];//昨收

      var reData = {};
      reData.codeStr = codeStr;
      reData.name = name;
      reData.price = parseFloat(price);
      reData.yesPrice = parseFloat(yesPrice);
      reData.income = category.transformDecimal(reData.price - reData.yesPrice);
      reData.incomeRate = category.transformPercent((reData.price - reData.yesPrice) / reData.yesPrice);
      reArr[i] = reData;
    }
    console.log(reArr);
    return reArr;
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
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