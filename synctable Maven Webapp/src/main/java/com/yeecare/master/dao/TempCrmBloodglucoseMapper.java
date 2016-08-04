package com.yeecare.master.dao;

import java.util.List;

import com.yeecare.master.pojo.TempCrmBloodglucose;

public interface TempCrmBloodglucoseMapper {
    int deleteByPrimaryKey(String cId);

    int insert(TempCrmBloodglucose record);

    int insertSelective(TempCrmBloodglucose record);

    TempCrmBloodglucose selectByPrimaryKey(String cId);

    int updateByPrimaryKeySelective(TempCrmBloodglucose record);

    int updateByPrimaryKey(TempCrmBloodglucose record);
    
    List<TempCrmBloodglucose> query();
    
    int batchDeleteById(List<TempCrmBloodglucose> list);
    
}