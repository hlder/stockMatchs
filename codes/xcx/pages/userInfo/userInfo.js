const chartLineUtil = require('../../utils/chartLine.js')
const category = require('../../utils/category.js')


const appParams = require('../../utils/appParams.js')
const httpUtil = require('../../utils/httpUtil.js')
const app = getApp()

var that;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    account:{},
    listEntrustHis:[],
    listEntrust:[],
    isILeader:true,
    listChat:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    that = this;
    console.log(options);
    var leaderAccountId = options.leaderAccountId;//leader的ID
    var matchId = options.matchId;//比赛的ID
    var accountId = options.accountId;//我的账户ID

    httpUtil.doPost({
      app: app,
      url: appParams.queryUserInfo,
      data: {
        matchId: '' + matchId,
        leaderAccountId: '' + leaderAccountId,
        accountId: '' + accountId
      },
      success: function (res) {
        console.log("用户基本信息", res.data.data);
        var data=res.data.data;
        data.account.create_time = that.formatDate(data.account.create_time);
        
        data.account.total_income_rate = category.transformPercent(data.account.total_income_rate);
        data.account.month_income_rate = category.transformPercent(data.account.month_income_rate);
        data.account.week_income_rate = category.transformPercent(data.account.week_income_rate);

        that.setData({
          account: data.account,
          userInfo: data.userInfo,
          listEntrust: data.listEntrust,
          listEntrustHis:data.listEntrustHis,
          isILeader: data.isILeader
        });
      }
    });
    httpUtil.doPost({//获取收益走势图表数据
      app: app,
      url: appParams.queryUserIncomeArr,
      data: {
        matchId: '' + matchId,
        leaderAccountId: '' + leaderAccountId,
        accountId: '' + accountId
      },
      success: function (res) {
        var reqData = res.data.data.list;
        var arr=[];
        var startTime="";
        var endTime="";
        for (var i = 0; i < reqData.length;i++){
          if(i==0){
            startTime = reqData[i]["create_time"];
            startTime = that.formatDate(startTime);
          }
          arr[i] = reqData[i]["income"];
          if (i == (reqData.length-1)) {
            endTime = reqData[i]["create_time"];
            endTime = that.formatDate(endTime);
          }
        }
        wx.getSystemInfo({
          success: function (res) {
            chartLineUtil.initFrame('monthchart', res.windowWidth, 200, "" + startTime, "" + endTime,
              [{ color: "#ff0000", arr: arr }]);
          }
        })

      }
    });


    
    
  },
  formatDate: function (strTime) {
    var date = new Date(strTime);
    return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate();
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