package com.yeecare.master.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yeecare.master.dao.TempCrmBloodglucoseMapper;
import com.yeecare.master.pojo.TempCrmBloodglucose;
import com.yeecare.master.service.ICrmBloodglucoseService;
@Service("masterService")
public class CrmBloodglucoseService implements ICrmBloodglucoseService {
	
	@Resource
	private TempCrmBloodglucoseMapper masterDao;

	public CrmBloodglucoseService() {
	}

	@Override
	public List<TempCrmBloodglucose> query() {
		return masterDao.query();
	}

	@Override
	public int deleteById(String id) {
		return masterDao.deleteByPrimaryKey(id);
	}

	@Override
	public int batchDeleteById(List<TempCrmBloodglucose> list) {
		return masterDao.batchDeleteById(list);
	}


}
