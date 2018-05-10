package com.yiming.jmeter.dubbo.sampler;

import lombok.Data;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestStateListener;

/**
 * Created by yiming on 2018-05-10.
 * Description:
 */
@Data
public class DubboSampler extends AbstractSampler implements TestStateListener {

    public static final String DEFAULT_LABEL_NAME = "Dubbo Sampler";

    private String interfaceName;

    private String serviceProviderName;

    private String simpleName;

    private String message;

    @Override
    public SampleResult sample(Entry entry) {
        SampleResult result = new SampleResult();
        result.setSampleLabel(simpleName);

        try {
            result.sampleStart();
            //对目标系统发出测试请求
            sendDubboRequest();
            result.sampleEnd();
            result.setSuccessful(true);
            result.setResponseCodeOK();
        } catch (Exception e) {
            result.sampleEnd(); // stop stopwatch
            result.setSuccessful(false);
            result.setResponseMessage("Exception: " + e);
            // get stack trace as a String to return as document data
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            e.printStackTrace(new java.io.PrintWriter(stringWriter));
            result.setResponseData(stringWriter.toString(), null);
            result.setDataType(org.apache.jmeter.samplers.SampleResult.TEXT);
            result.setResponseCode("FAILED");
        }
        return result;
    }

    //todo 发起dubbo请求
    private void sendDubboRequest() {
        System.out.println("send dubbo request");
    }

    @Override
    public void testStarted() {

    }

    @Override
    public void testStarted(String s) {

    }

    @Override
    public void testEnded() {

    }

    @Override
    public void testEnded(String s) {

    }
}
