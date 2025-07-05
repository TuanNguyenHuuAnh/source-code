package vn.com.unit.db.service.impl;

import jp.sf.amateras.mirage.SqlManagerImpl;
import org.springframework.stereotype.Service;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;

@Service
public class SqlManagerDb2ServiceImpl extends SqlManagerImpl implements SqlManagerDb2Service {

    @Override
    public Long getNextValBySeqName(String seqName) {
        String querySql = dialect.getSequenceSql(seqName);
        Long sequenceValue = super.getSingleResultBySql(Long.class, querySql);
        return sequenceValue;
    }
}
