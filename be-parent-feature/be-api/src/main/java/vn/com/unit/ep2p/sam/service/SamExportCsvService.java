package vn.com.unit.ep2p.sam.service;

import java.util.Locale;

import org.springframework.http.ResponseEntity;

import vn.com.unit.core.config.SystemConfig;

public interface SamExportCsvService {
	
	public SystemConfig getSystemConfig();
	
    /**
     * Get activity detail by activity code
     * @param number
     * @return
     */
	@SuppressWarnings("rawtypes")
	ResponseEntity exportCsv(String fromDate, String toDate, Boolean isDetail, Locale locale);
}
