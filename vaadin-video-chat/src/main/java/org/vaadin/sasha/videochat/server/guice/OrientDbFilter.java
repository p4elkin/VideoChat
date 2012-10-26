package org.vaadin.sasha.videochat.server.guice;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.google.inject.Provider;
import com.orientechnologies.orient.core.storage.OStorage;
import com.orientechnologies.orient.object.db.OObjectDatabasePool;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

@Singleton
public class OrientDbFilter implements Filter {
    
    @Inject
    private Provider<OObjectDatabaseTx> databaseProvider;
    
    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(req, res);   
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseProvider.get().close();   
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        OObjectDatabaseTx database = OObjectDatabasePool.global().acquire("remote:localhost/videochat", "root", "root");
        if (!(database.getMetadata().getSchema().existsClass("User"))) {
            database.getMetadata().getSchema().createClass(
                    "User", database.addCluster("user", OStorage.CLUSTER_TYPE.PHYSICAL));
            database.getMetadata().getSchema().save();
        }
    }

}
