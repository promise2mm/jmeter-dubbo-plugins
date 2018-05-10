package com.yiming.jmeter.dubbo.gui;

import com.yiming.jmeter.dubbo.sampler.DubboSampler;
import lombok.extern.slf4j.Slf4j;
import org.apache.jmeter.gui.util.JSyntaxTextArea;
import org.apache.jmeter.gui.util.JTextScrollPane;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledTextField;

import javax.swing.*;
import java.awt.*;

/**
 * Created by yiming on 2018-05-10 12:30.
 * Description:
 */
@Slf4j
public class DubboSamplerUI extends AbstractSamplerGui {

    private static final long serialVersionUID = 1L;
    private final JLabeledTextField serviceProviderNameField = new JLabeledTextField("ServiceProviderName");
    private final JLabeledTextField simpleNameField = new JLabeledTextField("SimpleName");
    private final JSyntaxTextArea textMessage = new JSyntaxTextArea(10, 50);
    private final JLabel textArea = new JLabel("Message");
    private final JTextScrollPane textPanel = new JTextScrollPane(textMessage);


    public DubboSamplerUI() {
        super();
        this.init();
    }

    private void init() {
        log.info("Initializing the UI.");
        setLayout(new BorderLayout());
        setBorder(makeBorder());

        add(makeTitlePanel(), BorderLayout.NORTH);
        JPanel mainPanel = new VerticalPanel();
        add(mainPanel, BorderLayout.CENTER);

        JPanel DPanel = new JPanel();
        DPanel.setLayout(new GridLayout(3, 2));
        DPanel.add(serviceProviderNameField);
        DPanel.add(simpleNameField);

        JPanel ControlPanel = new VerticalPanel();
        ControlPanel.add(DPanel);
        ControlPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "Parameters"));
        mainPanel.add(ControlPanel);

        JPanel ContentPanel = new VerticalPanel();
        JPanel messageContentPanel = new JPanel(new BorderLayout());
        messageContentPanel.add(this.textArea, BorderLayout.NORTH);
        messageContentPanel.add(this.textPanel, BorderLayout.CENTER);
        ContentPanel.add(messageContentPanel);
        ContentPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "Content"));
        mainPanel.add(ContentPanel);
    }

    @Override
    public String getLabelResource() {
        return DubboSampler.DEFAULT_LABEL_NAME;
    }

    @Override
    public TestElement createTestElement() {
        DubboSampler sampler = new DubboSampler();
        this.setupSamplerProperties(sampler);
        return sampler;
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);
        DubboSampler sampler = (DubboSampler) element;
        this.serviceProviderNameField.setText(sampler.getServiceProviderName());
        this.simpleNameField.setText(sampler.getSimpleName());
        //this.keyField.setText(sampler.getKey());
        this.textMessage.setText(sampler.getMessage());
    }

    private void setupSamplerProperties(DubboSampler sampler) {
        this.configureTestElement(sampler);
        sampler.setMessage(this.textMessage.getText());
    }

    @Override
    public void modifyTestElement(TestElement testElement) {
        DubboSampler sampler = (DubboSampler) testElement;
        this.setupSamplerProperties(sampler);
    }
}
