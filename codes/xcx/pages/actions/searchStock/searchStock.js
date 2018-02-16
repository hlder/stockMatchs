// pages/actions/searchStock/searchStock.js
const appParams = require('../../../utils/appParams.js')
const httpUtil = require('../../../utils/httpUtil.js')
const app = getApp()
var that;

Page({

  /**
   * 页面的初始数据
   */
  data: {
    searchStr:"",
    requestListData:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    that=this;
    wx.setNavigationBarTitle({
      title: "搜索"
    });

    //this.doSearch("a");
    
  },

  doSearch:function(searchStr){
    httpUtil.doPost({
      app: app,
      url: appParams.queryStockFuzzy,
      data: {
        searchStr: searchStr
      },
      success: function (res) {
        console.log(res.data)
        that.setData({
          requestListData: res.data.data
        });
      }
    });
  },
  onInput:function(e){//当用户输入
    this.doSearch("" + e.detail.value);
  },
  onItemClick:function(e){
    var temBean=that.data.requestListData[e.currentTarget.id];
    let pages = getCurrentPages();//当前页面
    let prevPage = pages[pages.length - 2];//上一页面
    prevPage.setData({//直接给上移页面赋值
      selectStockCode: temBean.stock_code,
      selectStockCodeStr: temBean.stock_code_str,
      selectStockName: temBean.stock_name,
    });

    wx.navigateBack({
      delta: 1
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