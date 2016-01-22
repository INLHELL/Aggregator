package com.genesys.raa.agg;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.github.davidmoten.rx.jdbc.Database;

public class AggConfiguration {

	public AggConfiguration() {
		super();
	}

	public Map<String, String> getAggregateDefinitions(){
		Map<String, String> aggregateDefs = new HashMap<String, String>();
		aggregateDefs.put("CAMPAIGN", testSelectSql);
		return aggregateDefs;
	}

	public Properties getProperties() {
		return null;
	}
	
	public Database getDatabase() {
		return null;
	}

	public String getDatabaseTablePrefix() {
		// TODO Auto-generated method stub
		return "RAA";
	}
	
	String testSelectSql = "select dt.DATE_TIME_HOUR_KEY as DATE_TIME_KEY, " +
   "    caf.CAMPAIGN_KEY as CAMPAIGN_KEY,  " +
   "     caf.TENANT_KEY as TENANT_KEY, " +
   "     caf.GROUP_KEY as CAMPAIGN_GROUP_KEY, " +
   "     coalesce(irf.RESOURCE_GROUP_COMBINATION_KEY, caf.RESOURCE_GROUP_COMBINATION_KEY, -1) as GROUP_COMBINATION_KEY, " +
   "     coalesce(irf.RESOURCE_KEY, caf.RESOURCE_KEY) as RESOURCE_KEY, " +
   "     caf.MEDIA_TYPE_KEY as MEDIA_TYPE_KEY, " +
   "     coalesce(irf.INTERACTION_TYPE_KEY, it_.INTERACTION_TYPE_KEY) as INTERACTION_TYPE_KEY, " +
   "     coalesce(irfud.INTERACTION_DESCRIPTOR_KEY, -2) as INTERACTION_DESCRIPTOR_KEY, " +
   "     coalesce(-2,-2) as USER_DATA_KEY1, " +
   "     coalesce(-2,-2) as USER_DATA_KEY2, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when irf.CUSTOMER_HANDLE_COUNT > 0 then 1 else 0 end) as ACCEPTED, " +
   "     sum(case when dm.DIALING_MODE_CODE in ('PREVIEW' , 'PUSH_PREVIEW') and coalesce(irf.IRF_ANCHOR,1) = 1 then 1 else 0 end) as PREVIEW, " +
   "     sum(case when dm.DIALING_MODE_CODE in ('PREVIEW' , 'PUSH_PREVIEW') and coalesce(irf.IRF_ANCHOR,1) = 1 then coalesce(itf.START_TS, caf.END_TS) - caf.START_TS else 0 end) as PREVIEW_TIME, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when irf.CUSTOMER_DIAL_COUNT + irf.CUSTOMER_RING_COUNT + irf.CUSTOMER_TALK_COUNT + irf.CUSTOMER_HANDLE_COUNT > 0 then 1 else 0 end) as OFFERED, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 else irf.CUSTOMER_RING_COUNT + irf.CUSTOMER_DIAL_COUNT end) as INVITE, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 else irf.CUSTOMER_RING_DURATION + irf.CUSTOMER_DIAL_DURATION end) as INVITE_TIME, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 else irf.CUSTOMER_TALK_DURATION end) as ENGAGE_TIME, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when irf.CUSTOMER_HANDLE_COUNT > 0 and (irf.CUSTOMER_TALK_DURATION <= scfg.INT_VAL_01 and scfg.INT_VAL_01 > 0) then irf.CUSTOMER_TALK_COUNT else 0 end) as SHORT, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 else CUSTOMER_HOLD_DURATION end) as HOLD_TIME, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when irf.CUSTOMER_HOLD_COUNT > 0 then 1 else 0 end) as HOLD, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 else irf.CUSTOMER_ACW_DURATION end) as WRAP_TIME, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 else irf.CUSTOMER_ACW_COUNT end) as WRAP, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when mt.IS_ONLINE = 1 and irf.CUSTOMER_HANDLE_COUNT = 0 and irf.CONS_RCV_TALK_COUNT + irf.TALK_COUNT + irf.POST_CONS_XFER_TALK_COUNT + irf.CONF_JOIN_TALK_COUNT > 0 then 1 when mt.IS_ONLINE = 0 and it.INTERACTION_SUBTYPE_CODE <> 'INTERNALCOLLABORATIONREPLY' and irf.CONS_RCV_TALK_COUNT > 0 then 1 else 0 end) as CONSULT_RECEIVED_ACCEPTED, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when mt.IS_ONLINE = 1 and irf.CUSTOMER_HANDLE_COUNT = 0 then irf.CONS_RCV_TALK_DURATION + irf.TALK_DURATION + irf.POST_CONS_XFER_TALK_DURATION + irf.CONF_JOIN_TALK_DURATION when mt.IS_ONLINE = 0 then irf.CONS_RCV_TALK_DURATION else 0 end) as CONSULT_RECEIVED_ENGAGE_TIME, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when irf.CONS_RCV_ACW_COUNT + irf.AFTER_CALL_WORK_COUNT > 0 and irf.CUSTOMER_HANDLE_COUNT = 0 then 1 else 0 end) as CONSULT_RECEIVED_WRAP, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when irf.CUSTOMER_HANDLE_COUNT = 0 and irf.CUSTOMER_RING_COUNT = 0 and td.RESOURCE_ROLE_CODE <> 'INITIATEDCONSULT' then irf.CONS_RCV_ACW_DURATION + irf.AFTER_CALL_WORK_DURATION else 0 end) as CONSULT_RECEIVED_WRAP_TIME, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when irf.CUSTOMER_HANDLE_COUNT = 0 then irf.CONS_RCV_HOLD_DURATION + irf.HOLD_DURATION + irf.POST_CONS_XFER_HOLD_DURATION + irf.CONF_JOIN_HOLD_DURATION else 0 end) as CONSULT_RECEIVED_HOLD_TIME, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when irf.CUSTOMER_HANDLE_COUNT = 0 and irf.CONS_RCV_HOLD_COUNT + irf.HOLD_COUNT + irf.POST_CONS_XFER_HOLD_DURATION + irf.CONF_JOIN_HOLD_COUNT > 0 then 1 else 0 end) as CONSULT_RECEIVED_HOLD, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when irf.CUSTOMER_HANDLE_COUNT > 0 and irf.CONS_RCV_TALK_COUNT > 0 then irf.CONS_RCV_TALK_DURATION else 0 end) as CONSULT_RCV_WARM_ENGAGE_TIME, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when irf.CUSTOMER_HANDLE_COUNT > 0 and irf.CONS_RCV_TALK_COUNT > 0 then 1 else 0 end) as CONSULT_RCV_ACC_WARM, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when irf.CUSTOMER_HANDLE_COUNT > 0 and irf.CONS_RCV_HOLD_COUNT > 0 then irf.CONS_RCV_HOLD_DURATION else 0 end) as CONSULT_RCV_WARM_HOLD_TIME, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when irf.CUSTOMER_HANDLE_COUNT > 0 and irf.CONS_RCV_HOLD_COUNT > 0 then 1 else 0 end) as CONSULT_RCV_WARM_HOLD, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when irf.CONS_RCV_ACW_COUNT >0 and irf.CUSTOMER_HANDLE_COUNT > 0 then 1 else 0 end) as CONSULT_RCV_WARM_WRAP, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when irf.CUSTOMER_HANDLE_COUNT > 0 and td.RESOURCE_ROLE_CODE <> 'INITIATEDCONSULT' then irf.CONS_RCV_ACW_DURATION else 0 end) as CONSULT_RCV_WARM_WRAP_TIME, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when irf.CUSTOMER_HANDLE_COUNT > 0 and td.TECHNICAL_RESULT_CODE= 'TRANSFERRED' then 1 else 0 end) as TRANSFER_INIT_AGENT, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when irf.CUSTOMER_DIAL_COUNT + irf.CUSTOMER_RING_COUNT + irf.CUSTOMER_TALK_COUNT + irf.CUSTOMER_HANDLE_COUNT > 0 then (coalesce(cast(irfug.SATISFACTION as integer) , 0)) else 0 end) as SATISFACTION, " +
   "     sum(case when irf.INTERACTION_RESOURCE_ID is null then 0 when irf.CUSTOMER_DIAL_COUNT + irf.CUSTOMER_RING_COUNT + irf.CUSTOMER_TALK_COUNT + irf.CUSTOMER_HANDLE_COUNT > 0 then (coalesce(cast(irfug.REVENUE as integer) , 0)) else 0 end) as REVENUE " +
   " from CONTACT_ATTEMPT_FACT caf " +
   " inner join DATE_TIME  dt  on(caf.START_DATE_TIME_KEY = dt.DATE_TIME_KEY and dt.DATE_TIME_KEY = ?) " +
   " inner join DIALING_MODE  dm  on(dm.DIALING_MODE_KEY = caf.DIALING_MODE_KEY) " +
   " inner join INTERACTION_TYPE  it_  on(it_.INTERACTION_TYPE_CODE = 'OUTBOUND' and it_.INTERACTION_SUBTYPE_CODE = 'OUTBOUNDCONTACT') " +
   " left outer join IRF_USER_DATA_GEN_1  irfug  on(irfug.GSW_CALL_ATTEMPT_GUID = caf.CALL_ATTEMPT_ID) " +
   " left outer join INTERACTION_RESOURCE_FACT  irf  on(irf.INTERACTION_RESOURCE_ID = irfug.INTERACTION_RESOURCE_ID) " +
   " left outer join TECHNICAL_DESCRIPTOR  td  on(irf.TECHNICAL_DESCRIPTOR_KEY = td.TECHNICAL_DESCRIPTOR_KEY) " +
   " left outer join MEDIA_TYPE  mt  on(mt.MEDIA_TYPE_KEY = irf.MEDIA_TYPE_KEY) " +
   " left outer join INTERACTION_TYPE  it  on(it.INTERACTION_TYPE_KEY = irf.INTERACTION_TYPE_KEY) " +
   " left outer join AGR_SCFG_MAP MAP1$scfg on (MAP1$scfg.SCFG_TYPE_CODE='AGENT-IXN' and MAP1$scfg.SCFG_ORIGIN_SELECTOR='by-tenant-media' and MAP1$scfg.OBJ_ID=(irf.TENANT_KEY * 10000 + irf.MEDIA_TYPE_KEY)) " +
   " left outer join AGR_SCFG_MAP MAP2$scfg on (MAP2$scfg.SCFG_TYPE_CODE='AGENT-IXN' and MAP2$scfg.SCFG_ORIGIN_SELECTOR='by-tenant' and MAP2$scfg.OBJ_ID=irf.TENANT_KEY) " +
   " left outer join AGR_SCFG_MAP MAP3$scfg on (MAP3$scfg.SCFG_TYPE_CODE='AGENT-IXN' and MAP3$scfg.SCFG_ORIGIN_SELECTOR='by-app-media' and MAP3$scfg.OBJ_ID=irf.MEDIA_TYPE_KEY) " +
   " left outer join AGR_SCFG_MAP MAP4$scfg on (MAP4$scfg.SCFG_TYPE_CODE='AGENT-IXN' and MAP4$scfg.SCFG_ORIGIN_SELECTOR='app-default' and MAP4$scfg.OBJ_ID=1) " +
   " left outer join AGR_SCFG_MAP MAP5$scfg on (MAP5$scfg.SCFG_TYPE_CODE='AGENT-IXN' and MAP5$scfg.SCFG_ORIGIN_SELECTOR='default' and MAP5$scfg.OBJ_ID=1) " +
   " left outer join AGR_SCFG_SCHEDULE SCH$scfg on (SCH$scfg.SCFG_GROUP_KEY=coalesce(MAP1$scfg.SCFG_GROUP_KEY,MAP2$scfg.SCFG_GROUP_KEY,MAP3$scfg.SCFG_GROUP_KEY,MAP4$scfg.SCFG_GROUP_KEY,MAP5$scfg.SCFG_GROUP_KEY,-1) and SCH$scfg.END_DATE_TIME_KEY = 2147483647) " +
   " left outer join AGR_SCFG scfg on (SCH$scfg.SCFG_KEY=scfg.SCFG_KEY) " +
   " left outer join INTERACTION_FACT  itf  on(irf.INTERACTION_ID = itf.INTERACTION_ID) " +
   " left outer join IRF_USER_DATA_KEYS  irfud  on(irf.INTERACTION_RESOURCE_ID = irfud.INTERACTION_RESOURCE_ID) " +
   " where coalesce(irf.RESOURCE_KEY,caf.RESOURCE_KEY) in (select r_.RESOURCE_KEY from RESOURCE_ r_ where r_.RESOURCE_TYPE_CODE = 'AGENT') and not ((irf.INTERACTION_RESOURCE_ID is null and dm.DIALING_MODE_CODE not in ('PREVIEW' , 'PUSH_PREVIEW')) or (coalesce(irf.CONS_RCV_RING_DURATION,0) > 0 and coalesce(irf.CONS_RCV_TALK_COUNT,0) + coalesce(irf.CUSTOMER_RING_COUNT,0) = 0) or (coalesce(irf.CUSTOMER_RING_COUNT,0) = 0 and coalesce(irf.RING_COUNT,0) > 0 and coalesce(irf.TALK_COUNT,0) = 0) or (coalesce(irf.CONS_INIT_DIAL_COUNT,0) > 0 and coalesce(irf.CONS_INIT_TALK_COUNT,0) = 0) or (coalesce(it.INTERACTION_SUBTYPE_CODE,it_.INTERACTION_SUBTYPE_CODE) = 'INTERNALCOLLABORATIONREPLY' and coalesce(irf.CONS_RCV_TALK_DURATION,0) = 0)) " +
   " group by dt.DATE_TIME_HOUR_KEY, coalesce(irf.RESOURCE_GROUP_COMBINATION_KEY, caf.RESOURCE_GROUP_COMBINATION_KEY, -1), coalesce(irf.RESOURCE_KEY, caf.RESOURCE_KEY), coalesce(irfud.INTERACTION_DESCRIPTOR_KEY, -2), caf.CAMPAIGN_KEY, caf.TENANT_KEY, caf.GROUP_KEY, caf.MEDIA_TYPE_KEY, coalesce(irf.INTERACTION_TYPE_KEY, it_.INTERACTION_TYPE_KEY)"; 
}
