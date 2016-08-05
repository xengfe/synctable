package com.yeecare.slave.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yeecare.slave.dao.BloodglucoseMapper;
import com.yeecare.slave.pojo.Bloodglucose;
import com.yeecare.slave.service.IBloodglucoseService;
@Service("slaveService")
public class BloodglucoseService implements IBloodglucoseService {
	
	@Resource
	private BloodglucoseMapper slaveDao;

	public BloodglucoseService() {
	}

	@Override
	public List<Bloodglucose> query() {
		return slaveDao.query();
	}

	@Override
	public int save(Bloodglucose bloodglucose) {
		return slaveDao.insert(bloodglucose);
	}

	@Override
	public int batchSave(List<Bloodglucose> list) {
		return slaveDao.batchSave(list);
	}

	@Override
	public Bloodglucose selectByPrimaryKey(String cId) {
		return slaveDao.selectByPrimaryKey(cId);
	}

}
