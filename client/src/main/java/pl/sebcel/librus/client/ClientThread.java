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

        logInfo("Starting Librus Email Notifications client thread");

        while (!terminate) {
            try {
                logInfo("Client thread run");
                logInfo("Account providers: " + accountProviderServiceTracker.size());
                AccountProvider accountProvider = accountProviderServiceTracker.getService();
                if (accountProvider != null) {
                    logInfo("Getting configuration from account provider");

                    List<LibrusAccount> librusAccounts = accountProvider.getLibrusAccounts();
                    logInfo("Loaded " + librusAccounts.size()+" librus accounts information");
                }

                Thread.sleep(30000);
            } catch (Exception ex) {
                // intentional
            }
        }

        logInfo("Stopped Librus Email Notifications client thread");
    }

    public void terminate() {
        this.terminate = true;
    }

    private void logInfo(String text) {
        System.out.println(text);
    }
}