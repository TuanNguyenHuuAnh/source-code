package vn.com.unit.ep2p.rest.ers;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.rest.AbstractRest;

@Api(tags = { "Api LocLT test" })
@RestController
@RequestMapping("/")
public class DevTest extends AbstractRest {
	
//    private static final Logger logger = LoggerFactory.getLogger(DevTest.class);

	@ApiOperation("Test handle error for logstash")
    @GetMapping("throw-error")
	public DtsApiResponse throwError() {
		long start = System.currentTimeMillis();
		return this.errorHandler.handlerException(null, start);
	}
}
