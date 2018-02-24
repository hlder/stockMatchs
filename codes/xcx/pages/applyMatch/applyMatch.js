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
    timerAuthCodeSend:60,//发送验证码的倒计时
    isShowProfession:false,//是否要输入职业
    isShowClass: false,//是否要输入班级
    isShowStuNum: false//是否要输入学号
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
    console.log('输入的手机号：', inputPhoneNum)
    if (typeof (inputPhoneNum) == "undefined") { 
      wx.showToast({
        title: '请输入手机号',
        icon: 'none',
        duration: 2000
      })
    }else{//执行发送验证码

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
              wx.redirectTo({
                url: '/pages/home/home?accountId=' + res.data.data.id
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