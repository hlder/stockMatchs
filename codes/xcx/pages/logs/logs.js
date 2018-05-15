//logs.js
const util = require('../../utils/util.js')

Page({
  data: {
    logs: [],
    temp:""
  },
  onLoad: function () {
  },
  onGetWeChatFormId:function(e){
    console.log("点击了")
    this.setData({ temp: "" + e.detail.formId});
  }
})
