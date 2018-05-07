// pages/leaderList/leaderList.js
const appParams = require('../../utils/appParams.js')
const httpUtil = require('../../utils/httpUtil.js')
const category = require('../../utils/category.js')
const app = getApp()
var that;
var accountId;
var matchId;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    data:{}
  },
  onUserItemClick: function (e) {
    wx.navigateTo({
      url: '/pages/userInfo/userInfo?matchId=' + matchId + '&accountId=' + accountId + '&leaderAccountId=' + e.currentTarget.id
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    that=this;
    accountId = options.accountId;
    matchId = options.matchId;

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

    httpUtil.doPost({
      app: app,
      url: appParams.queryMyLeaders,
      data: {
        accountId: 15,
        matchId: 1
      },
      fail: function (e) {
        console.log("登录失败:-------------------------------------")
        console.log(e)
      },
      success: function (res) {
        console.log("登录成功:", res.data)
        var reqData = res.data.data;
        if (reqData.myLeaders != null) {
          for (var i = 0; i < reqData.myLeaders.length; i++) {
            reqData.myLeaders[i].total_income_rate = category.transformPercent(reqData.myLeaders[i].total_income_rate);
          }
        }
        if (reqData.otherLeaders != null) {
          for (var i = 0; i < reqData.otherLeaders.length; i++) {
            reqData.otherLeaders[i].total_income_rate = category.transformPercent(reqData.otherLeaders[i].total_income_rate);
          }
        }
        that.setData({
          data: res.data.data
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