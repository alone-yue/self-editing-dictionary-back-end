package com.idictionary.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.idictionary.model.Token;
import com.idictionary.service.TokenService;
import com.idictionary.util.UserVerificationUtil;


@WebFilter(filterName="AuthorizationFilter",urlPatterns= {"*"})
public class AuthorizationFilter implements Filter{

	@Autowired
	TokenService ts;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse rep = (HttpServletResponse)response;
		
		String token = req.getHeader("Authorization");
		
		if(token == null || "".equals(token)) {
			
			req.setAttribute(UserVerificationUtil.LOGIN_STATE, UserVerificationUtil.USER_NOT_LOGGED_IN);
			chain.doFilter(request, response);
			return;
		}
		
		Token t = ts.getByToken(token);
		if(t == null) {
			req.setAttribute(UserVerificationUtil.LOGIN_STATE, UserVerificationUtil.WRONG_TOKEN);
			chain.doFilter(request, response);
			return;
		}
		
		req.setAttribute(UserVerificationUtil.LOGIN_STATE, UserVerificationUtil.USER_LOGGED_IN);
		req.setAttribute(UserVerificationUtil.USER_ID, t.getuId());
		chain.doFilter(request, response);
	}

}
