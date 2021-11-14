package com.company.customerinfo.config.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SleuthFilter implements Filter {

    public static final String XB3_TRACE_ID = "X-B3-TraceId";
    public static final String XB3_SPAN_ID = "X-B3-SpanId";

    @Autowired
    private Tracer tracer;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Span span = tracer.currentSpan();
        if (span != null) {
            httpServletResponse.setHeader(XB3_TRACE_ID, span.context().traceId() );
            httpServletResponse.setHeader(XB3_SPAN_ID, span.context().spanId() );
        }
        chain.doFilter(request, response);
    }

}