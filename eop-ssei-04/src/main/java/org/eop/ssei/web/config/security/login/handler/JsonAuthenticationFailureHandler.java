package org.eop.ssei.web.config.security.login.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * @author lixinjie
 * @since 2018-10-18
 */
public class JsonAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private boolean allowSessionCreation = true;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		saveException(request, exception);
		
		writeJsonToResponse(response);
	}
	
	/**
	 * write login failure json to client
	 */
	protected void writeJsonToResponse(HttpServletResponse response) throws IOException {
		String json = "{\"code\":-1,\"desc\":\"失败\"}";
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json);
	}

	/**
	 * Caches the {@code AuthenticationException} for use in view rendering.
	 * <p>
	 * Will attempt to store the exception in the session. If there is no
	 * session and {@code allowSessionCreation} is {@code true} a session will be created.
	 * Otherwise the exception will not be stored.
	 */
	protected final void saveException(HttpServletRequest request,
			AuthenticationException exception) {
		HttpSession session = request.getSession(false);
		
		if (session != null || allowSessionCreation) {
			request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
					exception);
		}
	}
}
