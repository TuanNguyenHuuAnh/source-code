package vn.com.unit.process.workflow.repository;

import org.springframework.data.repository.query.Param;

import vn.com.unit.process.workflow.dto.AppParamDeployDto;
import vn.com.unit.workflow.repository.JpmParamRepository;

public interface AppParamDeployRepository extends JpmParamRepository {

    /**
     * findListParamDeployDtoByProcessId
     * 
     * @param Long
     *            processId
     * @return array <AppParamDeployDto>
     * @author KhuongTH
     */
    public AppParamDeployDto[] findListParamDeployDtoByProcessId(@Param("processId") Long processId);

    /**
     * getById
     * 
     * @param Long
     *            idd
     * @return array <AppParamDeployDto>
     * @author KhuongTH
     */
    public AppParamDeployDto getById(@Param("id") Long id);
}
