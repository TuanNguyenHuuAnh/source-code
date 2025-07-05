package vn.com.unit.cms.core.module.payment.repository;

import vn.com.unit.cms.core.module.payment.entity.PaymentConfirm;
import vn.com.unit.db.repository.DbRepository;
import org.springframework.data.repository.query.Param;

public interface PaymentConfirmRepository extends DbRepository<PaymentConfirm, Long> {
	PaymentConfirm getConfirmInfo(@Param("agentCode") String agentCode, @Param("period") String period);
	PaymentConfirm getGAConfirmInfo(@Param("agentCode") String agentCode, @Param("gaCode") String gaCode, @Param("period") String period);
	int checkGAConfirmed(@Param("agentCode") String agentCode, @Param("gaCode") String gaCode, @Param("period") String period);
}
