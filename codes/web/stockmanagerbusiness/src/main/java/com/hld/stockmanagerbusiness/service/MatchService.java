package com.hld.stockmanagerbusiness.service;

public interface MatchService {
    int applyMatch(long userId,long matchId,String name,String phoneNum,String authNum,String profession,String stuClass,String stuNum);
}
