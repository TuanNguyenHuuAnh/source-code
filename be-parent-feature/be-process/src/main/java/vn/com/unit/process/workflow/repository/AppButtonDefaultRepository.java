package vn.com.unit.process.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.process.workflow.dto.AppButtonDto;
import vn.com.unit.workflow.dto.JpmButtonForDocDto;
import vn.com.unit.workflow.entity.JpmButtonDefault;

public interface AppButtonDefaultRepository extends DbRepository<JpmButtonDefault, Long> {
	
	/**
	 * Get all button default
	 * 
	 * @return List<AppButtonDto>
	 * @author KhoaNA
	 */
    List<AppButtonDto> getListButtonDefault();
    
    /**
     * Get list button by type and kinds
     * 
     * @param type
     * 			type String
     * @param kinds
     * 			type List<String>
     * @return List<AppButtonDto>
     * @author KhoaNA
     */
    List<AppButtonDto> getListButtonDefaultByTypeAndKinds(@Param("type") String type, @Param("kinds") List<String> kinds);
    
    /**
     * Get list JpmButtonForDocDto by kinds and lang
     * 
     * @param type
     * 			type String
     * @param kinds
     * 			type List<String>
     * @param lang
     * 			type String
     * @return List<JpmButtonForDocDto>
     * @author KhoaNA
     */
    List<JpmButtonForDocDto> getListJpmButtonForDocDtoByTypeAndKindsAndLang(@Param("type") String type, @Param("kinds") List<String> kinds, @Param("lang") String lang);
}
