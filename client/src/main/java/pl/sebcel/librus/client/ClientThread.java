package pl.sebcel.librus.client;

import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;
import pl.sebcel.librus.accountprovider.api.AccountProvider;
import pl.sebcel.librus.accountprovider.api.LibrusAccount;

import java.util.List;

public class ClientThread implements Runnable {

    private boolean terminate = false;

    private ServiceTracker<AccountProvider, AccountProvider> accountProviderServiceTracker;
    private ServiceTracker<LogService, LogService> logServiceTracker;

    public ClientThread(ServiceTracker<AccountProvider, AccountProvider> accountProviderServiceTracker, ServiceTracker<LogService, LogService> logServiceTracker) {
        this.accountProviderServiceTracker = accountProviderServiceTracker;
        this.logServiceTracker = logServiceTracker;
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

                Thread.sleep(5000);
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
        try {
            LogService logService = logServiceTracker.waitForService(1000);
            if (logService != null) {
                logService.log(LogService.LOG_INFO, text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}