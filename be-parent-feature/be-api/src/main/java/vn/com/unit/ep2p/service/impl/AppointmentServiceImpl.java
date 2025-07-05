package vn.com.unit.ep2p.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.WriterException;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.appointment.dto.AppointmentDto;
import vn.com.unit.cms.core.module.appointment.entity.Appointment;
import vn.com.unit.cms.core.module.appointment.repository.AppointmentRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.dto.CustomerAppointmentDto;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.utils.Utility;
import vn.com.unit.ep2p.service.AppointmentService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;

@Service
@Transactional(rollbackFor = Exception.class)
public class AppointmentServiceImpl extends AbstractCommonService implements AppointmentService {
	
	private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Override
	public AppointmentDto addAppointment(AppointmentDto dto)
			throws WriterException, IOException, SQLException, DetailException {
		String createBy = UserProfileUtils.getFaceMask();

		// Check for duplicate appointment
		int duplicateCount = appointmentRepository.countDuplicateAppointment(dto.getAppointmentTime(), createBy,
				dto.getPurposeCode(), dto.getClientId());
		logger.info("Duplicate count: {}", duplicateCount);

		if (duplicateCount > 0) {
			throw new DetailException(
					"Lịch hẹn này đã bị trùng với lịch hẹn đã tạo trước đó, Bạn vui lòng kiểm tra lại.");
		}

		Appointment entity = new Appointment();
		entity.setClientId(dto.getClientId());
		entity.setClientName(dto.getClientName());
		entity.setPurposeCode(dto.getPurposeCode());
		entity.setCreateBy(createBy);
		entity.setCreateDate(new Date());
		entity.setLocation(dto.getLocation());
		entity.setNotes(dto.getNotes());
		entity.setAppointmentTime(dto.getAppointmentTime());
		appointmentRepository.save(entity);
		
		dto.setId(entity.getId());
		return dto;
	}

	@Override
	public CmsCommonPagination<CustomerAppointmentDto> getListAppointment(Date appointmentDate, Integer page, Integer pageSize, Integer appointmentId) {
		String user = UserProfileUtils.getUserPrincipal().getUsername();

		CmsCommonPagination<CustomerAppointmentDto> resultData = new CmsCommonPagination<>();
		List<CustomerAppointmentDto> lstData = new ArrayList<>();

		if (user != null) {
			int count = appointmentRepository.countByCondition(appointmentDate, user);

			Integer offset = page == null ? null : Utility.calculateOffsetSQL(page + 1, pageSize);
			if(count > 0) lstData = appointmentRepository.getListAppointmentByCondition(appointmentDate, offset, pageSize, user, appointmentId);

			resultData.setData(lstData);
			resultData.setTotalData(count);
		} else {
			resultData.setData(new ArrayList<>());
			resultData.setTotalData(0);
		}

		return resultData;
	}

}
