package org.eop.ssei.web.config.security.login.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

/**
 * @author lixinjie
 * @since 2018-10-18
 */
public class JsonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private RequestCache requestCache = new HttpSessionRequestCache();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if (savedRequest != null) {
			requestCache.removeRequest(request, response);
		}
		
		clearAuthenticationAttributes(request);
		
		saveEntireUserDetailsToSession(request, authentication);
		
		writeJsonToResponse(response);
	}
	
	private void saveEntireUserDetailsToSession(HttpServletRequest request, Authentication authentication) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute("EntireUserDetails", authentication.getPrincipal());
		}
	}
	
	/**
	 * write login success json to client
	 */
	protected void writeJsonToResponse(HttpServletResponse response) throws IOException {
		String json = "{\"code\":0,\"desc\":\"成功\"}";
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json);
	}

	/**
	 * Removes temporary authentication-related data which may have been stored in the
	 * session during the authentication process.
	 */
	protected final void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null) {
			return;
		}

		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
