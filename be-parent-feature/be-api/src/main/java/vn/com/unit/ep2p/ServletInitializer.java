/*******************************************************************************
 * Class        ：ServletInitializer
 * Created date ：2020/11/08
 * Lasted date  ：2020/11/08
 * Author       ：KhoaNA
 * Change log   ：2020/11/08：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * ServletInitializer
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EnterpriseApplication.class);
	}

}
