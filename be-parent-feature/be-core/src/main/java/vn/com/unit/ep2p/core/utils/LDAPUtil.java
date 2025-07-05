/**
 * @author vunt
 */
package vn.com.unit.ep2p.core.utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.ep2p.admin.constant.Message;

/**
 * LDAPUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class LDAPUtil {

	private static final Logger logger = LoggerFactory.getLogger(LDAPUtil.class);

	private static final String USERNAME_FILTER = "%USERNAME%";
	
    /**
     * checkLogin
     * 
     * @param principal
     * @param credentials
     * @param systemConfig
     * @return
     * @author HungHT
     */
    public static boolean checkLogin(String principal, String credentials, SystemConfig systemConfig, Long companyId) {
		boolean isSuccess = true;
        try {
            Hashtable<String, String> env = new Hashtable<String, String>();
            String ldapInitialContextFactory = systemConfig.getConfig(SystemConfig.LDAP_INITIAL_CONTEXT_FACTORY, companyId);
    		String ldapProviderUrl = systemConfig.getConfig(SystemConfig.LDAP_PROVIDER_URL, companyId);
    		String ldapSecurityAuthentication = systemConfig.getConfig(SystemConfig.LDAP_SECURITY_AUTHENTICATION, companyId);
    		String ldapMainGroup = systemConfig.getConfig(SystemConfig.LDAP_MAIN_GROUP, companyId);
    		String ldapAccountFilter = systemConfig.getConfig(SystemConfig.LDAP_ACCOUNT_FILTER, companyId);
    		String ldapDomain = systemConfig.getConfig(SystemConfig.LDAP_DOMAIN, companyId);

    		System.out.println("LDAP_INITIAL_CONTEXT_FACTORY "+ldapInitialContextFactory);
    		System.out.println("LDAP_PROVIDER_URL "+ldapProviderUrl);
    		System.out.println("LDAP_SECURITY_AUTHENTICATION "+ldapSecurityAuthentication);
    		System.out.println("LDAP_MAIN_GROUP "+ldapMainGroup);
    		System.out.println("LDAP_ACCOUNT_FILTER "+ldapAccountFilter);
    		
            env.put(Context.INITIAL_CONTEXT_FACTORY, ldapInitialContextFactory); //com.sun.jndi.ldap.LdapCtxFactory
            env.put(Context.PROVIDER_URL, ldapProviderUrl); //ldap://10.87.103.21:389
            env.put(Context.SECURITY_AUTHENTICATION, ldapSecurityAuthentication); //simple
            env.put(Context.SECURITY_PRINCIPAL, ldapDomain + "\\" + principal);
            env.put(Context.SECURITY_CREDENTIALS, credentials);
			env.put(Context.REFERRAL, "follow");

			System.out.println("################## BEGIN ################");
			final String LDAP_SEARCH = ldapMainGroup;// "DC=VN,DC=Intranet";
			System.out.println("################## LDAP_SEARCH " + LDAP_SEARCH + " ################");
			try {
				DirContext context = new InitialDirContext(env);
				SearchControls searchControls = new SearchControls();
				searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

				System.out.println("################## BEFORE CONTEXT SEARCH ################");
				NamingEnumeration<SearchResult> results = context.search(ldapMainGroup,
						"(&(objectclass=user)(!(userAccountControl:1.2.840.113556.1.4.803:=2))(sAMAccountName=" + principal
								+ "))",
						searchControls);
				if (results == null || !results.hasMore()) {
					isSuccess = false;
				}
				System.out.println("################## AFTER CONTEXT SEARCH ################");

			} catch (NamingException e) {
				logger.error(Message.ERROR, e);
				isSuccess = false;// not in ldap
			}
			System.out.println("################## LOGIN LDAP : " + isSuccess + " ################");
            return isSuccess;
        } catch (Exception e) {
        	logger.error("checkLogin", e);
            return isSuccess;
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