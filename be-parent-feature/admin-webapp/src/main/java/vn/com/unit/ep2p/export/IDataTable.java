/*******************************************************************************
 * Class        ：IDataTable
 * Created date ：2017/07/19
 * Lasted date  ：2017/07/19
 * Author       ：KhoaNA
 * Change log   ：2017/07/19：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.export;

import java.util.List;

import vn.com.unit.ep2p.export.util.ColumnFormat;
import vn.com.unit.ep2p.export.util.DataTable;
import vn.com.unit.ep2p.export.util.HeaderTable;

/**
 * IDataTable
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface IDataTable {

	/**
     * Get headerTable
     * @return HeaderTable
     * @author  KhoaNA
     */
	HeaderTable getHeaderTable();
	
	/**
     * Get column format list
     * @return List<ColumnFormat>
     * @author  KhoaNA
     */
	List<ColumnFormat> getColumnFormats();
	
	/**
     * Get dataTable
     * @return DataTable
     * @author  KhoaNA
     */
	DataTable getDataTable();
	
	/**
     * Prepare data: dataTable, headerTable, columnFormats
     * @return DataTable
     * @author  KhoaNA
     */
	void initDataTable() throws Exception;
}
