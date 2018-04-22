/**
 * 
 * js直接调用
 * initFrame(
 *          canvasId            //坐标画布的id
 *          width               //坐标画布的宽度
 *          heigth              //坐标画布的高度
 *          leftBottomText      //坐标左下角的数值
 *          rightBottomText     //坐标右下角的数值
 *          [
 *              {
 *                  color:                  //曲线的颜色
 *                  arr:[0.2,0.8,0.9,0.1]   //数据
 *              } 
 *          ]
 *          )
 */
const category = require('./category.js');

var padd=10;
var bottom=20;
var left_input=50;
var width=0;
var height=0;
var itemHeight=0;
var maxMinGap=0;
var min=100000,max=-100000;
/**
 * 初始化框架
 */
function initFrame(canvasId,baseWidth,baseHeight,leftBottomText,rightBottomText,arrDatas){
    console.log('chartLine',arrDatas);
    // 初始化最值
    min=100000,max=-100000;
    var textSize=10;
    var cs=wx.createContext();
    cs.setFontSize(textSize);
    drawFrame(cs,baseWidth,baseHeight);

    for(var i=0;i<arrDatas.length;i++){
        var item=arrDatas[i];
        for(var j=0;j<item.arr.length;j++){
            var tem=parseFloat(item.arr[j]);
            if(tem<min){
                min=tem;
            }
            if(tem>max){
                max=tem;
            }
        }
    }
    if((max-min)==0){
        max+=0.01;
        min-=0.01;
    }

    var temGap=(max-min)/4;//间隔

    max+=temGap/2;
    min-=temGap/2;
    maxMinGap=max-min;
    temGap=maxMinGap/4;//间隔

    
    cs.setLineWidth(1);
    for(var i=0;i<arrDatas.length;i++){
        var item=arrDatas[i];
        drawChartLine(cs,item.arr,item.color);
    }
    
    drawFrameText(cs,[max,max-temGap,max-temGap*2,max-temGap*3,max-temGap*4],
                    leftBottomText,rightBottomText,textSize);
 
    wx.drawCanvas({
      canvasId: canvasId,
      actions: cs.getActions()
    });
}

/**
 * 绘制线条
 */
function drawChartLine(cs,arr,color){
    cs.beginPath();
    cs.setStrokeStyle(color);
    var item=width/(arr.length-1);

    var x=0,y=0;
    for(var i=0;i<arr.length;i++){
        if(i==0){
            cs.moveTo(padd+item*i,dataToY(min,arr[i]));
        }
        if((i+1)<arr.length){
            x=padd+item*(i+1);
            y=dataToY(min,arr[i+1]);
            cs.lineTo(x,y);
        }
    }
    cs.stroke();
    // wx.drawCanvas({
    //   canvasId: canvasId,
    //   actions: cs.getActions()
    // });
}
function dataToY(min,data){
    // return padd+data;
    return padd+height-((parseFloat(data)-min)/maxMinGap*height);
}

/**
 * 绘制框架文字
 */
function drawFrameText(cs,arr,left,right,textSize){
    var itemPad=(height-padd-padd)/4;
    var x=left_input+padd/2;
    var y=padd*2+padd/2;
    cs.setFillStyle("#9e9e9e");
    cs.setTextAlign('right')
    for(var i=0;i<5;i++){
        cs.fillText(category.transformDecimal(arr[i]*100,2)+"%",x,y+itemPad*i);
    }
    
    cs.setTextAlign('left')
    y=height+padd+textSize+5;
    cs.fillText(left,padd,y);
    cs.fillText(right,padd+width-padd-4*right.length,y);


}

/**
 * 绘制框架
 */
function drawFrame(cs,baseWidth,baseHeight){
    width=baseWidth-padd-padd;
    height=baseHeight-padd-padd-bottom;
    itemHeight=height/4;
    var x=0,y=0,xw=0;

    cs.setStrokeStyle("#bdbdbd");
    cs.setLineWidth(1);
    cs.rect(padd,padd,width,height);
    cs.stroke();
    cs.rect(padd,padd,left_input,height);
    cs.stroke();
    //绘制线条
    x=padd+left_input;
    y=padd+itemHeight*1;
    xw=padd+width;
    cs.moveTo(x,y);
    cs.lineTo(xw,y);
    cs.stroke();
    y+=itemHeight;
    cs.moveTo(x,y);
    cs.lineTo(xw,y);
    cs.stroke();
    y+=itemHeight;
    cs.moveTo(x,y);
    cs.lineTo(xw,y);
    cs.stroke();
    return cs;
}

module.exports = {
  // 外部调用
  initFrame:initFrame
}