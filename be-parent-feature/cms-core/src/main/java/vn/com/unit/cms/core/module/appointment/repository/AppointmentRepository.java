package vn.com.unit.cms.core.module.appointment.repository;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.appointment.entity.Appointment;
import vn.com.unit.ep2p.admin.dto.CustomerAppointmentDto;
import vn.com.unit.db.repository.DbRepository;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends DbRepository<Appointment, Long> {
        int countByCondition(@Param("appointmentDate") Date appointmentDate, @Param("agentCode") String agentCode);

        List<CustomerAppointmentDto> getListAppointmentByCondition(@Param("appointmentDate") Date appointmentDate,
                        @Param("page") Integer page, @Param("pageSize") Integer pageSize,
                        @Param("agentCode") String agentCode, @Param("appointmentId") Integer appointmentId);

        int countDuplicateAppointment(@Param("appointmentDate") Date appointmentDate,
                        @Param("agentCode") String agentCode,
                        @Param("purposeCode") String purposeCode,
                                      @Param("clientId") String clientId);
}
