package pl.sebcel.librus.defaultaccountprovider.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.osgi.framework.BundleContext;
import pl.sebcel.librus.accountprovider.api.AccountProvider;
import pl.sebcel.librus.accountprovider.api.LibrusAccount;
import pl.sebcel.librus.defaultaccountprovider.Activator;

import java.util.ArrayList;
import java.util.List;

public class DefaultAccountProvider implements AccountProvider {

    private Logger log = LogManager.getLogger(DefaultAccountProvider.class);

    private BundleContext bundleContext;

    public DefaultAccountProvider(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public List<LibrusAccount> getLibrusAccounts() {
        List<LibrusAccount> result = new ArrayList<>();

        int i = 1;

        while (true) {
            String loginKey = "pl.sebcel.librus.account." + i + ".name";
            String passwordKey = "pl.sebcel.librus.account." + i + ".password";
            String recipientsKey = "pl.sebcel.librus.account." + i + ".recipients";

            String login = bundleContext.getProperty(loginKey);
            String password = bundleContext.getProperty(passwordKey);
            String recipientsList = bundleContext.getProperty(recipientsKey);

            if (login == null) {
                break;
            }

            List<String> recipients = new ArrayList<>();

            LibrusAccount account = new LibrusAccount(login, password, recipients);
            result.add(account);
            log.info("Loaded Librus account configuration for login " + login);

            i += 1;
        }

        return result;
    }
}