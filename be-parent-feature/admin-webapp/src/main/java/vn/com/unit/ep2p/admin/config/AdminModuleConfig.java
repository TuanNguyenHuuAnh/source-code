package vn.com.unit.ep2p.admin.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import vn.com.unit.ep2p.configurer.ModuleConfig;

@Component
@Order(1)
public class AdminModuleConfig implements ModuleConfig {

    @Override
    public Map<String, List<String>> getIncludeModuleCode() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> moduleCode = new ArrayList<String>();
        moduleCode.add("ADMIN_WEBAPP");
        map.put("1", moduleCode);

        return map;
    }
}