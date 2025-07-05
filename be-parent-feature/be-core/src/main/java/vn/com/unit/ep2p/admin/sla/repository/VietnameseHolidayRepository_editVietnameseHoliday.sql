UPDATE SLA_DAY_OFF  	
  SET description = /*vietnameseHoliday.description*/,  
  	  COMPANY_ID = /*vietnameseHoliday.companyId*/,
  	  CALENDAR_TYPE_ID = /*vietnameseHoliday.calendarType*/,
      updateD_By    = /*vietnameseHoliday.updatedBy*/,
      updateD_Date  = /*vietnameseHoliday.updatedDate*/  
  WHERE VIETNAMESEHOLIDAYDATE = /*vietnameseHoliday.vietnameseHolidayDate*/
;