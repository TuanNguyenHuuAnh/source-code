SELECT *
FROM M_INTRODUCTION_CATEGORY cate
WHERE
	cate.delete_date is NULL
	AND cate.customer_type_id = /*customerTypeId*/
	/*IF typeOfMain != null && typeOfMain != -1*/
	AND cate.TYPE_OF_MAIN = /*typeOfMain*/
	/*END*/
	/*IF typeOfMain == -1*/
	AND cate.TYPE_OF_MAIN is NULL
	/*END*/
	/*IF pictureIntroduction != null && pictureIntroduction != -1*/
	AND cate.PICTURE_INTRODUCTION = /*pictureIntroduction*/
	/*END*/
	/*IF pictureIntroduction == -1*/
	AND cate.PICTURE_INTRODUCTION is NULL
	/*END*/