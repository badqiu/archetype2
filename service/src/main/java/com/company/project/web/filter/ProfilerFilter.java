
package com.company.project.web.filter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.rapid.common.util.Profiler;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 耗时性能日志打印 Profiler filter。
 *
 * @author badqiu
 */
public class ProfilerFilter extends OncePerRequestFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(ProfilerFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
        	if(logger.isInfoEnabled()) {
        		Profiler.start(request.getRequestURI());
        	}
            
            chain.doFilter(request, response);
        } finally {
            if(logger.isInfoEnabled()) {
            	Profiler.release();
	            String dump = Profiler.dump();
	            if (StringUtils.isNotBlank(dump)) {
	                logger.info("profile dump:\n" + dump);
	            }
            }
            
            Profiler.reset();
        }
    }

    @Override
    public void initFilterBean() throws ServletException {
    }

    public void destroy() {
    }

}
