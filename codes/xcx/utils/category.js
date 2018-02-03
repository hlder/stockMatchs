const md5 = require('./md5.js');
/**
 * 当前时间 yyyy-MM-dd HH:mm:ss
 */
function formatTime() {
  var date = new Date();
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()

  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}
/**
 * 当前时间 yyyy-MM-dd
 */
function formatDataYMD() {
  var date = new Date();
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()
  return [year, month, day].map(formatNumber).join('-')
}

//
function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

// 时间戳-->时间
function transformTimestamp (timestamp){
  var time = arguments[0] || 0;
  var t, y, m, d, h, i, s;
  t = time ? new Date(time * 1000) : new Date();
  y = t.getFullYear();  // 年
  m = t.getMonth() + 1; // 月
  d = t.getDate();      // 日
  h = t.getHours();     // 时
  i = t.getMinutes();   // 分
  s = t.getSeconds();   // 秒
  return [ y, m, d ].map(formatNumber).join('-') + ' ' + [ h, i, s].map(formatNumber).join(':');

}
// 时间-->时间戳
function transformDate (dateString) {
  var date = dateString;
  date = new Date(Date.parse(date.replace(/-/g, "/")));
  date = date.getTime();
  return date;
}

/** 合并参数
 * json2: 接口需要的特定参数
 * json1 所有接口需要的基本参数;
 * json  基本参数+特定参数(可以为空)
 */
function mergeParameters(json2) {
  var json = {};
 
  var token = wx.getStorageSync('lhyone_token');
  var secretKey = wx.getStorageSync('lhyone_secretKey');
  if(token.length > 0 && secretKey.length > 0){
    var timeOffset = wx.getStorageSync('timeOffset');
    var timestamp = new Date().getTime() + timeOffset;
    var sigString = timestamp + token + secretKey;
    var sig = md5.hexMd5(sigString);
    var json1 = {"token":token, "timestamp":timestamp, "sign":sig};
    json = json1;
  }
  for(var attr in json2){
        json[attr]=json2[attr];
    }
  return json;
}
// 检查手机号码
function checkPhone(phone) {
  var reg = /^1[3|4|5|7|8][0-9]{9}$/; // 验证规则
  return reg.test(phone);
}

// 检查数字和字母
function checkAlphanumeric(value){
  var reg = /^[a-zA-Z0-9_]+$/;
  return reg.test(value);
}

// 百分比
function transformPercent(num) {
  if(num == null){
    num = 0;
  }
  if(isNaN(num)){
    num = 0;
  }
  var befNum = parseFloat(num)*100;
  // toFixed() 实际中出现五舍六入情况; 替换为math.round(num)返回与目标参数最接近的整数
  // var aftNum = befNum.toFixed(2);
  var isMinus = false;
  if(befNum < 0){
    isMinus = true;
    befNum = Math.abs(befNum);
  }
  var aftNum = Math.round(befNum * Math.pow(10, 2)) / Math.pow(10, 2);
  if(isMinus){
    aftNum = 0 - aftNum;
  }
  aftNum = aftNum.toFixed(2);
  return aftNum+'%';
}
// 保留小数位
/**
 * num 小数
 * places 保留位数
 */
function transformDecimal(num,places,rate) {
  if(num == null){
    num = 0;
  }
  if(places == null){
    places = 2;
  }
  if(isNaN(num)){
    num = 0;
  }
  if(isNaN(places)){
    places = 2;
  }
  var places = parseInt(places);
  var befNum = parseFloat(num);
  // var aftNum = befNum.toFixed(places);
  var isMinus = false;
  if(befNum < 0){
    isMinus = true;
    befNum = Math.abs(befNum);
  }
  if(rate=="floor"){
    var aftNum = Math.floor(befNum * Math.pow(10, places)) / Math.pow(10, places);
  }else{
    var aftNum = Math.round(befNum * Math.pow(10, places)) / Math.pow(10, places);
  }
  if(isMinus){
    aftNum = 0 - aftNum;
  }
  aftNum = aftNum.toFixed(places);
  return aftNum;
}
// 转换单位
function transformUnit(num) {
  if(num == null){
    num = 0;
  }
  if(isNaN(num)){
    num = 0;
  }
  var aftNum = parseFloat(num) / 10000.0;
  if(aftNum >= 1 || aftNum <= -1){
    // 万级以上
    var temNum = aftNum / 10000.0;
    if(temNum >= 1 || temNum <= -1){
      // 亿级以上
      return transformDecimal(temNum,2)+'亿';
    }else {
      return transformDecimal(aftNum,2)+'万';
    }
  }else {
    // 万级以下
    return transformDecimal(num,2);
  }
}
// 转换单位 保留几位小数
function transformUnitChart(num, point) {
  if(num == null){
    num = 0;
  }
  if(isNaN(num)){
    num = 0;
  }
  var aftNum = parseFloat(num) / 10000.0;
  if(aftNum >= 1 || aftNum <= -1){
    // 万级以上
    var temNum = aftNum / 10000.0;
    if(temNum >= 1 || temNum <= -1){
      // 亿级以上
      return transformDecimal(temNum, 2)+'亿';
    }else {
      return transformDecimal(aftNum, 2)+'万';
    }
  }else {
    // 万级以下
    return transformDecimal(num,point);
  }
}

