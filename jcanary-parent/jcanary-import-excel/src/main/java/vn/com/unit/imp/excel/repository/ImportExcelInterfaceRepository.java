package vn.com.unit.imp.excel.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;

public interface ImportExcelInterfaceRepository<T> extends DbRepository<T, Long> {
    int countData(@Param("sessionKey") String sessionKey);
    
    int countError(@Param("sessionKey") String sessionKey);

    List<T> findListData(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
            @Param("sessionKey") String sessionKey);

    List<T> findListDataExport(@Param("sessionKey") String sessionKey);
    
    List<T> findAllDatas(@Param("sessionKey") String sessionKey);
    
    @Modifying
    void validateData(@Param("sessionKey") String sessionKey);
    
    //phan quyen voi channel
    
    int countDataByChannel(@Param("sessionKey") String sessionKey, @Param("channel") String channel);
    
    List<T> findListDataByChannel(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
            @Param("sessionKey") String sessionKey, @Param("channel") String channel);
    
    int countErrorByChannel(@Param("sessionKey") String sessionKey, @Param("channel") String channel);
    
    List<T> findListDataExportByChannel(@Param("sessionKey") String sessionKey, @Param("channel") String channel);
    
    List<T> findAllDatasByChannel(@Param("sessionKey") String sessionKey, @Param("channel") String channel);
    
}
