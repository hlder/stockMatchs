// const AppIp = "https://www.qdcash.xyz/";
const AppIp = "http://127.0.0.1:8088/";
// const AppIp = "http://47.100.180.170:8088/";
// const AppIp = "http://127.0.0.1:8088/";
// const AppIp = "http://192.168.1.175:8088/";
// const AppIp = "http://192.168.31.130:8088/";
const stockIp = "https://qdcash.xyz/";
// const stockIp = "http://47.100.180.170:8080/";

//sina查询股票指数列表的接口
const urlSinaStockIndexList ="https://hq.sinajs.cn/list=sh000001,sz399001,sz399006,sz399300,sh000016,sz399905";
//sina涨幅榜
const urlSinaStockUpRanking = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=10&sort=changepercent&asc=0&node=hs_a&symbol=&_s_r_a=init#";
//sina跌幅榜
const urlSinaStockDownRanking = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=10&sort=changepercent&asc=1&node=hs_a&symbol=&_s_r_a=sort#";




// stockServer/queryStockInfoByCode?stockCode=sz002471

const loginUrl = AppIp + "loginWx";//登录

const queryApplyMatch = AppIp + "queryApplyMatchInfo";//查询报名比赛的信息
const applyMatch = AppIp + "applyMatch";//申请报名
const queryHomeInfo = AppIp +"queryHomeInfo";//查询主页信息
const queryMyHolderInfos = AppIp +"queryMyHolderInfos";//查询我的持仓
const queryMyEntrust = AppIp +"queryMyEntrust";//查询我的委托
const revokeMyEntrust = AppIp +"revokeMyEntrust";//撤单
const queryMyEntrustHistory = AppIp +"queryMyEntrustHistory";//委托历史

const entrustBuyStock = AppIp +"entrustBuyStock";//委托买入
const entrustSellStock = AppIp +"entrustSellStock";//委托卖出

const queryStockFuzzy = AppIp +"queryStockFuzzy";//模糊搜索
const queryH5Info = AppIp +"queryH5Info";
const sendAuthSmsCode = AppIp +"sendAuthSmsCode";//发送验证码

const queryClassListByMold = AppIp +"queryClassListByMold";//查询课堂列表
const queryClassInfoById = AppIp + "queryClassInfoById";//查询课堂详情
const queryUserInfo = AppIp +"queryUserInfo";//查询用户信息
const queryUserIncomeArr = AppIp + "queryUserIncomeArr";//查询用户的收益曲线
const queryMyLeaders = AppIp + "queryMyLeaders";//查询我的leaders
const attenUser = AppIp +"attenUser";//关注某人
const uploadWeChatFormId = AppIp +"uploadWeChatFormId";
const queryMinAllInfo = AppIp +"queryMinAllInfo";
const checkMatch = AppIp +"checkMatch";
//===================================================================================================
const queryStockInfoByCode = stockIp +"stockServer/queryStockInfoByCode";

module.exports = {
  loginUrl:loginUrl,
  queryApplyMatch: queryApplyMatch,
  applyMatch: applyMatch,
  queryHomeInfo: queryHomeInfo,
  queryMyHolderInfos: queryMyHolderInfos,
  queryMyEntrust: queryMyEntrust,
  revokeMyEntrust: revokeMyEntrust,
  queryMyEntrustHistory: queryMyEntrustHistory,

  entrustBuyStock: entrustBuyStock,
  entrustSellStock: entrustSellStock,
  queryStockFuzzy: queryStockFuzzy,
  queryH5Info: queryH5Info,
  sendAuthSmsCode: sendAuthSmsCode,
  queryClassListByMold: queryClassListByMold,
  queryClassInfoById: queryClassInfoById,
  queryUserInfo: queryUserInfo,
  queryUserIncomeArr: queryUserIncomeArr,
  queryMyLeaders:queryMyLeaders,
  attenUser: attenUser,
  uploadWeChatFormId: uploadWeChatFormId,
  queryMinAllInfo: queryMinAllInfo,
  checkMatch: checkMatch,
  urlSinaStockIndexList: urlSinaStockIndexList,
  urlSinaStockUpRanking: urlSinaStockUpRanking,
  urlSinaStockDownRanking: urlSinaStockDownRanking,
  //================================================================================================
  queryStockInfoByCode: queryStockInfoByCode
}
