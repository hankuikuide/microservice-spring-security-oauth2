package com.crhms.security.client2.security;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * @author ：hkk
 * @date ：Created in 2019/10/11 19:48
 */
public class ZuulAuthHeaderFilter extends ZuulFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private ServiceOAuth2RestTemplate restTemplate;

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    public ZuulAuthHeaderFilter(ServiceOAuth2RestTemplate template) {

        this.restTemplate = template;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();

        OAuth2AccessToken token = this.restTemplate.getAccessToken();
        requestContext.addZuulRequestHeader(AUTHORIZATION_HEADER,
                String.format("%s %s",
                        token.getTokenType(), token.getValue()));

        return null;
    }
}
