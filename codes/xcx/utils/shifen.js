

const category = require('./category.js');
  /**
   * 绘制时分线
   * cs:画布
   * frameWidth:画布宽
   * frameHeight:画布的高
   * tOpen:昨收
   * vals:数据
          price:当前价
          time:时间
          tranPrice:交易额
          vol:交易量
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

   //绘制框架
    cs.setStrokeStyle("#bdbdbd");
    cs.setLineWidth(1);
    cs.rect(pad,cy,frameWidth-pad*2,chartHeight);
    cs.stroke();
    
    cs.rect(pad,zy,frameWidth-pad*2,frameHeight-zy);
    cs.stroke();

    var lineGap=frameWidth/100;
    var zeroHeight=cb/2-1;
    cs.beginPath();
    for(var i=0;i<100;i++){
      if(i%2!=0){
        cs.lineTo(i*lineGap,zeroHeight);
      }else{
        cs.moveTo(i*lineGap,zeroHeight);
      }
    }
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
      if(i!=1){//中间虚线不用重画
        var temY=frameYLineGap*(i+1)-1;
        cs.moveTo(0,temY)
        cs.lineTo(frameWidth,temY);
      }
    }
    cs.stroke();


    var maxDiff=-1;//最大价格的差值
    var maxDiffPrice=-1;//最大价格差值的价格

    var maxNum=-1;//最大成交量
    for(var i=0;i<vals.length;i++){
      var tem=vals[i];
      var diff=Math.abs(tem.price-tOpen);
      if(diff>maxDiff){
        maxDiff=diff;
        maxDiffPrice=Math.abs(tem.price);
      }
      if(tem.vol>maxNum){
        maxNum=tem.vol;
      }
    }
    if(maxDiff==0){
      maxDiff=1.0;
      maxDiffPrice+=maxDiff/2;
    }
    maxDiff+=maxDiff/20;

    var gapw=getGap(frameWidth);
    



    var x=0,y=0;

    /**
     * 绘制平均值
     */
    cs.beginPath();
    cs.setStrokeStyle("#e7b448");
    var count=0;
    for(var i=0;i<vals.length;i++){
        var tem=vals[i];
        x=pad+gapw*i;
        count+=tem.price;
      if(i==0){
        y=(maxDiff+tOpen-count)/(maxDiff*2)*(cb-cy);
        cs.moveTo(x,y);
      }else{
        var pj=count/(i+1);
        y=(maxDiff+tOpen-pj)/(maxDiff*2)*(cb-cy);
        // tem.price
        cs.lineTo(x,y);
      }
    }
    cs.stroke();
    /**
     * 绘制线条
     */
    cs.beginPath();
    cs.setStrokeStyle("#00d0fc");
    for(var i=0;i<vals.length;i++){
      var tem=vals[i];
      x=pad+gapw*i;
      y=(maxDiff+tOpen-tem.price)/(maxDiff*2)*(cb-cy);
      if(i==0){
        cs.moveTo(x,y);
      }
      cs.lineTo(x,y);
    }
    cs.stroke();
    //绘制交易量红线
    if(maxNum>0){
      cs.beginPath();
      cs.setStrokeStyle("#ff4433");
      for(var i=0;i<vals.length;i++){
        var tem=vals[i];
        x=pad+gapw*i;
        y=zb-tem.vol/maxNum*(frameHeight-zy);
        //y=(maxDiff+tOpen-tem.price)/(maxDiff*2)*(cb-cy);
        var last=vals[i];
        if(i!=0){
          last=vals[i-1];
        }
        if(tem.price>=last.price){
          cs.moveTo(x,zb);
          cs.lineTo(x,y);
        }
      }
      cs.stroke();

      //绘制交易量绿线
      cs.beginPath();
      cs.setStrokeStyle("#32a632");
      for(var i=0;i<vals.length;i++){
        var tem=vals[i];
        x=pad+gapw*i;
        y=zb-tem.vol/maxNum*(frameHeight-zy);
        //y=(maxDiff+tOpen-tem.price)/(maxDiff*2)*(cb-cy);
        var last=vals[i];
        if(i!=0){
          last=vals[i-1];
        }
        if(tem.price<last.price){
          cs.moveTo(x,zb);
          cs.lineTo(x,y);
        }
      }
      cs.stroke();
    }
    

    //绘制文字
    cs.beginPath();
    cs.setStrokeStyle("#000000");
    cs.fillText(category.transformDecimal((tOpen+maxDiff),2)+"",5,textSize);
    cs.fillText(category.transformDecimal((tOpen-maxDiff),2)+"",5,cb-1);
    var bfh=category.transformDecimal((maxDiff/tOpen*100),2)+"%";
    var tw=("--"+bfh).length*textSize/2;
    cs.fillText(bfh,frameWidth-tw,textSize);
    cs.fillText("-"+bfh,frameWidth-tw,cb-1);
    cs.stroke();
    cs.draw();

  }


  /**
   * 绘制触摸事件线条
   * x，y:触摸点的x，y
   * 返回当前选择的object
   */
  function drawCross(canvasId,frameWidth,frameHeight,tOpen,vals,x,y){
    var cs = wx.createCanvasContext(canvasId);
    tOpen = parseFloat(tOpen);
    cs.setFontSize(10);
    var gapw=getGap(frameWidth);
    var maxWidth=vals.length*gapw;
    if(x>maxWidth){
      x=maxWidth;
    }
    var temItem=parseInt(vals.length*x/maxWidth);
    // console.log("return:======", temItem, vals.length);
    if(temItem>=vals.length||temItem<0){
    // console.log("return:======");
        return;
    }
    var itemObj=vals[temItem];
    var chartHeight= frameHeight*215/296;
    var cy=0;
    var cb=cy+chartHeight;
    var maxDiff=getMaxDiff(vals,tOpen);
    x=gapw*temItem;
    y=(maxDiff+tOpen-itemObj.price)/(maxDiff*2)*(cb-cy);


    var bfb=category.transformDecimal(((itemObj.price-tOpen)/tOpen*100),2)+"%";
    var textW=frameWidth-bfb.length*5-5;

    //绘制背景
    cs.beginPath();
    cs.setStrokeStyle("#eeeeee");
    cs.setGlobalAlpha(0.5);
    cs.setLineWidth(10);
    cs.moveTo(textW,y);
    cs.lineTo(textW+bfb.length*5+5,y);

    cs.moveTo(0,y);
    cs.lineTo((""+itemObj.price).length*5+10,y);
    cs.stroke();


    //绘制文字,线条
    cs.beginPath();
    cs.setStrokeStyle("#000000");
    cs.setGlobalAlpha(1);
    cs.setLineWidth(1);

    cs.moveTo(x,0);
    cs.lineTo(x,frameHeight);

    cs.moveTo((""+itemObj.price).length*5+10,y);
    cs.lineTo(frameWidth-bfb.length*5-10,y);

    cs.fillText(""+itemObj.price,5,y+5);//左边文字
    cs.fillText(bfb,textW,y+5);//右边文字
    cs.stroke();
    cs.draw();
    

    return itemObj;
  }


  //获取间隔
  function getGap(width){
    var tem = parseFloat(width) / 239;
    return tem;
  }
  function getMaxDiff(vals,tOpen){
    var maxDiff=-1;//最大价格的差值

    var maxNum=-1;//最大成交量
    for(var i=0;i<vals.length;i++){
      var tem=vals[i];
      var diff=Math.abs(tem.price-tOpen);
      if(diff>maxDiff){
        maxDiff=diff;
      }
      
    }
    maxDiff+=maxDiff/20;
    return maxDiff;
  }
  function getMaxNum(vals){
    var maxNum=-1;//最大成交量
    for(var i=0;i<vals.length;i++){
      var tem=vals[i];
      if(tem.vol>maxNum){
        maxNum=tem.vol;
      }
    }
    return maxNum;
  }

  function drawStockInfo(canvasId,frameWidth,frameHeight,stockInfo){
    var cs = wx.createCanvasContext(canvasId);
    var padding = 10; //
    var gap = 5;
    cs.setLineWidth(1);
    var fontSize = 12
    cs.setFontSize(fontSize);
    var priceStr='价',changeStr='幅', volStr='量',aveStr='均';
    var part1_w = frameWidth*0.14;
    var part2_w = frameWidth*0.20;
    var part3_w = frameWidth*0.26;
    var part4_w = frameWidth*0.20;
    var line_y = padding+fontSize;
    cs.setFillStyle("#9e9e9e");
    cs.fillText(priceStr,part1_w,line_y);
    cs.fillText(changeStr,part1_w+part2_w,line_y);
    cs.fillText(volStr,part1_w+part2_w*2,line_y);
    cs.fillText(aveStr,part1_w+part2_w*2+part3_w,line_y);

    cs.setFillStyle("#424242");
    cs.fillText(stockInfo.timeStr+'',9,line_y);
    cs.fillText(stockInfo.tradeNumStr+'手',part1_w+part2_w*2+(volStr.length+1)*6+gap,line_y);
 

    var redColor = '#ff5722';
    var greenColor = '#00bfa5';
    // 当前价
    if(stockInfo.changeRate < 0){
      cs.setFillStyle(greenColor);
    }else {
      cs.setFillStyle(redColor);
    }
    cs.fillText(stockInfo.price,part1_w+gap+(priceStr.length+1)*6,line_y);
  
    // 涨跌幅
    if(stockInfo.changeRate < 0){
      cs.setFillStyle(greenColor);
    }else {
      cs.setFillStyle(redColor);
    }
    cs.fillText(stockInfo.changeRateStr,part1_w+part2_w+gap+(changeStr.length+1)*6,line_y);
    
    // 均价
    if(stockInfo.averageDiff < 0){
      cs.setFillStyle(greenColor);
    }else {
      cs.setFillStyle(redColor);
    }
    cs.fillText(stockInfo.average,part1_w+part2_w*2+part3_w+gap+(aveStr.length+1)*6,line_y);
    
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
