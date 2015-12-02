package com.zfpt.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * 项目名称：zfpt   
 * 类名称：TestSupport   
 * 类描述： Spring测试支持,用于测试由Spring 管理的bean,编写测试类时,继承该类  
 * 创建人：chens
 * 创建时间：2015年11月25日 上午10:34:13   
 * 修改备注：   
 * @version
 */
@ContextConfiguration(locations = {"classpath*:/spring/applicationContext.xml"})
public class TestSupport extends AbstractJUnit4SpringContextTests {
    protected long startTime;
    protected long endTime;

    /**
     * 记录 开始运行时间
     *
     * @return
     */
    protected long start() {
        this.startTime = System.currentTimeMillis();
        return startTime;
    }

    /**
     * 记录 结束运行时间
     *
     * @return
     */
    protected long end() {
        this.endTime = System.currentTimeMillis();
        this.log();
        return endTime;
    }

    /**
     * 输出记录
     */
    protected void log() {
        String text = "\n开始时间 : " + this.startTime + "\n结束时间 : " + this.endTime + "\n执行时间 : " + (this.endTime - this.startTime);
        logger.info(text);
    }
}