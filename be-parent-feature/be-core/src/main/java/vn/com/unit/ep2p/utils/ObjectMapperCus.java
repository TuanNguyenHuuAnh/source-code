package vn.com.unit.ep2p.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperCus extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObjectMapperCus() {

		this.setSerializationInclusion(JsonInclude.Include.NON_NULL)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
}
