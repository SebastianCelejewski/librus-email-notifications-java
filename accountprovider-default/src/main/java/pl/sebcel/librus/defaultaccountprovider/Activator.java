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
        log.info("Starting Default Account Provider");

        log.info("Registering account provider service");
        DefaultAccountProvider provider = new DefaultAccountProvider(context);
        context.registerService(AccountProvider.class.getName(), provider, null);

        log.info("Default Account Provider started successfully");
   }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Stopping Default Account Provider");
    }
}