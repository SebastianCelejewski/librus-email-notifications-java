package pl.sebcel.librus.logging;

import org.osgi.framework.*;
import org.osgi.service.log.LogReaderService;
import org.osgi.util.tracker.ServiceTracker;
import pl.sebcel.librus.logging.pl.sebcel.librus.logging.service.LibrusLogListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Activator implements BundleActivator {

    private LibrusLogListener librusLogListener = new LibrusLogListener();
    private List<LogReaderService> logReaderServices = new ArrayList<LogReaderService>();

    private ServiceListener m_servlistener = new ServiceListener() {
        public void serviceChanged(ServiceEvent event) {
            BundleContext bundleContext = event.getServiceReference().getBundle().getBundleContext();
            LogReaderService logReaderService = (LogReaderService) bundleContext.getService(event.getServiceReference());
            if (logReaderService != null) {
                if (event.getType() == ServiceEvent.REGISTERED) {
                    System.out.println("A");
                    logReaderServices.add(logReaderService);
                    logReaderService.addLogListener(librusLogListener);
                } else if (event.getType() == ServiceEvent.UNREGISTERING) {
                    System.out.println("B");
                    logReaderService.removeLogListener(librusLogListener);
                    logReaderServices.remove(logReaderService);
                }
            }
        }
    };

    public void start(BundleContext context) throws Exception {
        System.out.println("Librus Email Notifications Logging bundle is starting");

        ServiceTracker logReaderTracker = new ServiceTracker(context, org.osgi.service.log.LogReaderService.class.getName(), null);
        logReaderTracker.open();
        Object[] readers = logReaderTracker.getServices();
        if (readers != null) {
            for (int i = 0; i < readers.length; i++) {
                LogReaderService lrs = (LogReaderService) readers[i];
                logReaderServices.add(lrs);
                lrs.addLogListener(librusLogListener);
            }
        }

        logReaderTracker.close();

        // Add the ServiceListener, but with a filter so that we only receive events related to LogReaderService
        String filter = "(objectclass=" + LogReaderService.class.getName() + ")";
        try {
            context.addServiceListener(m_servlistener, filter);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
        System.out.println("Librus Email Notifications Logging bundle is started");
    }

    public void stop(BundleContext context) throws Exception {
        System.out.println("Librus Email Notifications Logging bundle is stopping");
        for (Iterator<LogReaderService> i = logReaderServices.iterator(); i.hasNext(); ) {
            LogReaderService lrs = i.next();
            lrs.removeLogListener(librusLogListener);
            i.remove();
        }
        System.out.println("Librus Email Notifications Logging bundle is stopped");
    }
}