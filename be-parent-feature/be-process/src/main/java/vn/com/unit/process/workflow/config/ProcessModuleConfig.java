package vn.com.unit.process.workflow.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import vn.com.unit.ep2p.configurer.ModuleConfig;

@Component
@Order(2)
public class ProcessModuleConfig implements ModuleConfig {

	@Override
	public Map<String, List<String>> getIncludeModuleCode() {

		Map<String, List<String>> map = new HashMap<>();

		List<String> moduleCode = new ArrayList<String>();

		moduleCode.add("PROCESS");
		map.put("1", moduleCode);

		return map;
	}

}