const AppIp ="http://192.168.1.177:8088/";

const loginUrl = AppIp + "loginWx";//登录

const queryApplyMatch = AppIp + "queryApplyMatchInfo";//查询报名比赛的信息
const applyMatch = AppIp + "applyMatch";//申请报名
const queryHomeInfo = AppIp +"queryHomeInfo";//查询主页信息

module.exports = {
  queryApplyMatch: queryApplyMatch,
  applyMatch: applyMatch,
  queryHomeInfo: queryHomeInfo
}
