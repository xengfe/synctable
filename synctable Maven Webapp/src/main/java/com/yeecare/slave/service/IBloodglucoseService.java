package com.yeecare.slave.service;

import java.util.List;

import com.yeecare.slave.pojo.Bloodglucose;

public interface IBloodglucoseService {
	
	public List<Bloodglucose> query();
	
	public int save(Bloodglucose bloodglucose);
	
	public int batchSave(List<Bloodglucose> list);
	
}
