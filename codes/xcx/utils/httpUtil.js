
function doPost(params) {
  if (params.app.globalData.tokenUser!=null){
    params.data.userId = params.app.globalData.tokenUser.id;
    params.data.token = params.app.globalData.tokenUser.token;
  }

  console.log("tokenUser:", params.data);
  wx.request({
    url: params.url, //仅为示例，并非真实的接口地址
    method: 'POST',
    data: params.data,
    header: {
      'content-type': 'application/x-www-form-urlencoded' // 默认值
    },
    success: params.success,
    fail: params.fail
  })
}
module.exports = {
  doPost: doPost
}
