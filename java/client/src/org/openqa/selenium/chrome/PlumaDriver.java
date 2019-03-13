package org.openqa.selenium.chrome;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.html5.LocationContext;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.mobile.NetworkConnection;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.RemoteTouchScreen;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.html5.RemoteLocationContext;
import org.openqa.selenium.remote.html5.RemoteWebStorage;
import org.openqa.selenium.remote.mobile.RemoteNetworkConnection;
import org.openqa.selenium.remote.service.DriverCommandExecutor;

/**
 * a {@link WebDriver} implementation that controls a JSDOM object running on a local machine
 *
 */

public class PlumaDriver extends RemoteWebDriver
    implements LocationContext,  WebStorage, NetworkConnection {

  private RemoteLocationContext locationContext;
  private RemoteWebStorage webStorage;
  private RemoteNetworkConnection networkConnection;

  @Override
  public void setFileDetector(FileDetector detector) {
    throw new WebDriverException(
        "Setting the file detector only works on remote webdriver instances obtained " +
        "via RemoteWebDriver");
  }

  @Override
  public LocalStorage getLocalStorage() {
    return webStorage.getLocalStorage();
  }

  @Override
  public SessionStorage getSessionStorage() {
    return webStorage.getSessionStorage();
  }

  @Override
  public Location location() {
    return locationContext.location();
  }

  @Override
  public void setLocation(Location location) {
    locationContext.setLocation(location);
  }

  @Override
  public ConnectionType getNetworkConnection() {
    return networkConnection.getNetworkConnection();
  }

  @Override
  public ConnectionType setNetworkConnection(ConnectionType type) {
    return networkConnection.setNetworkConnection(type);
  }

  public PlumaDriver() { this(PlumaDriverService.createDefaultService(), new PlumaOptions()); }

  public PlumaDriver(PlumaDriverService service) { this(service, new PlumaOptions()); }


  public PlumaDriver(PlumaDriverService service, PlumaOptions options) {
    //super(new DriverCommandExecutor(service), options);
    this(service, (Capabilities) options);
  }

  /**
   * Creates a new PlumaDriver instance with the specified options.
   *
   * @param options The options to use
   */
  public PlumaDriver(PlumaOptions options) {
    this(PlumaDriverService.createDefaultService(), options);
  }

  @Deprecated
  public PlumaDriver(PlumaDriverService service, Capabilities capabilities) {

    super(new PlumaDriverCommandExecutor(service), capabilities);
    locationContext = new RemoteLocationContext(getExecuteMethod());
    webStorage = new RemoteWebStorage(getExecuteMethod());
    networkConnection = new RemoteNetworkConnection(getExecuteMethod());
  }


}

