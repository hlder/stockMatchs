package com.hld.stockweb.util;
import com.alibaba.fastjson.JSON;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zsl on 2017/9/3.
 */
@Configuration
public class ConfigurationFilter {
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter());//添加过滤器
        registration.addUrlPatterns("/web/*");//设置过滤路径，/*所有路径
//        registration.addInitParameter("name", "alue");//添加默认参数
//        registration.setName("MyFilter");//设置优先级
        registration.setOrder(1);//设置优先级
        return registration;
    }

    public class MyFilter implements Filter {
        @Override
        public void destroy() {
        }

        @Override
        public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
                throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest) srequest;
            //打印请求Url
            System.out.println("this is MyFilter,url :" + request.getRequestURI());
            if("/web/login".equals(""+request.getRequestURI())){//login的时候不拦截
                filterChain.doFilter(srequest, sresponse);
            }else{
                OutputStreamWriter osw = new OutputStreamWriter(sresponse.getOutputStream(),"GBK");
                PrintWriter writer  = new PrintWriter(osw, true);
                Map<String,Object> map=new HashMap<>();
                map.put("code","1001");
                map.put("msg","请求超时,请重新登录!");
                if(writer!=null){
                    writer.write(JSON.toJSONString(map));
                }
                if(osw!=null){
                    osw.close();
                }
                if(writer!=null){
                    writer.close();
                }
            }
        }

        @Override
        public void init(FilterConfig arg0) throws ServletException {
        }
    }
}