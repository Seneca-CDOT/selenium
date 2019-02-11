package org.openqa.selenium.pluma;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * a {@link WebDriver} implementation that controls a JSDOM object running on a local machine
 *
 */

public class PlumaDriver extends RemoteWebDriver {

  public PlumaDriver() { this (PlumaDriverService.createDefaultService(), new PlumaOptions()); }

}
