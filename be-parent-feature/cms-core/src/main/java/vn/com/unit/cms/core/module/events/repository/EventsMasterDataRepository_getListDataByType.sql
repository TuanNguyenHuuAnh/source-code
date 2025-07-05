SELECT
	ma.TYPE
	, ma.CODE
	, ma.NAME
FROM M_EVENTS_MASTERDATA ma
where ma.ACTIVE = 1
/*IF type != null && type != ''*/
AND  ma.TYPE = /*type*/'OBJECT_EVENT'
/*END*/
/*IF parentId != null && parentId != ''*/
AND  ma.PARENT_ID = /*parentId*/'M'
/*END*/
/*IF code != null && code != ''*/
AND  ma.CODE = /*code*/'1'
/*END*/