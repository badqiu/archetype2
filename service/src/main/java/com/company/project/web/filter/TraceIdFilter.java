
package com.company.project.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.company.project.util.LogTraceUtil;

/**
 * 性能filter。
 * 
 * <p>
 * web.xml配置文件格式如下：
 * <pre><![CDATA[
 <filter>
 <filter-name>PerformanceFilter</filter-name>
 <filter-class>com.duowan.common.web.filter.PerformanceFilter</filter-class>
 <init-param>
 <param-name>threshold</param-name>
 <param-value>3000</param-value>
 </init-param>
 </filter>
 ]]></pre>
 * </p>
 * 
 * <p>
 * 其中<code>threshold</code>参数表明超时阈值，如果处理的总时间超过该值，则filter会以warning的方式记录该次操作。
 * </p>
 *
 * @author badqiu
 *
 */
public class TraceIdFilter  extends OncePerRequestFilter implements Filter {
    private String traceIdHttpKey = "traceId";

	public String getTraceIdHttpKey() {
		return traceIdHttpKey;
	}

	public void setTraceIdHttpKey(String traceIdHttpKey) {
		this.traceIdHttpKey = traceIdHttpKey;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		try {
			String traceId = request.getHeader(traceIdHttpKey);
			LogTraceUtil.beginTrace(traceId);
			chain.doFilter(request, response);
		}finally {
			LogTraceUtil.endTrace();
		}
    }

    @Override
    public void initFilterBean() throws ServletException {
    }
    
    public void destroy() {
    }




}
