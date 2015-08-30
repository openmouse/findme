/*
 * Project Name: xinyunlian-ecom
 * File Name: TokenInterceptor.java
 * Class Name: TokenInterceptor
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kelansi.findme.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kelansi.findme.common.Setting;

/**
 * Interceptor - 令牌
 * 
 * @author Hengtiansoft Team
 * @version 1.0_beta
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

	private Logger LOGGER = LoggerFactory.getLogger(TokenInterceptor.class);
	
	/** "令牌"属性名称 */
	private static final String	TOKEN_ATTRIBUTE_NAME		= "token";

	/** "令牌"Cookie名称 */
	private static final String	TOKEN_COOKIE_NAME			= "token";

	/** "令牌"参数名称 */
	private static final String	TOKEN_PARAMETER_NAME		= "token";

	/** 错误消息 */
	// private static final String ERROR_MESSAGE = "Bad or missing token!";

	private static final String	DEFAULT_UNAUTHORIZED_URL	= "/common/unauthorized.jhtml";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = getCookie(request, TOKEN_COOKIE_NAME);
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			String requestType = request.getHeader("X-Requested-With");
			if (requestType != null && "XMLHttpRequest".equalsIgnoreCase(requestType)) {
				if (token != null && token.equals(request.getHeader(TOKEN_PARAMETER_NAME))) {
					return true;
				} else {
					response.addHeader("tokenStatus", "accessDenied");
				}
			} else {
				if (token != null && token.equals(request.getParameter(TOKEN_PARAMETER_NAME))) {
					return true;
				}
			}
			if (token == null) {
				token = UUID.randomUUID().toString();
				addCookie(request, response, TOKEN_COOKIE_NAME, token);
			}
			response.sendRedirect(request.getContextPath() + DEFAULT_UNAUTHORIZED_URL);
			return false;
		} else {
			if (token == null) {
				token = UUID.randomUUID().toString();
				addCookie(request, response, TOKEN_COOKIE_NAME, token);
			}
			request.setAttribute(TOKEN_ATTRIBUTE_NAME, token);
			return true;
		}
	}

	private void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer maxAge, String path, String domain, Boolean secure) {
		Assert.notNull(request);
		Assert.notNull(response);
		Assert.hasText(name);
		try {
			name = URLEncoder.encode(name, "UTF-8");
			value = URLEncoder.encode(value, "UTF-8");
			Cookie cookie = new Cookie(name, value);
			if (maxAge != null) {
				cookie.setMaxAge(maxAge);
			}
			if (StringUtils.isNotEmpty(path)) {
				cookie.setPath(path);
			}
			if (StringUtils.isNotEmpty(domain)) {
				cookie.setDomain(domain);
			}
			if (secure != null) {
				cookie.setSecure(secure);
			}
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	public String getCookie(HttpServletRequest request, String name) {
		Assert.notNull(request);
		Assert.hasText(name);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			try {
				name = URLEncoder.encode(name, "UTF-8");
				for (Cookie cookie : cookies) {
					if (name.equals(cookie.getName())) {
						return URLDecoder.decode(cookie.getValue(), "UTF-8");
					}
				}
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return null;
	}
	
	public void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
		addCookie(request, response, name, value, null, Setting.cookiePath, Setting.cookieDomain, null);
	}
}
