package com.cmsz.cloudplatform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.cloudplatform.model.WorkItem;
import com.cmsz.cloudplatform.model.WorkOrder;
import com.cmsz.cloudplatform.service.AbstractSAManager;
import com.cmsz.cloudplatform.service.SAAsynManager;
import com.cmsz.cloudplatform.service.WorkItemManager;
import com.cmsz.cloudplatform.utils.ServiceOptionUtil;
@Service(value="saDomainLimitImpl")

public class SADomainLimitImpl extends AbstractSAManager {

	public WorkItemManager getItemManager() {
		return itemManager;
	}
	@Autowired
	public void setItemManager(WorkItemManager itemManager) {
		this.itemManager = itemManager;
	}

	private WorkItemManager itemManager=null;
		
	
    public Map<String,Object[]> getProvisionAttributes(Map<String,Object[]> cloudStackParams, WorkOrder workOrder){
    	String domainid="";
    	WorkItem wiExample=new WorkItem();
		wiExample.setWorkOrderId(workOrder.getId());
		wiExample.setAttributeName("domainId");
		List<WorkItem> wiList=(List<WorkItem>) itemManager.findByExample(wiExample);
		for(WorkItem wi:wiList){
			domainid=wi.getAttributeValue();
		}
    	cloudStackParams.put("domainid", new Object[]{domainid});
    	return cloudStackParams;
    }
	public void putUserCredentialParams(Map<String,Object[]> cloudStackParams,WorkOrder workOrder){

		this.putUserCredentialParams(cloudStackParams,ServiceOptionUtil.obtainCloudStackUserId());
	}
}
