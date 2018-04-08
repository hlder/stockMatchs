// pages/study/classList.js
const appParams = require('../../utils/appParams.js')
const httpUtil = require('../../utils/httpUtil.js')
const app = getApp()

var that;
var matchId;
Page({

  /**
   * 页面的初始数据
   */
  data: {
  
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    that = this;
    matchId = options.matchId;
  
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
    console.log(e);
    wx.navigateTo({
      url: 'list/list?mold=' + e.currentTarget.id + "&title=" + e.currentTarget.dataset.name
    })
  },
  getUserInfo: function () {
    wx.login({
      success: function (resLogin) {
        wx.getUserInfo({
          withCredentials: true,
          success: res => {
            app.globalData.userInfo = res.userInfo
            that.onLoadUserSuccess(res, resLogin.code);
            that.setData({
              userInfo: res.userInfo,
              hasUserInfo: true
            })
          }
        })
      }
    })
  },
  onLoadUserSuccess: function (detail, code) {
    console.log("code:" + code);
    console.log("encryptedData:", detail);

    httpUtil.doPost({
      app: app,
      url: appParams.loginUrl,
      data: {
        encryptedData: '' + detail.encryptedData,
        iv: '' + detail.iv,
        code: '' + code
      },
      fail: function (e) {
        console.log("登录失败:-------------------------------------")
        console.log(e)

      },
      success: function (res) {
        console.log("登录成功:", res.data)
        var reqData = res.data.data;

        app.globalData.tokenUser = reqData;
        if (reqData.def_account_id > 0) {//有默认的比赛，直接进入默认比赛首页
          wx.redirectTo({
            url: '/pages/home/home?accountId=' + reqData.def_account_id
          });
        } else {//没有默认的比赛，跳转报名页面
          wx.redirectTo({
            url: '../applyMatch/applyMatch?matchId=' + matchId
          });
        }
      }
    });
  }
})