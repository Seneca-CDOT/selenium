package org.openqa.selenium.chrome;

import static com.google.common.base.Preconditions.checkNotNull;

import org .openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;

import java.io.File;
import java.io.IOException;

/**
 * Class to manage options specific to {@link PlumaDriver}.
 *
 */

public class PlumaOptions extends MutableCapabilities {

  private String binary;


  public PlumaOptions () { setCapability(CapabilityType.BROWSER_NAME, BrowserType.PLUMA);}

  @Override
  public PlumaOptions merge(Capabilities extraCapabilities) {
    super.merge(extraCapabilities);
    return this;
  }

  // TODO: include method to allow script execution for browser
  // TODO: include method to allow browser to intervene before parsing
  // TODO: include method to load resources (iframes, stylesheets, scripts, images... need to integrate npm canvas in server source


  /**
   * Sets the path to the Pluma executable. The path should exist on the machine
   * which will launch Pluma. Path should either be absolute or relative
   * to the location of running PlumaDriver server.
   * @param path Path to Pluma executable
   */
  public PlumaOptions setBinary(File path) {
    binary = checkNotNull(path).getPath();
    return this;
  }

  /**
   * Sets the path to the Pluma executable. The path should exist on the machine
   * which will launch Pluma. Path should either be absolute or relative
   * to the location of running PlumaDriver server.
   * @param path Path to Pluma executable
   */
  public PlumaOptions setBinary(String path) {
    binary = checkNotNull(path);
    return this;
  }

  public PlumaOptions setProxy(Proxy proxy) {
    setCapability(CapabilityType.PROXY, proxy);
    return this;
  }


}
