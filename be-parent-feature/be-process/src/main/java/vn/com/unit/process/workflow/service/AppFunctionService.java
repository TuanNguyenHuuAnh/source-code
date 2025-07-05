package vn.com.unit.process.workflow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.com.unit.process.workflow.dto.AppFunctionDto;
import vn.com.unit.workflow.entity.JpmPermission;

public interface AppFunctionService {
	
	enum Permission {
		GROUP(2, "jpm.process.function.type.group"), USER(1, "jpm.process.function.type.user");

        private Integer value;

        private String name;

        private static final Map<Integer, Permission> mappings = new HashMap<>(8);

        static {
            for (Permission item : values()) {
                mappings.put(item.getValue(), item);
            }
        }

        public static Permission resolve(Integer value) {
            return (value != null ? mappings.get(value) : null);
        }

        private Permission(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public Integer getValue() {
            return value;
        }

    }
	
    AppFunctionDto[] getListJpmFunctionByProcessId(Long jpmProcessId);
    AppFunctionDto getById(Long id);
    JpmPermission saveJpmFunction(AppFunctionDto functionDto) throws Exception;
    void deleteJpmFunction(Long functionId);
    boolean validateFunctionWithNameAndProcessId(Long functionId, String functionName, Long processId);
    boolean validateJpmFunctionUsing(Long functionId);
    
    /**
     * saveJpmFunctionByProcessId
     * @param functionDtos
     * @param processId
     * @param convertFunctionId
     * @return List<JpmPermission>
     * @author KhuongTH
     */
    List<JpmPermission> saveJpmFunctionByProcessId(List<AppFunctionDto> functionDtos, Long processId, Map<Long, Long> convertFunctionId);
    
    /**
     * getListFunctionDefaultFreeform
     * @return List<AppFunctionDto>
     * @author KhuongTH
     */
    List<AppFunctionDto> getListFunctionDefaultFreeform();
}
