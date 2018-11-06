package org.eop.ssei.web.config.security.access.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author lixinjie
 * @since 2018-10-18
 */
public class JsonAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		writeJsonToResponse(response);
	}

	/**
	 * write access denied json to client
	 */
	protected void writeJsonToResponse(HttpServletResponse response) throws IOException {
		String json = "{\"code\":-2,\"desc\":\"拒绝\"}";
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json);
	}
}
