package com.yiming.jmeter.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.yiming.jmeter.dubbo.dubbo.DubboServiceFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yiming on 2018-05-10 11:01.
 * Description:
 *
 *
 */
public class Test {

    private static final String zkAddress = "172.16.101.137:1500";

    private static final String applicationName = "zcy-fixed-universal";

    private static final String PROTOCOL = "zookeeper";
    private static final String VERSION = "1.0.0";



    public static void main(String[] args) {

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(applicationName);

        // 连接注册中心配置
        registryConfig();

        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig());
        referenceConfig.setVersion(VERSION);


        String interfaceName = "cn.gov.zcy.user.service.ZcyUserReadService";
        String methodName = "findUserById";

        referenceConfig.setInterface(interfaceName);
        referenceConfig.setGeneric(Boolean.TRUE);
        Object res = referenceConfig.get().$invoke(methodName,
            new String[]{"java.lang.Long"},
            new Long[]{100L}
            );
        System.out.println(JSON.toJSONString(res));
    }

    private static RegistryConfig registryConfig() {
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(zkAddress);
        registry.setProtocol(PROTOCOL);
        return registry;
    }


}
