const category = require('./category.js');
/**
   * 绘制时分线
   * cs:画布
   * frameWidth:画布宽
   * frameHeight:画布的高
   * tOpen:昨收
   * vals:数据
        closePrice:当日收盘价
        date:日期
        maxPrice:最高股价
        minPrice:最低股价
        openPrice:当日开盘价
        tradeNum:交易量
        tradePrice:交易额
        yesClosePrice:昨日收盘价
 */
function onDraw(canvasId,frameWidth,frameHeight,tOpen,vals){
    var cs = wx.createCanvasContext(canvasId);
    tOpen = parseFloat(tOpen);
    var textSize=10;
    cs.setFontSize(textSize);
    var pad=0;
    var chartHeight= frameHeight*215/296;
    var zy=pad+frameHeight*221/296;
    var zb=frameHeight;
    var cy=pad;
    var cb=cy+chartHeight;

    var zw=5;
    var gap=2;
    var count=frameWidth/(zw+gap);//总数
    var index=0;
    var max=count;
    if(count<vals.length){
      index=parseInt(vals.length-count);
      max+=index;
    }
    if(vals.length<count){
      max=vals.length;
    }

   //绘制框架
    cs.setStrokeStyle("#bdbdbd");
    cs.setLineWidth(1);
    cs.rect(pad,cy,frameWidth-pad*2,chartHeight);
    cs.stroke();
    
    cs.rect(pad,zy,frameWidth-pad*2,frameHeight-zy);
    cs.stroke();

    var lineGap=frameWidth/100;
    cs.beginPath();
    
    var frameLineGap=frameWidth/4;
    for(var i=0;i<3;i++){
      var temX=frameLineGap*(i+1)-1;
      cs.moveTo(temX,0)
      cs.lineTo(temX,cb);
    }
    for(var i=0;i<3;i++){
      var temX=frameLineGap*(i+1)-1;
      cs.moveTo(temX,zy)
      cs.lineTo(temX,zb);
    }
    var frameYLineGap=cb/4;
    for(var i=0;i<3;i++){
        var temY=frameYLineGap*(i+1)-1;
        cs.moveTo(0,temY)
        cs.lineTo(frameWidth,temY);
    }
    cs.stroke();


    //===================获取最大最小值=====================
    var maxPrice=-1;
    var minPrice=100000;
    var maxNum=-1;
    var minNum=1000000*10000000;
    for(var i=index;i<max;i++){
      var tem=vals[i];
      if(tem.maxPrice>maxPrice){
        maxPrice=tem.maxPrice;
      }
      if(tem.minPrice<minPrice){
        minPrice=tem.minPrice;
      }
      if(tem.tradeNum>maxNum){
        maxNum=tem.tradeNum;
      }
      if(tem.tradeNum<minNum){
        minNum=tem.tradeNum;
      }
    }
    var diff=maxPrice-minPrice;
    //===================获取平均值=====================
    var pj5=new Array();
    var pj10=new Array();
    var pj20=new Array();
    for(var i=0;i<vals.length;i++){
      var tem=vals[i];
      // 提前计算好MA5/MA10/MA20数据及位置
      var ma20_x = frameWidth;
      if(category.isNonNull(tem.ma20)){
        ma20_x = frameWidth - (tem.ma20.length+1)*6;
        tem.ma20_x = ma20_x
      }
      var ma10_x = 0;
      if(category.isNonNull(tem.ma10)){
        ma10_x = ma20_x - (tem.ma10.length+1)*6;
        tem.ma10_x = ma10_x
      }
      
      var ma5_x = 0
      if(category.isNonNull(tem.ma5)){
        ma5_x = ma10_x - (tem.ma5.length+1)*6;
        tem.ma5_x = ma5_x;
      }

      if(i>=4){
        var temCount=0;
        for(var j=0;j<5;j++){
          temCount+=vals[i-j].closePrice;
        }
        pj5.push(temCount/5);
      }
      if(i>=9){
        var temCount=0;
        for(var j=0;j<10;j++){
          temCount+=vals[i-j].closePrice;
        }
        pj10.push(temCount/10);
      }
      if(i>=19){
        var temCount=0;
        for(var j=0;j<20;j++){
          temCount+=vals[i-j].closePrice;
        }
        pj20.push(temCount/20);
      }
    }
    cs.setFontSize(textSize);

    var x=0,y=0;
    //===================绘制柱状图=====================
    cs.beginPath();
    cs.setStrokeStyle("#ff4433");
    for(var i=index;i<max;i++){
      var tem=vals[i];
      if(tem.closePrice>=tem.openPrice){//需要绘制红柱
        x=(zw+gap)*(i-index);
        y=(maxPrice-tem.closePrice)/diff*cb;
        var b=(maxPrice-tem.openPrice)/diff*cb;
        cs.rect(x-zw/2,y,zw,b-y);
        // x+=zw/2;
        y=(maxPrice-tem.maxPrice)/diff*cb;
        cs.moveTo(x,y);
        y=(maxPrice-tem.closePrice)/diff*cb;
        cs.lineTo(x,y);
        y=(maxPrice-tem.openPrice)/diff*cb;
        cs.moveTo(x,y);
        y=(maxPrice-tem.minPrice)/diff*cb;
        cs.lineTo(x,y);
      }
    }
    cs.stroke();
    cs.beginPath();
    cs.setStrokeStyle("#32a632");
    cs.setLineWidth(zw);
    for(var i=index;i<max;i++){
      var tem=vals[i];
      if(tem.closePrice<tem.openPrice){//需要绘制绿柱
        x=(zw+gap)*(i-index);
        y=(maxPrice-tem.closePrice)/diff*cb;
        var b=(maxPrice-tem.openPrice)/diff*cb;
        // x+=zw/2;
        cs.moveTo(x,y);
        cs.lineTo(x,b);
      }
    }
    cs.stroke();

    cs.beginPath();
    cs.setLineWidth(1);
    for(var i=index;i<max;i++){
      var tem=vals[i];
      if(tem.closePrice<tem.openPrice){//需要绘制绿柱
        x=(zw+gap)*(i-index);
        y=(maxPrice-tem.closePrice)/diff*cb;
        // x+=zw/2;
        y=(maxPrice-tem.maxPrice)/diff*cb;
        cs.moveTo(x,y);
        y=(maxPrice-tem.minPrice)/diff*cb;
        cs.lineTo(x,y);
      }
    }
    cs.stroke();

    //====================绘制下面交易量=====================
    cs.beginPath();
    cs.setLineWidth(5);
    cs.setStrokeStyle("#32a632");
    var diffNum=maxNum-minNum;
    for(var i=index;i<max;i++){
      var tem=vals[i];
      if(tem.closePrice<tem.openPrice){//需要绘制绿柱
        x=(zw+gap)*(i-index);
        // x+=zw/2;
        cs.moveTo(x,zb);
        y=zb-(tem.tradeNum-minNum)/diffNum*(zb-zy);
        cs.lineTo(x,y);
      }
    }
    cs.stroke();
    cs.beginPath();
    cs.setLineWidth(5);
    cs.setStrokeStyle("#ff4433");
    var diffNum=maxNum-minNum;
    for(var i=index;i<max;i++){
      var tem=vals[i];
      if(tem.closePrice>tem.openPrice){//需要绘制红柱
        x=(zw+gap)*(i-index);
        // x+=zw/2;
        cs.moveTo(x,zb);
        y=zb-(tem.tradeNum-minNum)/diffNum*(zb-zy);
        cs.lineTo(x,y);
      }
    }
    cs.stroke();


    //===================绘制均线=====================
    cs.beginPath();//绘制5日均线
    cs.setStrokeStyle("#ff4c94");
    cs.setLineWidth(1);
    var temIndex=index-5;
    for(var i=temIndex;i<max;i++){
      if(i<pj5.length&&i>=0){
        x=(zw+gap)*(i-temIndex);
        y=(maxPrice-pj5[i])/diff*cb;
        if(i==temIndex){
          cs.moveTo(x,y);
        }else{
          cs.lineTo(x,y);
        }
      }
      
    }
    cs.stroke();
    cs.beginPath();//绘制10日均线
    cs.setStrokeStyle("#ffb400");
    var temIndex=index-10;
    for(var i=temIndex;i<max;i++){
      if(i<pj10.length&&i>=0){
        x=(zw+gap)*(i-temIndex);
        y=(maxPrice-pj10[i])/diff*cb;
        if(i==temIndex){
          cs.moveTo(x,y);
        }else{
          cs.lineTo(x,y);
        }
      }
    }
    cs.stroke();
    cs.beginPath();//绘制20日均线
    cs.setStrokeStyle("#0fdeee");
    var temIndex=index-20;
    for(var i=temIndex;i<max;i++){
      if(i<pj20.length&&i>=0){
        x=(zw+gap)*(i-temIndex);
        y=(maxPrice-pj20[i])/diff*cb;
        if(i==temIndex){
          cs.moveTo(x,y);
        }else{
          cs.lineTo(x,y);
        }
      }
    }
    cs.stroke();
    cs.draw();



    cs.beginPath();
    cs.setStrokeStyle("#000000");
    cs.fillText(maxPrice+"",5,textSize);
    cs.fillText(minPrice+"",5,cb-1);
    cs.stroke();

  }

  /**
   * 绘制触摸线
   * tx，ty:触摸点的x，y
   * 返回当前选择的object
   */
  function drawCross(canvasId,frameWidth,frameHeight,tOpen,vals,tx,ty){
    var cs = wx.createCanvasContext(canvasId);
    tOpen = parseFloat(tOpen);
    cs.setLineWidth(1);
    var fontSize = 10
    cs.setFontSize(fontSize);
    var pad=0;
    var chartHeight= frameHeight*215/296;
    var zy=pad+frameHeight*221/296;
    var zb=frameHeight;
    var cy=pad;
    var cb=cy+chartHeight;

    var zw=5;
    var gap=2;
    var count=frameWidth/(zw+gap);//总个数
    var index=0;
    var max=count;
    if(count<vals.length){
      index=parseInt(vals.length-count);
      max+=index;
    }
    if(vals.length<count){
      max=vals.length;
    }
    var x=0,selectX=0;
    var minDifx=1000000;
    var selectIndex=0;
    //=======================================================
    selectIndex = parseInt((tx * (count)) / frameWidth + index);
    if (selectIndex >= vals.length || selectIndex < 0){
      return;
    }
    selectX = (zw + gap) * (selectIndex - index) ;
    minDifx = Math.abs(x - tx);

    // console.log('selectIndex:', selectIndex);
    var temObj = vals[selectIndex];
    
    var text = "" + temObj.date;
    var textX = selectX - text.length * 5 / 2;
    if (textX < 0) {
      textX = 0;
    }
    var temW = frameWidth - (text.length + 1) * 5;
    if (textX > temW) {
      textX = temW;
    }


    cs.beginPath();
    cs.setLineWidth(10);
    cs.setStrokeStyle("#ffffff");
    cs.moveTo(textX, cb - 5);
    cs.lineTo(textX + (text.length + 1) * 5, cb - 5);
    cs.stroke();


    cs.beginPath();
    cs.setLineWidth(1);
    cs.setStrokeStyle("#000000");
    cs.moveTo(selectX,0);
    cs.lineTo(selectX,frameHeight);

    cs.setFillStyle("#000000");
    cs.fillText(text,textX,cb);
    cs.stroke();


    cs.beginPath();
    if(category.isNonNull(temObj.ma5)){
      cs.setFillStyle("#ff4c94");
      cs.fillText(temObj.ma5+"",temObj.ma5_x+"",fontSize);
    }
    if(category.isNonNull(temObj.ma10)){
      cs.setFillStyle("#ffb400");
      cs.fillText(temObj.ma10+"",temObj.ma10_x+"",fontSize);
    }
    if(category.isNonNull(temObj.ma20)){
      cs.setFillStyle("#0fdeee");
      cs.fillText(temObj.ma20+"",temObj.ma20_x+"",fontSize);
    }
    cs.stroke();
    cs.draw();

    return temObj;

  }

  function drawStockInfo(canvasId,frameWidth,frameHeight,stockInfo){
    var cs = wx.createCanvasContext(canvasId);
    var padding_l = 9; // 横向字体间隔
    var padding_t = 4; // 纵向字体间隔 
    var gap = 5;
    cs.setLineWidth(1);
    var fontSize = 12
    cs.setFontSize(fontSize);
    var openStr='开',closeStr='收',maxStr='高', minStr='低',changeRate='幅',changeStr='额',numStr='成交',zRateStr='振幅';
    var part1_w = frameWidth*0.24;
    var part2_w = frameWidth*0.28;
    var line1_y = padding_t+fontSize;
    var line2_y = padding_t*2+fontSize*2;
    cs.setFillStyle("#9e9e9e");
    cs.fillText(openStr,padding_l,line1_y);
    cs.fillText(closeStr,padding_l,line2_y);
    cs.fillText(maxStr,part1_w,line1_y);
    cs.fillText(minStr,part1_w,line2_y);
    cs.fillText(changeRate,part1_w*2,line1_y);
    cs.fillText(changeStr,part1_w*2,line2_y);
    cs.fillText(numStr,part1_w*3-padding_l,line1_y);
    cs.fillText(zRateStr,part1_w*3-padding_l,line2_y);

    cs.setFillStyle("#424242");
    cs.fillText(stockInfo.tradeNumStr+'手',part1_w*3+(numStr.length+1)*6,line1_y);
    cs.fillText(stockInfo.zRateStr,part1_w*3+(zRateStr.length+1)*6,line2_y);

    var redColor = '#ff5722';
    var greenColor = '#00bfa5';
    // 开盘价
    if(stockInfo.openDiff < 0){
      cs.setFillStyle(greenColor);
    }else {
      cs.setFillStyle(redColor);
    }
    cs.fillText(stockInfo.openPrice,padding_l+gap+(openStr.length+1)*6,line1_y);
    // 收盘价
    if(stockInfo.closeDiff < 0){
      cs.setFillStyle(greenColor);
    }else {
      cs.setFillStyle(redColor);
    }
    cs.fillText(stockInfo.closePrice,padding_l+gap+(closeStr.length+1)*6,line2_y);
    // 最高价
    if(stockInfo.maxDiff < 0){
      cs.setFillStyle(greenColor);
    }else {
      cs.setFillStyle(redColor);
    }
    cs.fillText(stockInfo.maxPrice,part1_w+gap+(maxStr.length+1)*6,line1_y);
    // 最低价
    if(stockInfo.minDiff < 0){
      cs.setFillStyle(greenColor);
    }else {
      cs.setFillStyle(redColor);
    }
    cs.fillText(stockInfo.minPrice,part1_w+gap+(minStr.length+1)*6,line2_y);
    // 涨跌幅
    if(stockInfo.changeRate < 0){
      cs.setFillStyle(greenColor);
    }else {
      cs.setFillStyle(redColor);
    }
    cs.fillText(stockInfo.changeRateStr,part1_w*2+gap+(changeRate.length+1)*6,line1_y);
    // 涨跌额
    if(stockInfo.change < 0){
      cs.setFillStyle(greenColor);
    }else {
      cs.setFillStyle(redColor);
    }
    cs.fillText(stockInfo.changeStr,part1_w*2+gap+(changeStr.length+1)*6,line2_y);

    cs.draw();

  }
  
  function clearCanvas(canvasId){
    var cs = wx.createCanvasContext(canvasId);
    cs.draw();
  }

  module.exports = {
  // 外部调用 hexMd5 即可
  onDraw:onDraw,
  drawCross:drawCross,
  drawStockInfo:drawStockInfo,
  clearCanvas:clearCanvas
}
