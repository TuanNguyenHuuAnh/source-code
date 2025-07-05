package vn.com.unit.process.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.process.workflow.dto.AppStatusDeployDto;
import vn.com.unit.workflow.entity.JpmStatusDeploy;
import vn.com.unit.workflow.repository.JpmStatusDeployRepository;

public interface AppStatusDeployRepository extends JpmStatusDeployRepository {
	/**
     * findListStatusDeployDtoByProcessId
     * @Status Long processId
     * @return array <AppStatusDeployDto>
     * @author KhuongTH
     */
	public AppStatusDeployDto[] findListStatusDeployDtoByProcessId(@Param("processId") Long processId);
	/**
     * getById
     * @Status Long id
     * @return <AppStatusDeployDto>
     * @author KhuongTH
     */
	public AppStatusDeployDto getById(@Param("id") Long id);
		
	/**
     * getStatusByProcessI
     * @Status Long processId
     * @return JpmStatusDeploy
     * @author KhuongTH
     */
	public JpmStatusDeploy getStatusByProcessId(@Param("processId")Long processId);
	
    /**
     * findJpmStatusDeployListByProcessDeployId
     * @param processDeployId
     * @param lang
     * @return List<JpmStatusDeploy>
     * @author KhoaNA
     */
    List<JpmStatusDeploy> findJpmStatusDeployListByProcessDeployId(@Param("processDeployId") Long processDeployId, @Param("lang") String lang);
}
