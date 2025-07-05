package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.ContactBookingDto;
import vn.com.unit.cms.admin.all.dto.ContactBookingSearchDto;
import vn.com.unit.cms.admin.all.entity.ContactBooking;
import vn.com.unit.db.repository.DbRepository;

public interface ContactBookingRepository extends DbRepository<ContactBooking, Long>{
	public List<ContactBooking> findAllBooking();

	/**
	 * count by search condition
	 * @param searchCondition
	 * @return
	 */
	public int countBySearchCondition(@Param("searchCondition") ContactBookingSearchDto searchCondition);

	/**
	 * find list by search condition, limit and offset with input page number and item count
	 * @param offsetSQL
	 * @param sizeOfPage
	 * @param searchCondition
	 * @return
	 */
	public List<ContactBooking> findAllBookingByCondition(@Param("offsetSQL") int offsetSQL, @Param("sizeOfPage") int sizeOfPage,
			@Param("searchCondition") ContactBookingSearchDto searchCondition);

	/**
	 * find list by search condition, no limit page
	 * @param searchCondition
	 * @return
	 */
	public List<ContactBooking> findAllBookingByConditionNoPaging(@Param("searchCondition") ContactBookingSearchDto searchCondition);

	/**
	 * find all booking that remain some day (remainDay) to appoitment time from exist date (dateOffset)
	 * @param dateOffset
	 * @param remainDay
	 * @return
	 */
	public List<ContactBookingDto> findBookingForNotification(@Param("dateOffset") Date dateOffset, @Param("remainDay") Integer remainDay);
}
