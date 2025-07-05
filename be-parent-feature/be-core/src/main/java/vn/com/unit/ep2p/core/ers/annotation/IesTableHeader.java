// 2021-04-12 LocLT Task #41067

package vn.com.unit.ep2p.core.ers.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface IesTableHeader {

	public String value(); // default ""
	
	public int width() default 100;
	
	public String align() default "center";
	
	public String format() default "text";

}