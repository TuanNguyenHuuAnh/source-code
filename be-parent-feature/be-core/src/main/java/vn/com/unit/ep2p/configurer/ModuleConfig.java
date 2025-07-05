package vn.com.unit.ep2p.configurer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ModuleConfig {

    default Map<String, List<String>> getIncludeModuleCode() {
        return null;
    }

    default Map<String, List<String>> getExcludeModuleCode() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> excludeModuleCode = new ArrayList<>();
        excludeModuleCode.add("ROLE_FOR_PROCESS");
        map.put("1", excludeModuleCode);
        return map;
    }

}
