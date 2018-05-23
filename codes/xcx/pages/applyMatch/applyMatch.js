const appParams = require('../../utils/appParams.js')
const httpUtil = require('../../utils/httpUtil.js')

const app = getApp()
var that;
var inputPhoneNum;
var matchId;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    matchInfo:null,

    isShowTitleBanner:false,
    bannerWidth:200,
    bannerHeigth:150,
    bannerImgUrl:"",
    isAuthCodeSend:false,//是否已经发送了验证码
    isShowProfession:false,//是否要输入职业
    isShowClass: false,//是否要输入班级
    isShowStuNum: false,//是否要输入学号
    countDown:-1//发送验证码倒计时
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    that = this;
    matchId=options.matchId;
    
    httpUtil.doPost({
      app:app,
      url: appParams.queryApplyMatch,
      data: {
        matchCode: '' + matchId
      },
      success: function (res) {
        console.log(res.data)
        that.setData({
          matchInfo: res.data.data
        });
        var bl = parseFloat(res.data.data.apply_banner_height) / parseFloat(res.data.data.apply_banner_width);
        var isShowProfession = false, isShowClass = false, isShowStuNum = false;

        if (res.data.data.is_need_profession == 0) {
          isShowProfession = true;
        } else {
          isShowProfession = false;
        }
        if (res.data.data.is_need_stu_class == 0) {
          isShowClass = true;
        } else {
          isShowClass = false;
        }
        if (res.data.data.is_need_stu_num == 0) {
          isShowStuNum = true;
        } else {
          isShowStuNum = false;
        }
        that.chanageBanner(parseFloat(bl), res.data.data.apply_banner, isShowProfession, isShowClass, isShowStuNum);

      }
    });
    
  }, 
  /**
   * 改变banner的大小和图片
   */

  chanageBanner: function (bl, imgUrl, isShowProfession, isShowClass, isShowStuNum){
    try {
      var res = wx.getSystemInfoSync()
      this.setData({
        bannerWidth: res.windowWidth,
        bannerHeigth: res.windowWidth*bl,
        bannerImgUrl:imgUrl,
        isShowTitleBanner: true,
        isShowProfession: isShowProfession,
        isShowClass: isShowClass,
        isShowStuNum: isShowStuNum
      });
    } catch (e) {
    }
  },
  bindPhoneInput:function(e){//数据手机号监听
    inputPhoneNum = e.detail.value;
  },
  onSendSmsClick:function(e){//点击发送验证码
    if (that.data.countDown>0){
      return;
    }

    console.log('输入的手机号：', inputPhoneNum)
    if (typeof (inputPhoneNum) == "undefined") { 
      wx.showToast({
        title: '请输入手机号',
        icon: 'none',
        duration: 2000
      })
    }else{//执行发送验证码
      that.setData({
        countDown:60
      });
      that.dataTimer();

      httpUtil.doPost({
        app: app,
        url: appParams.sendAuthSmsCode,
        data: {
          matchId: "" + matchId,
          phone: "" + inputPhoneNum
        },
        success: function (res) {
          console.log("返回:", res.data)
          if (res.data.code == 0) {//发送成功
          }else{//发送验证码失败
            // that.setData({
            //   countDown: -1
            // });
            wx.showToast({
              title: '' + res.data.msg,
              icon: 'none',
              duration: 2000
            })
          }
          // WxParse.wxParse('article', 'html', res.data.data, that, 5);

        }
      });
    }
  },
  formSubmit:function(e){
    console.log('form发生了submit事件，携带数据为：', e.detail.value)
    var temVal = e.detail.value;
    httpUtil.doPost({
      app: app,
      url: appParams.applyMatch,
      data: {
        matchId: matchId,
        name: temVal.name,
        phoneNum: temVal.phone,
        authNum: temVal.authCode,
        profession: temVal.profession,
        stuClass: temVal.class,
        stuNum: temVal.number,
        isShowProfession: that.data.isShowProfession,//是否要输入职业
        isShowClass: that.data.isShowClass,//是否要输入班级
        isShowStuNum: that.data.isShowStuNum//是否要输入学号
      },
      success: function (res) {
        console.log(res.data)
        if (res.data.code==0){//成功
          wx.showToast({
            title: '报名成功' ,
            icon: 'none',
            duration: 2000,
            success:function(){//弹框结束了
              wx.setStorageSync("matchId", "" + matchId);
              wx.setStorageSync("accountId", "" + res.data.data.id);
              wx.switchTab({
                url: '/pages/home/home'
              });
            }
          })
        }else{
          // res.data.msg;
          wx.showToast({
            title: '' + res.data.msg,
            icon: 'none',
            duration: 2000
          })
        }

      }
    });
    
  },


  dataTimer: function () {//定时器，定时加载股票信息
    // that.loadBuyAndSell5();
    var count = that.data.countDown;
    if (count>=0){
      count--;
      that.setData({
        countDown: count
      });
      setTimeout(that.dataTimer, 1000);//每秒定时器
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