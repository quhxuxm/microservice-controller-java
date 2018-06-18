package com.igt.interactivegame.rgs.tool.msc;

import com.igt.interactivegame.rgs.tool.msc.api.ComponentTaskResult;
import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class GUI {
    private static final Logger logger=LoggerFactory.getLogger(GUI.class);
    private JFrame mainFrame;
    private Engine engine;

    public GUI(Engine engine) {
        this.engine = engine;
        this.initializeGui();
    }

    private void initializeGui() {
        this.mainFrame = new JFrame();
        this.mainFrame.setTitle("Micro Service Controller");
        JTabbedPane tabbedPane = new JTabbedPane();
        this.mainFrame.setContentPane(tabbedPane);
        Map<String, IComponent> components = this.engine.getComponents();
        components.forEach((key, component) -> {
            JPanel panel = new JPanel();
            tabbedPane.add(component.getName(), panel);
            Map<IComponentAction.ActionName, IComponentAction> actions = component.getActions();
            actions.forEach((actionKey, action) -> {
                JButton button = new JButton(actionKey.name());
                panel.add(button);
                button.addActionListener(a -> {
                    Optional<Future<ComponentTaskResult>> execResult = GUI.this.engine.exec(actionKey, component);
                    execResult.ifPresentOrElse(resultFuture -> {
                        try {
                            ComponentTaskResult result = resultFuture.get();
                            if(result.getStatus() == ComponentTaskResult.Status.SUCCESS){
                                logger.info("Success to execute");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, () -> {
                    });
                });
            });
        });
        this.mainFrame.pack();
    }

    public void start() {
        this.mainFrame.setVisible(true);
    }
}
