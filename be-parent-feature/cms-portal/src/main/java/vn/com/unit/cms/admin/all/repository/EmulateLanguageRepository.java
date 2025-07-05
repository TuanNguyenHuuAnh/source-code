package vn.com.unit.cms.admin.all.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.core.module.emulate.dto.EmulateLanguageDto;
import vn.com.unit.cms.core.module.emulate.entity.Emulate;
import vn.com.unit.cms.core.module.emulate.entity.EmulateLanguage;
import vn.com.unit.db.repository.DbRepository;

/**
 * @author TaiTM
 */
public interface EmulateLanguageRepository extends DbRepository<EmulateLanguage, Long> {
    /**
     * @author TaiTM
     */
    List<EmulateLanguageDto> findListLanguage(@Param("id") Long emulateId, @Param("lang") String lang);

    /**
     * @author TaiTM
     */
    @Modifying
    void deleteDataById(@Param("id") Long emulateId, @Param("deleteBy") String deleteBy);
    
    public EmulateLanguage findByMemo(@Param("memo") String memo);

}
