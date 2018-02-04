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
    startDate:"2018-01-01",
    endDate: "2018-01-01",
    listData: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.setNavigationBarTitle({
      title: '' + options.title
    });
    var nowDate = new Date();
    var temDate = nowDate.getFullYear() + "-" + (nowDate.getMonth()+1) + "-" + nowDate.getDate();
    this.setData({
      startDate: temDate,
      endDate:temDate
    });

    that = this;
    httpUrl = appParams.queryMyEntrustHistory;
    accountId = options.accountId;
    this.reloadData();
  },
  reloadData: function () {
    httpUtil.doPost({
      app: app,
      url: httpUrl,
      data: {
        accountId: "" + accountId,
        startDate: that.data.startDate+" 00:00:00",
        endDate: that.data.endDate+" 23:59:59",
        page:0
      },
      success: function (res) {
        console.log("返回:", res.data)
        that.setData({
          listData: res.data.data
        });
      }
    });
  },
  onSearchClick: function () {//点击搜索
    this.reloadData();
  },
  onCheDanClick: function (e) {
    var entrustId = e.currentTarget.id;
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
  bindStartDateChange:function(e){//起始时间
    this.setData({
      startDate: e.detail.value
    })
  },
  bindEndDateChange:function(e){//结束时间
    this.setData({
      endDate: e.detail.value
    })
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