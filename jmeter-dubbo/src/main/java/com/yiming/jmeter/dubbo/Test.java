package com.yiming.jmeter.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by yiming on 2018-05-10 11:01. Description:
 */
public class Test {

    private static final String zkAddress = "172.16.101.137:1500";

    private static final String applicationName = "app-name";

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

        String interfaceName = "com.dtdream.vanyar.user.service.UserReadService";
        String methodName = "getLoginUser";

        String fullMethodSignature = getFullMethodSignature(interfaceName);
        System.out.println(fullMethodSignature);

        referenceConfig.setInterface(interfaceName);
        referenceConfig.setGeneric(Boolean.TRUE);
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", 100L);
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

    public static String getFullMethodSignature(String interfaceName) {
        try {
            ZooKeeper zooKeeper = getZooKeeper();
            List<String> children = zooKeeper.getChildren("/dubbo/" + interfaceName, false);
            //List<String> res = children.stream().filter(s -> s.equalsIgnoreCase(interfaceName)).collect(Collectors.toList());
            children.forEach(o -> {
                try {
                    List<String> cChildren = zooKeeper.getChildren("/dubbo/" + interfaceName + "/" + o, false);
                    System.out.println(JSON.toJSONString(URLDecoder.decode(o)));
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            return JSON.toJSONString(children);
        } catch (IOException | KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    private static ZooKeeper getZooKeeper() throws IOException {
        return new ZooKeeper(zkAddress, 5000, watchedEvent -> {
            System.out.println("已经触发了" + watchedEvent.getType() + "事件！");
        });
    }

    public static List<String> getChild(String path) throws IOException, KeeperException, InterruptedException {
        byte[] data = getZooKeeper().getData(path, false, null);
        return null;
    }


}
