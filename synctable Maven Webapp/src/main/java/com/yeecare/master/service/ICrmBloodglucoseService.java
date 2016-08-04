package com.yeecare.master.service;

import java.util.List;

import com.yeecare.master.pojo.TempCrmBloodglucose;

public interface ICrmBloodglucoseService {
	
	public List<TempCrmBloodglucose> query();
	
	public int deleteById(String id);
	
	public int batchDeleteById(List<TempCrmBloodglucose> list);
	

}
