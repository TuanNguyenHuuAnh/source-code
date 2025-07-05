/*******************************************************************************
 * Class        LDAPUtil
 * Created date 2017/02/15
 * Lasted date  2017/02/15
 * Author       KhoaNA
 * Change log   2017/02/1501-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;

/**
 * LDAPUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class LDAPUtil {

	private static final Logger logger = LoggerFactory.getLogger(LDAPUtil.class);

	/**
	 * Sync accounts LDAP
	 *
	 * @param principal
	 *         type String
	 * @param credentials
	 *         type String
	 * @param sAMAccountName
	 *         type String
	 * @return List<Account>
	 * @author KhoaNA
	 */
	public List<JcaAccount> syncAccountLdap(String principal, String credentials, String sAMAccountName) {
		List<JcaAccount> listAccount = new ArrayList<JcaAccount>();
		
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        /* env.put(Context.PROVIDER_URL, Utility.getConfigureProperty("ldap.url")); */
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, principal);
        env.put(Context.SECURITY_CREDENTIALS, credentials);
		
		try {
			DirContext context = new InitialDirContext(env);

			SearchControls searchControls = new SearchControls();
			int UF_ACCOUNTDISABLE = 0x0002;
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String filter = "(objectClass=user)";
			if (StringUtils.isEmpty(sAMAccountName)) {
				filter = "(&(objectClass=user)(!(userAccountControl:1.2.840.113556.1.4.803:="
						+ Integer.toString(UF_ACCOUNTDISABLE) + ")))";
			} else {
				filter = "(&(objectClass=user)(!(userAccountControl:1.2.840.113556.1.4.803:="
						+ Integer.toString(UF_ACCOUNTDISABLE) + "))(sAMAccountName=" + sAMAccountName + "))";
			}
			logger.info("filter--SynAccountLdap--"+filter);
			NamingEnumeration<SearchResult> enumeration = context.search(Utility.getConfigureProperty("ldap.search"), filter,
					searchControls);
			while (enumeration.hasMore()) {
				try {
					SearchResult searchResult = enumeration.next();
					Attributes att = searchResult.getAttributes();
					
					JcaAccount account = new JcaAccount();
					account.setUsername(att.get("sAMAccountName").get().toString().trim());
					
					try {
						account.setFullname(att.get("displayName").get().toString().trim());
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					
					try {
						account.setEmail(att.get("mail").get().toString().trim());
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					
					try {
						account.setPhone(att.get("telephonenumber").get().toString().trim());
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					
					listAccount.add(account);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return listAccount;
	}
	
    /**
     * checkLogin
     * 
     * @param principal
     * @param credentials
     * @param systemConfig
     * @return
     * @author HungHT
     */
    public static boolean checkLogin(String principal, String credentials, SystemConfig systemConfig) {
        try {
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, systemConfig.getConfig(SystemConfig.LDAP_INITIAL_CONTEXT_FACTORY));
            env.put(Context.PROVIDER_URL, systemConfig.getConfig(SystemConfig.LDAP_PROVIDER_URL));
            env.put(Context.SECURITY_AUTHENTICATION, systemConfig.getConfig(SystemConfig.LDAP_SECURITY_AUTHENTICATION));
            env.put(Context.SECURITY_PRINCIPAL, principal + ConstantCore.AT_SIGN + systemConfig.getConfig(SystemConfig.LDAP_DOMAIN));
            env.put(Context.SECURITY_CREDENTIALS, credentials);
            DirContext context = new InitialDirContext(env);
            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            context.search(systemConfig.getConfig(SystemConfig.LDAP_MAIN_GROUP), systemConfig.getConfig(SystemConfig.LDAP_ACCOUNT_FILTER),
                    searchControls);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

	/**
	 * Sync account Ldap check lock
	 *
	 * @param principal
	 *         type String
	 * @param credentials
	 *         type String
	 * @param sAMAccountName
	 *         type String
	 * @return List<Account>
	 * @author KhoaNA
	 */
	public List<JcaAccount> syncAccountLdapCheckLock(String principal, String credentials, String sAMAccountName) {
		List<JcaAccount> listAccount = new ArrayList<JcaAccount>();
		
		Hashtable<String, String> env = new Hashtable<String, String>();
		try {
    		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            /* env.put(Context.PROVIDER_URL, Utility.getConfigureProperty("ldap.url")); */
    		env.put(Context.SECURITY_AUTHENTICATION, "simple");
    		env.put(Context.SECURITY_PRINCIPAL, principal);
    		env.put(Context.SECURITY_CREDENTIALS, credentials);
    		
			DirContext context = new InitialDirContext(env);

			SearchControls searchControls = new SearchControls();
			int UF_ACCOUNTDISABLE = 0x0002;
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String filter = "(objectClass=user)";
			if (StringUtils.isEmpty(sAMAccountName)) {
				filter = "(&(objectClass=user)((userAccountControl:1.2.840.113556.1.4.803:="
						+ Integer.toString(UF_ACCOUNTDISABLE) + ")))";
			} else {
				filter = "(&(objectClass=user)(!(userAccountControl:1.2.840.113556.1.4.803:="
						+ Integer.toString(UF_ACCOUNTDISABLE) + "))(sAMAccountName=" + sAMAccountName + "))";
			}
			NamingEnumeration<SearchResult> enumeration = context.search(Utility.getConfigureProperty("ldap.search"), filter, searchControls);
			while (enumeration.hasMore()) {
				try {
					SearchResult searchResult = enumeration.next();
					Attributes att = searchResult.getAttributes();
					JcaAccount account = new JcaAccount();

					account.setUsername(att.get("sAMAccountName").get().toString().trim());
					try {
						account.setFullname(att.get("displayName").get().toString().trim());
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					try {
						account.setEmail(att.get("mail").get().toString().trim());
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					try {
						account.setPhone(att.get("telephonenumber").get().toString().trim());
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					
					listAccount.add(account);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return listAccount;
	}

    public static void main(String[] args) {
        //LDAPUtil.checkLogin("90900036@company.local", "Pass@word1", null);
    }
}