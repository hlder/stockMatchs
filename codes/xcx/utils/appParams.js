const AppIp ="http://192.168.1.177:8088/";
const stockIp = "http://47.100.180.170:8080/";
// stockServer/queryStockInfoByCode?stockCode=sz002471

const loginUrl = AppIp + "loginWx";//登录

const queryApplyMatch = AppIp + "queryApplyMatchInfo";//查询报名比赛的信息
const applyMatch = AppIp + "applyMatch";//申请报名
const queryHomeInfo = AppIp +"queryHomeInfo";//查询主页信息
const queryMyHolderInfos = AppIp +"queryMyHolderInfos";//查询我的持仓
const queryMyEntrust = AppIp +"queryMyEntrust";//查询我的委托
const revokeMyEntrust = AppIp +"revokeMyEntrust";//撤单
const queryMyEntrustHistory = AppIp +"queryMyEntrustHistory";//委托历史

//===================================================================================================
const queryStockInfoByCode = stockIp +"stockServer/queryStockInfoByCode";

module.exports = {
  queryApplyMatch: queryApplyMatch,
  applyMatch: applyMatch,
  queryHomeInfo: queryHomeInfo,
  queryMyHolderInfos: queryMyHolderInfos,
  queryMyEntrust: queryMyEntrust,
  revokeMyEntrust: revokeMyEntrust,
  queryMyEntrustHistory: queryMyEntrustHistory,
  //================================================================================================
  queryStockInfoByCode: queryStockInfoByCode
}