// 判断是否为非空
function isNonNull(str){
  if(str == null) {
    return false;
  }
  str = str.toString();
  if(str == ''){
    return false;
  }
  if(str == 'null' || str == 'NULL' || str == 'Null'){
    return false;
  }
  return true;
}

function transformNull(str){
  if(isNonNull(str)){
    return str;
  }else {
    return '';
  }
}

function isEmptyObject(obj){
  for(var item in obj){
    return false;
  }
  return true;
}


// 清空数组
function removeArray(arr) {
  if(arr instanceof Array){
    arr.splice(0,arr.length);
  }else {
    arr = [];
  }
  return arr;
}


// 绘制优惠券底部样式
function drawMycard_bottom(canvasId,viewWidth,viewHeight,textStr,bottomColor,fontObj){
  var ctx = wx.createCanvasContext(canvasId);
  var fontSize = 12
  var fontColor = '#ffffff'
  var color = '#ff5722';
  if(!isEmptyObject(fontObj)){
    fontSize = parseFloat(fontObj.fontSize);
    fontColor = fontObj.fontColor
  }
  if(isNonNull(bottomColor)){
    color = bottomColor
  }
  ctx.setStrokeStyle(color)
  var lineWidth = 1;
  ctx.setLineWidth(lineWidth);
  var radius = 3;
  var length = 5;
  var d_value = radius * 2 + length;
  var startPoint = 0;
  var x = 0;
  var count = Math.ceil(viewWidth / d_value);
  ctx.beginPath();
  ctx.moveTo(startPoint, radius)
  ctx.lineTo(viewWidth, radius)
  for(var i=0;i<count;i++){
    // Draw arc
    ctx.beginPath()
    ctx.arc(startPoint + (length + radius * 2) * i + length + radius, radius, radius, Math.PI, 0)
    ctx.setFillStyle(color)
    ctx.fill()
    ctx.stroke()

    x = startPoint + (length + radius * 2) * (i + 1);
  }
  
  ctx.beginPath();
  ctx.moveTo(x, radius)
  ctx.lineTo(x, viewHeight)
  ctx.lineTo(startPoint, viewHeight)
  ctx.lineTo(startPoint, radius)
  ctx.closePath();
  ctx.stroke();

  ctx.setFillStyle(color)
  ctx.fill()

  if(isNonNull(textStr)){
    ctx.setFontSize(fontSize)
    ctx.setFillStyle(fontColor)
    ctx.fillText(textStr, 10, fontSize + fontSize/2)
  }

  ctx.draw();

}

module.exports = {
    // 合并参数
    mergeParameters: mergeParameters,
    // 时间戳-->时间
    transformTimestamp:transformTimestamp,
    // 时间-->时间戳
    transformDate:transformDate,
    // 获取当前时间(yyyy-MM-dd)
    formatDataYMD:formatDataYMD,
    // 获取当前时间(yyyy-MM-dd HH:mm:ss)
    formatTime:formatTime,
    // 验证手机号码格式
    checkPhone:checkPhone,
    // 百分比
    transformPercent:transformPercent,
    // 保留小数位
    transformDecimal:transformDecimal,
    // 转换单位
    transformUnit:transformUnit,
    transformUnitChart: transformUnitChart,
    // 判断是否为非空
    isNonNull:isNonNull,
    transformNull:transformNull,

    // 清空数组
    removeArray:removeArray,
    // 判断只能输入字母或数字
    checkAlphanumeric:checkAlphanumeric,

    // 判断是否为空对象
    isEmptyObject:isEmptyObject,

    drawMycard_bottom:drawMycard_bottom
}