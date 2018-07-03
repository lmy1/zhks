package com.cd.zuul.ywdp.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.util.Collection;
import java.util.Queue;

/**
 * Created by li.mingyang on 2018/4/29.
 */
public class ResponseFilter extends ZuulFilter{

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse response = context.getResponse();
        Long length = context.getOriginContentLength();

        if (!StringUtils.isEmpty(length)) {
            response.setContentLengthLong(length);
        }
        return null;
    }

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }




}
