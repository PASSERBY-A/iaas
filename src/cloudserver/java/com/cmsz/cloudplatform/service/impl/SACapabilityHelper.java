package com.cmsz.cloudplatform.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.cloudplatform.model.WorkItem;
import com.cmsz.cloudplatform.model.WorkOrder;
import com.cmsz.cloudplatform.model.request.ListWorkOrderRequest;
import com.cmsz.cloudplatform.model.response.ListResponse;
import com.cmsz.cloudplatform.model.response.Response;
import com.cmsz.cloudplatform.service.AbstractSAManager;
import com.cmsz.cloudplatform.service.ProvisionAttributeManager;
import com.cmsz.cloudplatform.service.ResourcePoolPermissionManager;
import com.cmsz.cloudplatform.service.SAManager;
import com.cmsz.cloudplatform.service.WorkItemManager;
import com.cmsz.cloudplatform.service.WorkOrderManager;
import com.cmsz.cloudplatform.utils.Compare;
@Service(value="saCapabilityHelper")
public class SACapabilityHelper  {

	@Autowired
	private GenericCloudServerManagerImpl genericCloudServerManager;

	@Autowired
	private ResourcePoolPermissionManager resourcePoolPermissionManager;

	@Autowired
	public ResourcePoolPermissionManager getResourcePoolPermissionManager() {
		return resourcePoolPermissionManager;
	}

	public void setResourcePoolPermissionManager(
			ResourcePoolPermissionManager resourcePoolPermissionManager) {
		this.resourcePoolPermissionManager = resourcePoolPermissionManager;
	}

	public void setGenericCloudServerManager(
			GenericCloudServerManagerImpl genericCloudServerManager) {

		this.genericCloudServerManager = genericCloudServerManager;
	}

	private WorkOrderManager orderManager = null;

	public WorkOrderManager getOrderManager() {
		return orderManager;
	}

	@Autowired
	public void setOrderManager(WorkOrderManager orderManager) {
		this.orderManager = orderManager;
	}

	public WorkItemManager getItemManager() {
		return itemManager;
	}

	@Autowired
	public void setItemManager(WorkItemManager itemManager) {
		this.itemManager = itemManager;
	}

	private WorkItemManager itemManager = null;

	private ProvisionAttributeManager provisionAttributeManager = null;

	public ProvisionAttributeManager getProvisionAttributeManager() {
		return provisionAttributeManager;
	}

	@Autowired
	public void setProvisionAttributeManager(
			ProvisionAttributeManager provisionAttributeManager) {
		this.provisionAttributeManager = provisionAttributeManager;
	}


	public LinkedHashMap<String, Object[]> refreshCloudStackParams(
			Map<String, Object[]> cloudStackParams) {
		Object[] apikey = cloudStackParams.get("apikey");
		Object[] secretkey = cloudStackParams.get("secretkey");
		Object[] sessionkey = cloudStackParams.get("sessionkey");
		Object[] response = cloudStackParams.get("response");
		LinkedHashMap<String, Object[]> cloudStackParams2 = new LinkedHashMap<String, Object[]>();
		cloudStackParams2.put("apikey", apikey);
		cloudStackParams2.put("secretkey", secretkey);
		cloudStackParams2.put("sessionkey", sessionkey);
		cloudStackParams2.put("response", response);
		return cloudStackParams2;
	}

	public LinkedHashMap<String, String> getCapabilityMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

