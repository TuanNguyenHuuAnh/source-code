UPDATE 
	jca_m_org
SET 
	org_code = /*orgInfo.orgCode*/,
	org_name = /*orgInfo.orgName*/,
	org_name_abv = /*orgInfo.orgNameAbv*/,
	del_flg = /*orgInfo.delFlg*/,
	effected_date = /*orgInfo.effectedDate*/,
	expired_date = /*orgInfo.expiredDate*/,
	org_type = /*orgInfo.orgType*/,
	org_sub_type_1 = /*orgInfo.orgSubType1*/,
	email = /*orgInfo.email*/,
	phone = /*orgInfo.phone*/,
	address = /*orgInfo.address*/,
	surrogate = /*orgInfo.surrogate*/,
	latitude = /*orgInfo.latitude*/,
	longtitude = /*orgInfo.longtitude*/,
	PARENT_ORG_ID=/*orgInfo.parentOrgId*/
WHERE
	id = /*orgInfo.id*/