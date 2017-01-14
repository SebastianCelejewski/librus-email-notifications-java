package pl.sebcel.librus.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.util.tracker.ServiceTracker;
import pl.sebcel.librus.accountprovider.api.AccountProvider;

public class Activator implements BundleActivator {

    private ClientThread clientThread;

    private Logger log = LogManager.getLogger(Activator.class);

    @Override
    public void start(BundleContext context) throws Exception {

        log.info("Starting Librus Email Notifications Client");
        context.getBundle().adapt(BundleStartLevel.class).setStartLevel(5);

        ServiceTracker<AccountProvider, AccountProvider> accountProviderServiceTracker = new ServiceTracker<>(context, AccountProvider.class.getName(), null);
        accountProviderServiceTracker.open();

        clientThread = new ClientThread(accountProviderServiceTracker);
        new Thread(clientThread).start();

        log.info("Librus Email Notifications Client started successfully");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        log.info("Stopping Librus Email Notifications Client");
        clientThread.terminate();
    }
}