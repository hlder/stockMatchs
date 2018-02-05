// pages/holder/holder.js
const appParams = require('../../utils/appParams.js')
const httpUtil = require('../../utils/httpUtil.js')
const app = getApp()

var that;

Page({
  /**
   * 页面的初始数据
   */
  data: {
    hodlerBaseInfo:{},
    listData:[],
    selectIndex:-1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    that = this;

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
          listData: res.data.listData
        });
      }
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
  
  },
  onItemClick:function(e){
    // e.currentTarget.id
    var temSelectId=-1;
    if (this.data.selectIndex == e.currentTarget.id){
      temSelectId=-1;
    }else{
      temSelectId = e.currentTarget.id;
    }
    this.setData({
      selectIndex: temSelectId
    });
  },
  toStockDetail:function(e){
    var temIndex = e.currentTarget.id;
    var temObj = this.data.listData[temIndex];
    wx.navigateTo({
      url: '/pages/stockDetail/stockDetail?stockCode=' + temObj.stockCode + '&stockCodeStr=' + temObj.stockCodeStr + '&&stockName=' + temObj.stockName
    })
  }
})