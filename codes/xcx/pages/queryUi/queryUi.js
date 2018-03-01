// pages/queryUi/queryUi.js

var accountId;
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
    accountId = options.accountId;
    wx.setNavigationBarTitle({
      title: '查询' 
    });
  },
  onButtonClick1:function(){//今日成交
    wx.navigateTo({
      url: "../entrustHistoryList/entrustHistoryList?accountId=" + accountId +"&title=今日成交&indexType=0"
    })
  },
  onButtonClick2: function () {//今日委托
    wx.navigateTo({
      url: "../entrustHistoryList/entrustHistoryList?accountId=" + accountId + "&title=今日委托&indexType=1"
    })
  },
  onButtonClick3: function () {//成交历史
    wx.navigateTo({
      url: "../entrustHistoryList/entrustHistoryList?accountId=" + accountId + "&title=成交历史&indexType=2"
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