		map.put("instance", "0");
		map.put("ip", "1");
		map.put("volume", "2");
		map.put("snapshot", "3");
		map.put("template", "4");
		// map.put("Network", "5");
		map.put("network", "6");
		map.put("vpc", "7");
		map.put("cpu", "8");
		map.put("memory", "9");
		map.put("primaryStorage", "10");
		map.put("secondaryStorage", "11");
		/*Type of resource to update. Values are 0, 1, 2, 3, 4, 6, 7, 8, 9, 10 and 11.
		 *  0 - Instance. Number of instances a user can create. 
		 *  1 - IP. Number of public IP addresses a user can own. 
		 *  2 - Volume. Number of disk volumes a user can create.
		 *  3 - Snapshot. Number of snapshots a user can create.
		 *  4 - Template. Number of templates that a user can register/create.
		 *  6 - Network. Number of guest network a user can create.
		 *  7 - VPC. Number of VPC a user can create.
		 *  8 - CPU. Total number of CPU cores a user can use.
		 *  9 - Memory. Total Memory (in MB) a user can use.
		 *  10 - PrimaryStorage. Total primary storage space (in GiB) a user can use.
		 *  11 - SecondaryStorage. Total secondary storage space (in GiB) a user can use.*/
		return map;

	}

	public void active(Map<String, Object[]> cloudStackParams) {
		List<Integer> typeList=new ArrayList<Integer>();
		typeList.add(WorkOrder.TYPE_PROJECTLIMITAPPLICATION);
		typeList.add(WorkOrder.TYPE_ACCOUNTLIMITAPPLICATION);
		typeList.add(WorkOrder.TYPE_DOMAINLIMITAPPLICATION);
		List<Integer> statusList=new ArrayList<Integer>();
		statusList.add(WorkOrder.STATUS_APPROVED);
		List<WorkOrder> workOrderList=orderManager.listWorkOrderByTypeAndStatus(typeList, statusList);
		for(WorkOrder workOrder:workOrderList){
			try{
				SAManager saManager = this.getSAManager(workOrder
						.getWorkOrderType());
				saManager.putUserCredentialParams(cloudStackParams, workOrder);
				this.updateResourceLimit(cloudStackParams, workOrder,saManager);
			}catch(Exception ex){
				ex.printStackTrace();
				workOrder.setStatus(WorkOrder.STATUS_PROVISONFAIL);// 处理成功
				orderManager.save(workOrder);
				
			}
		}

	}

	public void updateResourceLimit(Map<String, Object[]> cloudStackParams,
			WorkOrder workOrder,SAManager saManager) {

		LinkedHashMap<String, String> map = this.getCapabilityMap();
		for (String capabilityName : map.keySet()) {
			WorkItem wiExample = new WorkItem();
			wiExample.setWorkOrderId(workOrder.getId());
			wiExample.setAttributeName(capabilityName);

			List<WorkItem> wil = (List<WorkItem>) itemManager
					.findByExample(wiExample);
			Collections.sort(wil, new Compare());
			cloudStackParams = this.refreshCloudStackParams(cloudStackParams);
			for (WorkItem wi : wil) {
				String attributeValue = wi.getAttributeValue();
				cloudStackParams.put("max", new Object[] { attributeValue });
			}
			cloudStackParams.put("command",
					new Object[] { "updateResourceLimit" });
			cloudStackParams.put("resourcetype",
					new Object[] { map.get(capabilityName) });
			saManager.getProvisionAttributes(cloudStackParams, workOrder);
			
			Response updateLimitResponse = genericCloudServerManager
					.get(cloudStackParams);
			JSONObject updateLimitResponseJSONObj = JSONObject
					.fromObject(updateLimitResponse.getResponseString());

			String updateresourcelimitresponseStr = updateLimitResponseJSONObj
					.getString("updateresourcelimitresponse");
			if (updateresourcelimitresponseStr.indexOf("errorcode") > -1) {
				JSONObject updateresourcelimitresponseObj = JSONObject
						.fromObject(updateresourcelimitresponseStr);
				String errorcode = updateresourcelimitresponseObj
						.getString("errorcode");
				String errortext = updateresourcelimitresponseObj
						.getString("errortext");
				workOrder.setStatus(WorkOrder.STATUS_PROVISONFAIL);// 处理失败
				workOrder.setErrorCode(errorcode);
				workOrder.setErrorText(errortext);
				orderManager.save(workOrder);
				

				return;

			}

		}
		workOrder.setStatus(WorkOrder.STATUS_PROVISIONSUCCEED);// 处理成功
		orderManager.save(workOrder);
	}

	public SAManager getSAManager(int workOrderType) {
		if (workOrderType == WorkOrder.TYPE_PROJECTLIMITAPPLICATION)
			return saProjectLimitImpl;
		else if (workOrderType == WorkOrder.TYPE_DOMAINLIMITAPPLICATION)
			return saDomainLimitImpl;
		else if (workOrderType == WorkOrder.TYPE_ACCOUNTLIMITAPPLICATION)
			return saAccountLimitImpl;
		else
			return null;
	}

	@Autowired
	private SAManager saProjectLimitImpl;
	@Autowired
	private SAManager saDomainLimitImpl;
	@Autowired
	private SAManager saAccountLimitImpl;

}
