package com.yiming.jmeter.dubbo.sampler;

import com.yiming.jmeter.dubbo.dubbo.DubboServiceFactory;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.util.List;
import java.util.Map;

/**
 * Created by yiming on 2018-05-10.
 * Description:
 */
@Data
@Slf4j
public class DubboSampler2_0 extends AbstractJavaSamplerClient {

    public static final String DEFAULT_LABEL_NAME = "Dubbo Sampler2_0";
    private static final String SERVICE_PROVIDER_NAME = "serviceProviderName";
    private static final String METHOD_NAME = "methodName";
    private static final String PARAMETERS = "parameters";
    private static final String VERSION = "version";
    private String interfaceName;
    private String serviceProviderName;
    private String version;
    private String simpleName;

    private String message;

    @Override
    public void setupTest(JavaSamplerContext context) {
        super.setupTest(context);
    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments defaultParameters = new Arguments();
        defaultParameters.addArgument(SERVICE_PROVIDER_NAME, "服务提供者Service");
        defaultParameters.addArgument(METHOD_NAME, "方法名");
        defaultParameters.addArgument(PARAMETERS, "参数(json)");
        defaultParameters.addArgument(VERSION, "1.0.0");
        return defaultParameters;
    }

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        SampleResult result = new SampleResult();
        String interfaceName = javaSamplerContext.getParameter(SERVICE_PROVIDER_NAME);
        String methodName = javaSamplerContext.getParameter(METHOD_NAME);
        String parameters = javaSamplerContext.getParameter(PARAMETERS);
        log.info(String.format("interfaceName:[%s],methodName:[%s],invokeParams:[%s]", interfaceName, methodName, JSON.toJSONString(parameters)));
        DubboServiceFactory.getInstance().genericInvoke(interfaceName, methodName, getParameters(parameters));
        return result;
    }

    private List<Map<String, Object>> getParameters(String parameter) {
        return (List<Map<String, Object>>) JSON.parse(parameter);
    }

    @Override
    public void teardownTest(JavaSamplerContext context) {
        super.teardownTest(context);
    }
}
