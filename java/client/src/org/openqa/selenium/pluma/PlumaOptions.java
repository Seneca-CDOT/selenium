package org.openqa.selenium.pluma;

import static com.google.common.base.Preconditions.checkNotNull;

import org .openqa.selenium.Capabilities;
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



}
