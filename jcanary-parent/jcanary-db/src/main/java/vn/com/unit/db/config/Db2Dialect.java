/*******************************************************************************
 * Class        SQLServerDialect
 * Created date 2016/06/01
 * Lasted date  2015/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.db.config;

import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.dialect.StandardDialect;

/**
 * SQLServerDialect
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class Db2Dialect extends StandardDialect {

	@Override
	public String getName() {
		return "Db2";
	}
	
	@Override
	public String getCountSql(String sql) {
		return "SELECT COUNT(*) FROM (" + sql + ") A";
	}

	@Override
	public boolean supportsGenerationType(GenerationType generationType) {
		if(generationType == GenerationType.IDENTITY){
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