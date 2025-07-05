package vn.com.unit.cms.admin.all.dialect;

import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.dialect.StandardDialect;

/**
 * SQLServerDialect
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class SQLServerDialect extends StandardDialect {

	@Override
	public String getName() {
		return "sqlserver";
	}
	
	@Override
	public String getCountSql(String sql) {
		return "SELECT COUNT(*) FROM (" + sql + ") A";
	}

	@Override
	public boolean supportsGenerationType(GenerationType generationType) {
		if(generationType == GenerationType.APPLICATION){
			return false;
		}
		return true;
	}
	
    @Override
    public String getSequenceSql(String sequenceName) {
        return String.format(
                "SELECT (NEXT VALUE FOR %s) AS NEXTVAL", sequenceName);
    }
}