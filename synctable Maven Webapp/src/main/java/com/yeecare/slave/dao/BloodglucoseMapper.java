package com.yeecare.slave.dao;

import java.util.List;

import com.yeecare.slave.pojo.Bloodglucose;

public interface BloodglucoseMapper {
    int deleteByPrimaryKey(String cId);

    int insert(Bloodglucose record);

    int insertSelective(Bloodglucose record);

    Bloodglucose selectByPrimaryKey(String cId);

    int updateByPrimaryKeySelective(Bloodglucose record);

    int updateByPrimaryKey(Bloodglucose record);
    
    List<Bloodglucose> query();
    
    int batchSave(List<Bloodglucose> list);
}