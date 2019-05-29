package com.nskfdc.scgj.common;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * The Class WebConfig.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

      

	/** The interceptor. */
	@Autowired
	private PrivilegeInterceptor interceptor;
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
	 */
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor);
        
    }

 
    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry)
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

//        registry.addViewController("/").setViewName("index");
//        registry.addViewController("/endpoints").setViewName("endpoints");
//        registry.addViewController("/home").setViewName("index");
//        registry.addViewController("/logoffUser").setViewName("logoffUser");
//        registry.addViewController("/login").setViewName("login");
//                
    }
    
    
}

