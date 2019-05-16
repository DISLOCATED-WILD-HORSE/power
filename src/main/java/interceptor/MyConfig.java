package interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
@EnableWebMvc
public class MyConfig implements WebMvcConfigurer {
    @Resource
    private GlobalInterceptor globalInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //WebMvcConfigurer.addInterceptors(registry);
        // 使拦截器生效1.此处参数是我们自定义的拦截器名( LoginInterceptor ) 2.添加拦截规则(/**)拦截全部
        //registry.addInterceptor(globalInterceptor).addPathPatterns("/**");
        //this.excludeUserLogin(registry.addInterceptor(globalInterceptor));
        //super.addInterceptors(registry);
    }
}
