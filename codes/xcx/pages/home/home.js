// pages/applyMatch/applyMatch.js
const httpUtil = require('../../utils/httpUtil.js')
const appParams = require('../../utils/appParams.js')

const app = getApp()
var that;

Page({

  /**
   * 页面的初始数据
   */
  data: {
    banners: null,
    buttons:null,
    matchInfo:null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    that=this;
    httpUtil.doPost({
      app: app,
      url: appParams.queryHomeInfo,
      data: {
        matchId:"1",
        accountId:"1"
      },
      success: function (res) {
        console.log("返回:", res.data)
        that.setData({
          banners: JSON.parse(res.data.data.banners),
          buttons: JSON.parse(res.data.data.buttons),
          matchInfo: res.data.data
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

  }
})