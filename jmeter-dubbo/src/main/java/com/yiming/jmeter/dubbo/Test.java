package com.yiming.jmeter.dubbo;

import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;

/**
 * Created by yiming on 2018-05-10 11:01.
 * Description:
 *
 *
 */
public class Test {

    private String zkAddress;

    private String applicationName = "";


    public static void main(String[] args) {
        // 连接注册中心配置
        registryConfig();

        // 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(12345);
        protocol.setThreads(200);

//        DubboServiceFactory.getInstance().genericInvoke();

    }

    private static void registryConfig() {
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("10.20.130.230:9090");
        registry.setUsername("aaa");
        registry.setPassword("bbb");
    }


}
