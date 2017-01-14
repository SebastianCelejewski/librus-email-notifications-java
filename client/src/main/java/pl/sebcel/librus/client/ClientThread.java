package pl.sebcel.librus.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.osgi.util.tracker.ServiceTracker;
import pl.sebcel.librus.accountprovider.api.AccountProvider;
import pl.sebcel.librus.accountprovider.api.LibrusAccount;

import java.util.List;

public class ClientThread implements Runnable {

    private Logger log = LogManager.getLogger(ClientThread.class);

    private boolean terminate = false;

    private ServiceTracker<AccountProvider, AccountProvider> accountProviderServiceTracker;

    public ClientThread(ServiceTracker<AccountProvider, AccountProvider> accountProviderServiceTracker) {
        this.accountProviderServiceTracker = accountProviderServiceTracker;
    }

    @Override
    public void run() {
        terminate = false;

        log.info("Starting Librus Email Notifications client thread");

        while (!terminate) {
            try {
                log.debug("Client thread run");
                log.debug("Account providers: " + accountProviderServiceTracker.size());
                AccountProvider accountProvider = accountProviderServiceTracker.getService();
                if (accountProvider != null) {
                    log.debug("Getting configuration from account provider");

                    List<LibrusAccount> librusAccounts = accountProvider.getLibrusAccounts();
                    log.debug("Loaded " + librusAccounts.size()+" librus accounts information");
                }

                Thread.sleep(30000);
            } catch (Exception ex) {
                // intentional
            }
        }

        log.info("Stopped Librus Email Notifications client thread");
    }

    public void terminate() {
        this.terminate = true;
    }
}