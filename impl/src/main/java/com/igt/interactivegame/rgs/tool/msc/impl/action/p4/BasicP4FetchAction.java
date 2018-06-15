package com.igt.interactivegame.rgs.tool.msc.impl.action.p4;

import com.igt.interactivegame.rgs.tool.msc.api.ComponentTaskResult;
import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentAction;
import com.igt.interactivegame.rgs.tool.msc.impl.action.p4.configuration.P4ConfigurationProperties;
import com.perforce.p4java.client.IClient;
import com.perforce.p4java.exception.AccessException;
import com.perforce.p4java.exception.ConnectionException;
import com.perforce.p4java.impl.mapbased.rpc.CommandEnv;
import com.perforce.p4java.option.client.ParallelSyncOptions;
import com.perforce.p4java.option.client.SyncOptions;
import com.perforce.p4java.server.IServer;
import com.perforce.p4java.server.ServerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class BasicP4FetchAction implements IComponentAction {
    private static final Logger logger = LoggerFactory.getLogger(BasicP4FetchAction.class);
    private IComponent owner;
    private P4ConfigurationProperties p4ConfigurationProperties;

    public BasicP4FetchAction(IComponent owner, P4ConfigurationProperties p4ConfigurationProperties) {
        this.owner = owner;
        this.p4ConfigurationProperties = p4ConfigurationProperties;
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
        IServer p4Server = null;
        try {
            p4Server = ServerFactory.getServer(this.p4ConfigurationProperties.getServerUrl(), null);
            p4Server.setUserName(this.p4ConfigurationProperties.getUserName());
            p4Server.connect();
            p4Server.login(this.p4ConfigurationProperties.getPassword());
            IClient p4Client = p4Server.getClient(this.p4ConfigurationProperties.getClientName());
            p4Server.setCurrentClient(p4Client);
            ParallelSyncOptions parallelSyncOptions=new ParallelSyncOptions();
            parallelSyncOptions.setCallback((CommandEnv cmdEnv, int threads, HashMap<String, String> flags, ArrayList<String> args)->{
                return Boolean.TRUE;
            });
            p4Client.syncParallel(Collections.emptyList(), new SyncOptions("-f"), parallelSyncOptions);
            logger.info(String.format("Success to sync p4 source code for component [%s].", this.getOwner().getName()));
        } catch (Exception e) {
            logger.error("Fail to fetch p4 source code for component [{}] because of exception.", this.owner.getName(),
                    e);
            long endTime = System.nanoTime();
            return new ComponentTaskResult(ComponentTaskResult.Status.FAIL, this.owner, endTime - startTime,
                    e.getMessage());
        } finally {
            try {
                if (p4Server != null) {
                    p4Server.disconnect();
                }
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
