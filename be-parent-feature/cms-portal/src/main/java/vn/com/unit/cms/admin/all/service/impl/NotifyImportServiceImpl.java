/**
 * @author TaiTM
 * @date Aug 18, 2020
 */

package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import jp.sf.amateras.mirage.SqlManager;
import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.cms.admin.all.db2.service.Db2Service;
import vn.com.unit.cms.admin.all.repository.NotifyImportRepository;
import vn.com.unit.cms.admin.all.service.NotifyImportService;
import vn.com.unit.cms.core.module.notify.dto.NotifyImportDto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.imp.excel.annotation.CoreReadOnlyTx;
import vn.com.unit.imp.excel.dto.ImportExcelSearchDto;

@SuppressWarnings("rawtypes")
@Service
@Qualifier(value = "notifyImportServiceImpl")
@CoreReadOnlyTx
public class NotifyImportServiceImpl implements NotifyImportService {

    @Autowired
    private NotifyImportRepository notifyImportRepository;

    /** MessageSource */
    @Autowired
    private MessageSource msg;

    /** SystemConfigure */
    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    @Qualifier("sqlManagerServicePr")
    private SqlManagerServiceImpl sqlManager;

    @Autowired
    private ConnectionProvider connectionProvider;
    
    @Autowired
    private Db2Service db2Service;

    @Override
    public int countData(String sessionKey) {
        return notifyImportRepository.countData(sessionKey);
    }

    @Override
    public List<NotifyImportDto> getListData(int page, String sessionKey, int sizeOfPage) {
        return notifyImportRepository.findListData(page, sizeOfPage, sessionKey);
    }

    @Override
    public int countError(String sessionKey) {
        return notifyImportRepository.countError(sessionKey);
    }

    @Override
    public List<NotifyImportDto> getListDataExport(String sessionKey) {
        return notifyImportRepository.findListDataExport(sessionKey);
    }

    @Override
    public List<NotifyImportDto> getAllDatas(String sessionKey) {
        return notifyImportRepository.findAllDatas(sessionKey);
    }

    @Override
    public Class getImportDto() {
        return NotifyImportDto.class;
    }

    @Override
    public Class getEntity() {
        return null;
    }

    @Override
    public ConnectionProvider getConnectionProvider() {
        return connectionProvider;
    }

