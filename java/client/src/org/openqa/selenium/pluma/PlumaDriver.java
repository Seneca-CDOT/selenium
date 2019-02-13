package org.openqa.selenium.pluma;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverCommandExecutor;

/**
 * a {@link WebDriver} implementation that controls a JSDOM object running on a local machine
 *
 */

public class PlumaDriver extends RemoteWebDriver {

  public PlumaDriver() { this(PlumaDriverService.createDefaultService(), new PlumaOptions()); }

  public PlumaDriver(PlumaDriverService service) { this(service, new PlumaOptions()); }

  public PlumaDriver(PlumaDriverService service, PlumaOptions options) {
    super(new DriverCommandExecutor(service), options);
  }

  public PlumaDriver(PlumaOptions options) {
    this(PlumaDriverService.createDefaultService(), options);
  }
}
