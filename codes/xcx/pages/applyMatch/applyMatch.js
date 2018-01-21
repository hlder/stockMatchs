// pages/applyMatch/applyMatch.js
var that;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isShowTitleBanner:false,
    bannerWidth:200,
    bannerHeigth:150,
    bannerImgUrl:"",
    isShowProfession:true,//是否要输入职业
    isShowClass:true,//是否要输入班级
    isShowStuNum:true//是否要输入学号
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    that=this;
    this.chanageBanner(0.3,"http://pic.qiantucdn.com/58pic/17/93/62/87e58PICV9N_1024.jpg!/fw/780/watermark/url/L3dhdGVybWFyay12MS40LnBuZw==/align/center");
  
  }, 
  /**
   * 改变banner的大小和图片
   */
  chanageBanner: function (bl, imgUrl){
    try {
      var res = wx.getSystemInfoSync()
      this.setData({
        bannerWidth: res.windowWidth,
        bannerHeigth: res.windowWidth*bl,
        bannerImgUrl:imgUrl,
        isShowTitleBanner: true
      });
    } catch (e) {
    }
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