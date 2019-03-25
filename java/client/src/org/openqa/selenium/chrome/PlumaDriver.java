package org.openqa.selenium.chrome;


import static org.openqa.selenium.remote.CapabilityType.PROXY;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.html5.RemoteWebStorage;
import org.openqa.selenium.remote.service.DriverCommandExecutor;
import org.openqa.selenium.remote.service.DriverService;

import java.util.Objects;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public class PlumaDriver extends RemoteWebDriver implements WebStorage {

  private static class PlumaDriverCommandExecutor extends DriverCommandExecutor {
    public PlumaDriverCommandExecutor(DriverService service) { super(service); }
  }

  private RemoteWebStorage webStorage;

  public PlumaDriver() { this(new PlumaOptions());}

  public PlumaDriver(PlumaOptions options) {
    super(toExecutor(options), dropCapabilities(options));
    webStorage = new RemoteWebStorage(getExecuteMethod());
  }

  public PlumaDriver(PlumaDriverService service) { this(service, new PlumaOptions());}

  public PlumaDriver(PlumaDriverService service, PlumaOptions options) {
    super(new PlumaDriverCommandExecutor(service), dropCapabilities(options));
    webStorage = new RemoteWebStorage(getExecuteMethod());
  }

  private static CommandExecutor toExecutor(PlumaOptions options) {
    Objects.requireNonNull(options, "No options to construct executor from");

    PlumaDriverService.Builder builder =
        StreamSupport.stream(ServiceLoader.load(DriverService.Builder.class).spliterator(), false)
            .filter(b -> b instanceof PlumaDriverService.Builder)
            .map(b -> (PlumaDriverService.Builder) b)
            .findFirst().orElseThrow(WebDriverException::new);

    return new PlumaDriverCommandExecutor(builder.build());
  }

  @Override
  public void setFileDetector(FileDetector detector) {
    throw new WebDriverException(
        "Setting the file detector only works on remote webdriver instances obtained " +
        "via RemoteWebDriver"
    );
  }

  @Override
  public LocalStorage getLocalStorage() { return webStorage.getLocalStorage(); }

  @Override
  public SessionStorage getSessionStorage() { return webStorage.getSessionStorage(); }



  private static Capabilities dropCapabilities(Capabilities capabilities) {
    if (capabilities == null) {
      return new ImmutableCapabilities();
    }

    MutableCapabilities caps = new MutableCapabilities(capabilities);


    // Ensure that the proxy is in a state fit to be sent to the extension
    Proxy proxy = Proxy.extractFrom(capabilities);
    if (proxy != null) {
      caps.setCapability(PROXY, proxy);
    }

    return caps;
  }

}






































//import org.openqa.selenium.Capabilities;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebDriverException;
//import org.openqa.selenium.html5.LocalStorage;
//import org.openqa.selenium.html5.Location;
//import org.openqa.selenium.html5.LocationContext;
//import org.openqa.selenium.html5.SessionStorage;
//import org.openqa.selenium.html5.WebStorage;
//import org.openqa.selenium.interactions.HasTouchScreen;
//import org.openqa.selenium.interactions.TouchScreen;
//import org.openqa.selenium.mobile.NetworkConnection;
//import org.openqa.selenium.remote.FileDetector;
//import org.openqa.selenium.remote.RemoteTouchScreen;
//import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.remote.html5.RemoteLocationContext;
//import org.openqa.selenium.remote.html5.RemoteWebStorage;
//import org.openqa.selenium.remote.mobile.RemoteNetworkConnection;
//import org.openqa.selenium.remote.service.DriverCommandExecutor;


//
//
///**
// * a {@link WebDriver} implementation that controls a JSDOM object running on a local machine
// *
// */
//
//public class PlumaDriver extends RemoteWebDriver
//    implements LocationContext,  WebStorage, NetworkConnection {
//
//  private RemoteLocationContext locationContext;
//  private RemoteWebStorage webStorage;
//  private RemoteNetworkConnection networkConnection;
//
//  @Override
//  public void setFileDetector(FileDetector detector) {
//    throw new WebDriverException(
//        "Setting the file detector only works on remote webdriver instances obtained " +
//        "via RemoteWebDriver");
//  }
//
//  @Override
//  public LocalStorage getLocalStorage() {
//    return webStorage.getLocalStorage();
//  }
//
//  @Override
//  public SessionStorage getSessionStorage() {
//    return webStorage.getSessionStorage();
//  }
//
//  @Override
//  public Location location() {
//    return locationContext.location();
//  }
//
//  @Override
//  public void setLocation(Location location) {
//    locationContext.setLocation(location);
//  }
//
//  @Override
//  public ConnectionType getNetworkConnection() {
//    return networkConnection.getNetworkConnection();
//  }
//
//  @Override
//  public ConnectionType setNetworkConnection(ConnectionType type) {
//    return networkConnection.setNetworkConnection(type);
//  }
//
//  public PlumaDriver() { this(PlumaDriverService.createDefaultService(), new PlumaOptions()); }
//
//  public PlumaDriver(PlumaDriverService service) { this(service, new PlumaOptions()); }
//
//
//  public PlumaDriver(PlumaDriverService service, PlumaOptions options) {
//    //super(new DriverCommandExecutor(service), options);
//    this(service, (Capabilities) options);
//  }
//
//  /**
//   * Creates a new PlumaDriver instance with the specified options.
//   *
//   * @param options The options to use
//   */
//  public PlumaDriver(PlumaOptions options) {
//    this(PlumaDriverService.createDefaultService(), options);
//  }
//
//  @Deprecated
//  public PlumaDriver(PlumaDriverService service, Capabilities capabilities) {
//
//    super(new PlumaDriverCommandExecutor(service), capabilities);
//    locationContext = new RemoteLocationContext(getExecuteMethod());
//    webStorage = new RemoteWebStorage(getExecuteMethod());
//    networkConnection = new RemoteNetworkConnection(getExecuteMethod());
//  }
//
//
//}
//