    @Override
    public MessageSource getMessageSource() {
        return msg;
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public SystemConfig getSystemConfig() {
        return systemConfig;
    }



    @Override
    public List<String> initHeaderTemplate() {
        List<String> result = new ArrayList<String>();

        result.add("template.header.id");
        result.add("template.header.agent.code");
        result.add("template.header.title");
        result.add("template.header.content");
        result.add("template.header.message.error");
        return result;
    }

    @Override
    public SqlManager getSqlManager() {
        return sqlManager;
    }

    @Override
    public boolean validateBusiness(String sessionKey, ImportExcelSearchDto searchDto, Map<String, Object> mapParams, List<NotifyImportDto> listDataValidate) {
    	List<NotifyImportDto> lstDataImport = getAllDatas(sessionKey);
    	for(NotifyImportDto data : lstDataImport) {
    		Db2AgentDto agentDto = db2Service.findAgentInfoByCondition(data.getAgentCode());
    		if ("true".equals(searchDto.getIsMultiple()) ) {
	    		if (data.getContent() == null || data.getTitle() == null) {
	    			if(data.getAgentCode() == null) {
	    				if (data.getContent() == null && data.getTitle() != null) {
	    					notifyImportRepository.updateMessageErrorAgentTer("F009.E025", data.getId());
	    				}
	    				else if ( data.getContent() != null && data.getTitle() == null )
	    					notifyImportRepository.updateMessageErrorAgentTer("F009.E026", data.getId());
	            		else
	    				notifyImportRepository.updateMessageErrorAgentNotExist("F009.E027", data.getId());	
	    	    	} else
	    			if(agentDto == null) {
	    				if (data.getContent() == null && data.getTitle() != null) {
	    					notifyImportRepository.updateMessageErrorAgentTer("F009.E019", data.getId());
	    				}
	    				else if ( data.getContent() != null && data.getTitle() == null )
	    					notifyImportRepository.updateMessageErrorAgentTer("F009.E018", data.getId());
	    				else
	    				notifyImportRepository.updateMessageErrorAgentNotExist("F009.E020", data.getId());
	    			}
	    			else {
	    				if(agentDto.getAgentStatusCode() == 0) {
	    					if (data.getContent() == null && data.getTitle() != null) {
	    						notifyImportRepository.updateMessageErrorAgentTer("F009.E016", data.getId());
	        				}
	        				else if ( data.getContent() != null && data.getTitle() == null ) {
	        					notifyImportRepository.updateMessageErrorAgentTer("F009.E015", data.getId());
	        				}
	        			    else
	    					notifyImportRepository.updateMessageErrorAgentTer("F009.E017", data.getId());       
	    				
	    			}
	    				else {
	    					if (data.getTitle() == null && data.getContent() == null) {
	    						notifyImportRepository.updateMessageErrorAgentTer("F009.E023", data.getId()); 
	    					}
	    					else if (data.getTitle() == null) {
	    						notifyImportRepository.updateMessageErrorAgentTer("F009.E021", data.getId()); 
	    					}
	    					else if (data.getContent() == null) {
	    						notifyImportRepository.updateMessageErrorAgentTer("F009.E022", data.getId()); 
	    					}
	    				}
	    		} 
    	    }
    		else 
    		{
    	    if(data.getAgentCode() == null) {
    	    	if(data.getContent().length() > 1000 && data.getTitle().length() > 100 ) {
        			notifyImportRepository.updateMessageErrorAgentNotExist("F009.E028", data.getId());
        		} else 
        		if (data.getTitle().length() > 100) {
        			notifyImportRepository.updateMessageErrorAgentNotExist("F009.E029", data.getId());
        		}
        		else if (data.getContent().length() > 1000) {
        			notifyImportRepository.updateMessageErrorAgentNotExist("F009.E030", data.getId());
        		}
        		else {
        			notifyImportRepository.updateMessageErrorAgentNotExist("F009.E024", data.getId());
        		}
    	    }
    	    else if(agentDto == null) {
    			if(data.getContent().length() > 1000 && data.getTitle().length() > 100 ) {
        			notifyImportRepository.updateMessageErrorAgentNotExist("F009.E011", data.getId());
        		} else 
        		if (data.getTitle().length() > 100) {
        			notifyImportRepository.updateMessageErrorAgentNotExist("F009.E009", data.getId());
        		}
        		else if (data.getContent().length() > 1000) {
        			notifyImportRepository.updateMessageErrorAgentNotExist("F009.E010", data.getId());
        		}
        		else
    			notifyImportRepository.updateMessageErrorAgentNotExist("F009.E005", data.getId());
    		} else {
    			if(agentDto.getAgentStatusCode() == 0) {
    				if(data.getContent().length() > 1000 && data.getTitle().length() > 100 ) {
            			notifyImportRepository.updateMessageErrorAgentNotExist("F009.E008", data.getId());
            		} else 
            		if (data.getTitle().length() > 100) {
            			notifyImportRepository.updateMessageErrorAgentNotExist("F009.E006", data.getId());
            		}
            		else if (data.getContent().length() > 1000) {
            			notifyImportRepository.updateMessageErrorAgentNotExist("F009.E007", data.getId());
            		}
            		else
        			notifyImportRepository.updateMessageErrorAgentTer("F009.E004", data.getId());
        		}
    			else {
    				if(data.getContent().length() > 1000 && data.getTitle().length() > 100 ) {
            			notifyImportRepository.updateMessageErrorAgentNotExist("F009.E014", data.getId());
            		} else 
            		if (data.getTitle().length() > 100) {
            			notifyImportRepository.updateMessageErrorAgentNotExist("F009.E012", data.getId());
            		}
            		else if (data.getContent().length() > 1000) {
            			notifyImportRepository.updateMessageErrorAgentNotExist("F009.E013", data.getId());
            		}
            		else {
            			
            		}
    				
    			}
    		}}
    	}  else {
    		if (data.getAgentCode() == null) {
    			notifyImportRepository.updateMessageErrorAgentNotExist("F009.E024", data.getId());
    		}
    		else
    		if(agentDto == null) {
				notifyImportRepository.updateMessageErrorAgentNotExist("F009.E005", data.getId());
			}
			else {
				if(agentDto.getAgentStatusCode() == 0) {
					notifyImportRepository.updateMessageErrorAgentTer("F009.E004", data.getId());       
				
			}
		} 
    		
    	}
    		
    	}
    	return countError(sessionKey) > 0;
    }

    @Override
    public void saveListImport(List<NotifyImportDto> listDataSave, String sessionKey, Locale locale, String username) throws Exception {

    }
}