package pl.sebcel.librus.client;

import org.osgi.util.tracker.ServiceTracker;
import pl.sebcel.librus.accountprovider.api.AccountProvider;

public class ClientThread implements Runnable {

    private boolean terminate = false;
    private ServiceTracker<AccountProvider, AccountProvider> accountProviderServiceTracker;

    public ClientThread(ServiceTracker<AccountProvider, AccountProvider> accountProviderServiceTracker) {
        this.accountProviderServiceTracker = accountProviderServiceTracker;
    }

    @Override
    public void run() {
        terminate = false;
        System.out.println("Starting Librus Email Notifications client thread");

        while (!terminate) {
            try {
                System.out.println("Client thread run");
                System.out.println("Account providers: " + accountProviderServiceTracker.size());
                Thread.sleep(1000);
            } catch (Exception ex) {
                // intentional
            }
        }

        System.out.println("Stopped Librus Email Notifications client thread");
    }

    public void terminate() {
        this.terminate = true;
    }
}