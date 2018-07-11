// pages/ranking/ranking.js
const httpUtil = require('../../utils/httpUtil.js')
const appParams = require('../../utils/appParams.js')
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
  
  },
  skip: function (skipType, value) {
    console.log("skipType:", skipType, "    value:", value);
    if (skipType == 3) {//跳转html解析
      console.log("跳转3");
      wx.navigateTo({
        url: '/pages/h5/h5?value=' + value
      });
    }
    if (skipType == 2) {//跳转功能
      console.log("跳转2");
      wx.navigateTo({
        url: '' + value
      });
    }
  },
  onBannerItemClick: function (e) {//点击banner
    console.log();
    var item = that.data.banners[parseInt(e.currentTarget.id)];
    that.skip(item.type, item.skip);
  },
  onButtonItemClick: function (e) {//功能按钮的点击事件
    console.log(e.currentTarget.id);
    var item = that.data.buttons[parseInt(e.currentTarget.id)];
    that.skip(item.type, item.skip);
    console.log("item", item);
  },



  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    that = this;
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    that.loadHomeData();
    that.loadMatchRanking();
  },



  onMoreLeaderBtnClick: function (e) {//更多的leader
    wx.navigateTo({
      url: '/pages/leaderList/leaderList?matchId=' + matchId + '&accountId=' + accountId
    });
  },
  onUserItemClick: function (e) {
    // leaderAccountId=14 & matchId=1 & accountId=13
    wx.navigateTo({
      url: '/pages/userInfo/userInfo?matchId=' + matchId + '&accountId=' + accountId + '&leaderAccountId=' + e.currentTarget.id
    });
  },

  loadMatchRanking: function () {
    httpUtil.doPost({
      app: app,
      url: appParams.queryMatchRanking,
      data: {
        matchId: "" + matchId,
        accountId: "" + accountId,
        page:0,
        pageSize:20
      },
      success: function (res) {
        var data = res.data.data;
        that.setData({
          matchRanking:data
        });
        
      }
    });
  },


  loadHomeData: function () {
    wx.showLoading({
      title: '加载中',
    })

    accountId = "" + wx.getStorageSync("accountId");
    matchId = "" + wx.getStorageSync("matchId");

    httpUtil.doPost({
      app: app,
      url: appParams.queryHomeInfo,
      data: {
        matchId: "" + matchId,
        accountId: "" + accountId
      },
      fail: function (e) {
        wx.hideLoading()
      },
      success: function (res) {
        wx.hideLoading()
        console.log("返回:", res.data)
        var data = res.data.data;
        // data.leaders
        if (data.leaders != null) {
          for (var i = 0; i < data.leaders.length; i++) {
            var text = data.leaders[i].total_income_rate;
            data.leaders[i].total_income_rate = category.transformPercent(text);
          }
        }

        that.setData({
          banners: JSON.parse(res.data.data.banners),
          buttons: JSON.parse(res.data.data.buttons),
          matchInfo: data
        });
        // 设置nav
        wx.setNavigationBarTitle({
          title: data.macthName
        })
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