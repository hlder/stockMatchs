
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
function doKline(params){
  wx.request({
    url: params.url,
    method: 'GET',
    header: { "content-type": "application/json" },
    success: function (res) {
      var statusCode = res.statusCode;
      if (statusCode == 200) {
        var str = res.data;
        // 剔除回车 \n
        str = str.replace(/[\r\n]/g, "");
        // 截取"var ****="之后的字符串
        var index = str.indexOf('=');
        var jsonStr = str.substring(index + 1, str.length - 1);
        // 剔除最外层{} 
        jsonStr = jsonStr.substring(1, jsonStr.length - 1);
        jsonStr = jsonStr.substring(jsonStr.indexOf(":") + 1);

        var jsonData = JSON.parse(jsonStr);
        var result = { data: { rows: jsonData } }


        params.success(result);
      }
    },
    fail: params.fail,
    complete: function (res) { }
  })
}

function doShifen(params) {
  wx.request({
    url: params.url,
    method: 'GET',
    header: { "content-type": "application/json" },
    success: function (res) {
      var statusCode = res.statusCode;
      if (statusCode == 200) {
        var str = res.data;
        str = str.replace(/[\r\n]/g, "");
        var index = str.indexOf('=');
        var jsonStr = str.substring(index + 1, str.length - 1);
        // 剔除最外层{} 
        jsonStr = jsonStr.substring(1, jsonStr.length - 1);
        //jsonStr = jsonStr.replace(/,Data:/,"|");
        // 分割字符串
        var jsonArr = jsonStr.split(":[");
        // 处理summary部分字符串
        var summary = jsonArr[0];
        summary = summary.substring(summary.indexOf("{") + 1, summary.length - 1);
        var summaryArr = summary.split(",");

        var item = summaryArr[1];
        var sum_value = item.substring(item.indexOf(":") + 1)   //报错地方

        // 处理data部分字符串
        var dataStr = "[" + jsonArr[1];
        var jsonData = JSON.parse(dataStr);

        var result = { data: { tOpen: sum_value, Data: jsonData } }

        params.success(result);
      }

    },
    fail: params.fail,
    complete: function (res) {}
  })
}


module.exports = {
  doPost: doPost,
  doShifen: doShifen,
  doKline: doKline
}
