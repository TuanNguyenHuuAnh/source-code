select dms_partner.CODE as CODE	 
		,dms_partner.NAME as NAME
from STG_DMS.DMS_PARTNER dms_partner 
where isnull(EXPIRED_DATE,'9999-12-31') between getdate() and '9999-12-31'
and dms_partner.CHANNEL = /*channel*/