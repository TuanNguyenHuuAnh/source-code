package vn.com.unit.ep2p.export;

import java.io.OutputStream;

public interface IExport {
	void setExportInput(IDataTable iDataTable) throws Exception;
	
	void export(OutputStream outputStream) throws Exception;
	
	void destroy() throws Exception;
}
