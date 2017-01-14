package pl.sebcel.librus.defaultaccountprovider;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import pl.sebcel.librus.accountprovider.api.AccountProvider;
import pl.sebcel.librus.defaultaccountprovider.service.DefaultAccountProvider;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Activator implements BundleActivator {

    private Logger log = LogManager.getLogger(Activator.class);

    @Override
    public void start(BundleContext context) throws Exception {
        log.info("Log4j info");
        log.error("Log4j error");
        System.out.println("Starting Default Account Provider");

        System.out.println("Registering account provider service");
        DefaultAccountProvider provider = new DefaultAccountProvider(context);
        context.registerService(AccountProvider.class.getName(), provider, null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Stopping Default Account Provider");
    }
}