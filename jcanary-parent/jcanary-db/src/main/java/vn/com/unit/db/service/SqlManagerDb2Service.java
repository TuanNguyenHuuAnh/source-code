package vn.com.unit.db.service;

import jp.sf.amateras.mirage.SqlManager;

public interface SqlManagerDb2Service extends SqlManager {

    Long getNextValBySeqName(String sequenceName);
}
