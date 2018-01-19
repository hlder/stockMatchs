//index.js
//获取应用实例
const app = getApp()
var that;
Page({
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
    that=this;
    this.getUserInfo();
  },
  getUserInfo:function(){
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
  onLoadUserSuccess: function (detail,code){
    console.log("code:" + code);
    console.log("encryptedData:", detail);
    wx.request({
      url: 'http://192.168.1.177:8088/loginWx', //仅为示例，并非真实的接口地址
      method: 'POST',
      data: {
        encryptedData: '' + detail.encryptedData,
        iv: '' + detail.iv,
        code: '' + code
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success: function (res) {
        console.log(res.data)
      }
    })

  }
})
  