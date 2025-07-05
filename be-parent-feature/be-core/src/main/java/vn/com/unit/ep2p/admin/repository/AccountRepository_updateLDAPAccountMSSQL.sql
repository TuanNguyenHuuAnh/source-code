UPDATE jca_account
SET 
	/*IF account.fullname != null && account.fullname != ''*/
	fullname = /*account.fullname*/, 
	/*END*/
	/*IF account.email != null && account.email != ''*/
	email = /*account.email*/, 
	/*END*/
	/*IF account.actived != null && account.actived != ''*/
	actived = /*account.actived*/,
	/*END*/
	/*IF account.enabled != null && account.enabled != ''*/
	enabled = /*account.enabled*/
	/*END*/
WHERE username = /*account.username*/