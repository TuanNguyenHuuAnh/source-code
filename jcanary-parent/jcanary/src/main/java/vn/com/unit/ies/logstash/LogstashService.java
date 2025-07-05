package vn.com.unit.ies.logstash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogstashService {
	
    private static final Logger logger = LoggerFactory.getLogger(LogstashService.class);

    public void writeLogstash(LogstashDto log) {
//    	logger.info(log.toString());
    }
}
