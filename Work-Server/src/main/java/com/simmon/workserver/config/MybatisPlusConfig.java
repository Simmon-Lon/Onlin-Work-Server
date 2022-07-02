package com.simmon.workserver.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Simmon
 */

@Configuration
public class MybatisPlusConfig {

    /**
     * Mybatis plus 版本低于3.2以下使用
     */


    /**
     *  @Bean
     *  public PaginationInnerInterceptor paginationInnerInterceptor(){
     *    return new PaginationInnerInterceptor();
     *  }
     */

    /**
     * Mybatis plus 版本低于3.2以上使用
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor=new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
