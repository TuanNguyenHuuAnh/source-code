package vn.com.unit.db.service;

import jp.sf.amateras.mirage.SqlManager;

public interface SqlManagerService {

    Long getNextValBySeqName(String sequenceName);
}
