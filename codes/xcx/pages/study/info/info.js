// pages/study/info/info.js

const appParams = require('../../../utils/appParams.js')
const httpUtil = require('../../../utils/httpUtil.js')
const app = getApp()
var that;

Page({

  /**
   * 页面的初始数据
   */
  data: {
    htmlVal:""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    that=this;
    httpUtil.doPost({
      app: app,
      url: appParams.queryClassInfoById,
      data: {
        id: options.id + ""
      },
      success: function (res) {
        if (res.data != null && res.data.data!=null){
          that.setData({
            htmlVal: res.data.data.text
          });
        }
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