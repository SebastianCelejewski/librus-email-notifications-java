package pl.sebcel.librus.defaultaccountprovider;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Starting Default Account Provider");

    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Stopping Default Account Provider");
    }
}