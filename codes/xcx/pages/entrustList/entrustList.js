// pages/entrustList/entrustList.js
const httpUtil = require('../../utils/httpUtil.js')
const appParams = require('../../utils/appParams.js')

const app = getApp()
var that;
var accountId;
var httpUrl;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    listData:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.setNavigationBarTitle({
      title: '撤单'
    });
    that = this;
    httpUrl = appParams.queryMyEntrust;
    accountId = options.accountId;
    this.reloadData();
  },
  reloadData:function(){
    httpUtil.doPost({
      app: app,
      url: httpUrl,
      data: {
        accountId: "" + accountId
      },
      success: function (res) {
        console.log("返回:", res.data)
        that.setData({
          listData:res.data.data
        });
      }
    });
  },
  onCheDanClick:function(e){
    var entrustId=e.currentTarget.id;
    console.log("entrustId:" + entrustId);
    //发送撤单
    httpUtil.doPost({
      app: app,
      url: appParams.revokeMyEntrust,
      data: {
        entrustId: "" + entrustId
      },
      success: function (res) {
        console.log("返回:", res.data)
        that.reloadData();
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
  
  }
})