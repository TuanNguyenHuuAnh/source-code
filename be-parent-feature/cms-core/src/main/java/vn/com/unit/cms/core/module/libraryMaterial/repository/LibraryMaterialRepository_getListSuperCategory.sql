SELECT 
	 cate.ID			as id
	,cate.PARENT_ID		as parent_id
	,cateLang.TITLE		as title
FROM M_DOCUMENT_CATEGORY cate
INNER JOIN M_DOCUMENT_CATEGORY_LANGUAGE cateLang 
	ON cate.id = cateLang.M_CATEGORY_ID
	AND cateLang.DELETE_BY is null
	AND cateLang.M_LANGUAGE_CODE = /*language*/
WHERE 
	CATE.PARENT_ID = /*parentId*/
    /*IF categoryType != null && categoryType !=''*/
	 	 AND CATE.CATEGORY_TYPE = /*categoryType*/'AQP2112.0001'
	 /*END*/
    /*IF categoryType == null or categoryType ==''*/
  		 AND CATE.CATEGORY_TYPE IN ('LBR2113.0001', 'JER2112.0001') -- LAY DANH SACH thuoc thu vien tai lieu VÀ danh muc hoc/thi
  		 AND  CATE.CATEGORY_TYPE NOT IN ('AQP2112.0001') --#62741
  	 /*END*/
	AND CATE.DELETE_BY IS NULL
	/*IF isCandidate == true*/
	    AND ISNULL(FOR_CANDIDATE, 0) = 1
	/*END*/
	/*IF channel == null || channel == ''*/
	AND isnull(CATE.CHANNEL, 'AG') = 'AG'
	/*END*/
	/*IF channel != null && channel == 'AG'*/
	AND isnull(CATE.CHANNEL, 'AG') = /*channel*/
	/*END*/
	/*IF channel != null && channel == 'AD'*/
	AND CATE.CHANNEL = /*channel*/
	/*END*/
	/*IF partners != null*/
 	AND CATE.PARTNER in /*partners*/()
 	/*END*/
order by cate.SORT asc