SELECT
    P_ZIP.PROVINCE		                                                      AS	ID
 ,  P_ZIP.NAME			  AS	TEXT
 ,  P_ZIP.NAME			  AS	NAME
FROM STG_ING.ZIPCODE P_ZIP

WHERE P_ZIP.WARD = 0
  AND P_ZIP.DISTRICT = 0
    /*IF city != null && city != ''*/
  AND P_ZIP.NAME	 = /*zipCode*/
    /*END*/
     ORDER BY P_ZIP.NAME
