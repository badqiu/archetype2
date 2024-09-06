
package com.company.project.web.filter;

import com.github.rapid.common.util.Profiler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Profiler filter。
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
