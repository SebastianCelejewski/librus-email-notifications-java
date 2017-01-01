package pl.sebcel.librus.client;

import org.osgi.util.tracker.ServiceTracker;
import pl.sebcel.librus.accountprovider.api.AccountProvider;
import pl.sebcel.librus.accountprovider.api.LibrusAccount;

import java.util.List;

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
                AccountProvider accountProvider = accountProviderServiceTracker.getService();
                if (accountProvider != null) {
                    System.out.println("Getting configuration from account provider");

                    List<LibrusAccount> librusAccounts = accountProvider.getLibrusAccounts();
                    System.out.println("Loaded " + librusAccounts.size()+" librus accounts information");
                }

                Thread.sleep(5000);
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