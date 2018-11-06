package org.eop.ssei.web.config.security.logout.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @author lixinjie
 * @since 2018-10-18
 */
public class JsonLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		writeJsonToResponse(response);
	}

	/**
	 * write logout success json to client
	 */
	protected void writeJsonToResponse(HttpServletResponse response) throws IOException {
		String json = "{\"code\":0,\"desc\":\"成功\"}";
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json);
	}
}
