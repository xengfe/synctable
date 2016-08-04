package com.yeecare.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yeecare.master.pojo.TempCrmBloodglucose;
import com.yeecare.master.service.ICrmBloodglucoseService;
import com.yeecare.slave.pojo.Bloodglucose;
import com.yeecare.slave.service.IBloodglucoseService;


@Controller
@RequestMapping("synchronized")
public class DatabaseController {
	
	private static final int  OK = 0;
	private static final int  ERROR = 1;
	private boolean isRunning = false;
	
	@Resource
	private ICrmBloodglucoseService masterService;
	
	@Resource
	private IBloodglucoseService slaveService;
	private Timer timer;

	public DatabaseController() {
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/start", method = { RequestMethod.POST })
	public Map<String, Object> start(@RequestParam(value = "period", required = true) String period) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (period == null) {
			map.put("code", ERROR);
			map.put("msg", "请输入休眠时间！");
		}else{
			if (!isRunning) {
				
				TimerTask task = new TimerTask(){

					@Override
					public void run() {
						doBussiness(masterService,slaveService);
					}};
					timer = new Timer();
					timer.schedule(task, 0, Integer.valueOf(period));
					
					map.put("code", OK);
					map.put("msg", "同步开始...");
					isRunning = true;
			}else {
				map.put("code", OK);
				map.put("msg", "同步正在进行...");
			}
		}
		
		
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/stop", method = { RequestMethod.POST })
	public Map<String, Object> stop() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (timer!= null) {
			timer.cancel();
			map.put("code", OK);
			map.put("msg", "暂停成功");
		}else {
			map.put("code", ERROR);
			map.put("msg", "程序暂无启动");
		}
		isRunning = false; 
		return map;
	}
	
	
	
	private void doBussiness(ICrmBloodglucoseService masterService,IBloodglucoseService slaveService){
		
		List<TempCrmBloodglucose> masterList = masterService.query();
		
		List<Bloodglucose> slavelList = slaveService.query();
		
		List<TempCrmBloodglucose> sourceDeleteData = new ArrayList<TempCrmBloodglucose>();
		// compare data for different data
		for (Iterator<TempCrmBloodglucose> iter = masterList.listIterator(); iter.hasNext();) {
			TempCrmBloodglucose crmBloodglucose = iter.next();
			for (Bloodglucose bloodglucose : slavelList) {
				if (bloodglucose.getcId().equals(crmBloodglucose.getcId())) {
					System.out.println("相同数据：" + crmBloodglucose.getcId());
					sourceDeleteData.add(crmBloodglucose);
					iter.remove();
				}
			}
		}
		
		//save new data into slave database
		List<Bloodglucose> insertDataList = new ArrayList<Bloodglucose>();
		
		for (TempCrmBloodglucose newBloodglucose : masterList) {
			Bloodglucose newBloodglucose2 = new Bloodglucose();
			newBloodglucose2.setcId(newBloodglucose.getcId());
			newBloodglucose2.setcUid(newBloodglucose.getcUid());
			newBloodglucose2.setcDid(newBloodglucose.getcDid());
			newBloodglucose2.setcDsync(newBloodglucose.getcDsync());
			newBloodglucose2.setcGlu(newBloodglucose.getcGlu());
			newBloodglucose2.setcTime(newBloodglucose.getcTime());
			newBloodglucose2.setcFlag(newBloodglucose.getcFlag());
			newBloodglucose2.setcRes(newBloodglucose.getcRes());
			newBloodglucose2.setcUpload(newBloodglucose.getcUpload());
			newBloodglucose2.setcClientip(newBloodglucose.getcClientip());
			newBloodglucose2.setcCtype(newBloodglucose.getcCtype());
			
			insertDataList.add(newBloodglucose2);
			
			int result = slaveService.save(newBloodglucose2);
			
			if (result!=0) {
				System.out.println("保存成功！");
			}else {
				System.out.println("保存失败！");
			}
			
		}
//		int result = slaveService.batchSave(insertDataList);
//		
//		if (result!=0) {
//			System.out.println("保存成功！");
//		}else {
//			System.out.println("保存失败！");
//		}
		//delete master data
		
		for (Bloodglucose bloodglucose : insertDataList) {
			int deleteResult = masterService.deleteById(bloodglucose.getcId());
			if (deleteResult!= 0) {
				System.out.println("删除成功");
			}else {
				System.out.println("删除失败");
			}
		}
//		
//		int deleteResult = masterService.batchDeleteById(sourceDeleteData);
//		if (deleteResult!= 0) {
//			System.out.println("删除成功");
//		}else {
//			System.out.println("删除失败");
//		}
	}
	
	


}
