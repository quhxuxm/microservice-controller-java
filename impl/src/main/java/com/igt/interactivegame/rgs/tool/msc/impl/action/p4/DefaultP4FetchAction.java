package com.igt.interactivegame.rgs.tool.msc.impl.action.p4;

import com.igt.interactivegame.rgs.tool.msc.api.ComponentTaskResult;
import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentAction;
import com.igt.interactivegame.rgs.tool.msc.impl.action.p4.configuration.P4ClientConfigurationProperties;
import com.perforce.p4java.client.IClient;
import com.perforce.p4java.exception.AccessException;
import com.perforce.p4java.exception.ConnectionException;
import com.perforce.p4java.exception.P4JavaException;
import com.perforce.p4java.option.client.SyncOptions;
import com.perforce.p4java.server.IServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class DefaultP4FetchAction implements IComponentAction {
    private static final Logger logger = LoggerFactory.getLogger(DefaultP4FetchAction.class);
    private IServer p4Server;
    private IComponent owner;
    private P4ClientConfigurationProperties clientConfigurationProperties;

    public DefaultP4FetchAction(IComponent owner, IServer p4Server, P4ClientConfigurationProperties clientConfigurationProperties) {
        this.owner = owner;
        this.p4Server = p4Server;
        this.clientConfigurationProperties = clientConfigurationProperties;
    }

    @Override
    public ActionName getName() {
        return ActionName.P4_FETCH;
    }

    @Override
    public IComponent getOwner() {
        return this.owner;
    }

    @Override
    public ComponentTaskResult exec() {
        long startTime = System.nanoTime();
        try {
            this.p4Server.connect();
            IClient p4Client = this.p4Server.getClient(this.clientConfigurationProperties.getClientName());
            p4Client.sync(Collections.emptyList(), new SyncOptions("-f"));
            logger.info(String.format("Success to sync p4 source code for component [%s].", this.getOwner().getName()));
        } catch (P4JavaException e) {
            logger.error("Fail to fetch p4 source code for component [{}] because of exception.", this.owner.getName(), e);
            long endTime = System.nanoTime();
            return new ComponentTaskResult(ComponentTaskResult.Status.FAIL, this.owner, endTime - startTime, e.getMessage());
        } finally {
            try {
                this.p4Server.disconnect();
            } catch (ConnectionException | AccessException e) {
                logger.error("Fail to disconnect p4 because of exception.", e);
            }
        }
        long endTime = System.nanoTime();
        logger.info("Success to fetch p4 source code for component [{}].", this.owner.getName());
        return new ComponentTaskResult(ComponentTaskResult.Status.SUCCESS, this.owner, endTime - startTime,
                String.format("Success to fetch p4 source code for component [%s]", this.owner.getName()));
    }
}
