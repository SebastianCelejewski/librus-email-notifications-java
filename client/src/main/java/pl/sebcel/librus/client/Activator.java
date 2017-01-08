package pl.sebcel.librus.client;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;
import pl.sebcel.librus.accountprovider.api.AccountProvider;

public class Activator implements BundleActivator {

    private ClientThread clientThread;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Starting Librus Email Notifications Client");

        ServiceTracker<AccountProvider, AccountProvider> accountProviderServiceTracker = new ServiceTracker<>(context, AccountProvider.class.getName(), null);
        accountProviderServiceTracker.open();

        ServiceTracker<LogService, LogService> logServiceTracker = new ServiceTracker<LogService, LogService>(context, LogService.class.getName(), null);
        logServiceTracker.open();

        clientThread = new ClientThread(accountProviderServiceTracker, logServiceTracker);
        new Thread(clientThread).start();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Stopping Librus Email Notifications Client");
        clientThread.terminate();
    }
}