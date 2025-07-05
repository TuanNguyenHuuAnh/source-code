package vn.com.unit.ep2p.ers.dto;


import org.springframework.security.core.AuthenticationException;


public class AuthenticationExceptionMockup extends  AuthenticationException {

	private static final long serialVersionUID = 1L;

	public AuthenticationExceptionMockup(String msg ) {
		super(msg);
		
	}
}
