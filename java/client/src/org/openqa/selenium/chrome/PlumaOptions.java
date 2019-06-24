package org.openqa.selenium.chrome;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.openqa.selenium.remote.CapabilityType.ACCEPT_INSECURE_CERTS;
import static org.openqa.selenium.remote.CapabilityType.PAGE_LOAD_STRATEGY;
import static org.openqa.selenium.remote.CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR;
import static org.openqa.selenium.remote.CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR;

import com.google.common.base.FinalizablePhantomReference;
import com.google.common.collect.ImmutableSortedSet;

import org .openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class to manage options specific to {@link PlumaDriver}.
 *
 */

public class PlumaOptions extends MutableCapabilities {

  final static String PLUMA_OPTIONS = "plm:plumaOptions";

  private final static String RUN_SCRIPTS = "runScripts";
  private final static String LOAD_SUBRESOURCES = "resources";

  private final static Set<String> CAPABILITY_NAMES = ImmutableSortedSet.<String>naturalOrder()
      .add(RUN_SCRIPTS)
      .add(LOAD_SUBRESOURCES)
      .build();

  private Map<String, Object> plumaOptions = new HashMap<>();

  private String binary;


  public PlumaOptions () {
    setCapability(CapabilityType.BROWSER_NAME, BrowserType.PLUMA);
    setCapability(PLUMA_OPTIONS, plumaOptions);
  }

  public PlumaOptions(Capabilities source) {
    this();
    merge(source);
  }

  @Override
  public PlumaOptions merge(Capabilities extraCapabilities) {
    super.merge(extraCapabilities);
    return this;
  }

  @Override
  public void setCapability(String key, Object value) {

    super.setCapability(key,value);

    if (CAPABILITY_NAMES.contains(key)) {
      plumaOptions.put(key, value);
    }

    if(PLUMA_OPTIONS.equals(key)){
      plumaOptions.clear();
      Map<?,?> streamFrom;
      if(value instanceof Map) {
        streamFrom = (Map<?,?>) value;
      } else if (value instanceof Capabilities) {
        streamFrom = ((Capabilities) value).asMap();
      } else {
        throw new IllegalArgumentException("Value must not be null for " + key);
      }

      streamFrom.entrySet().stream()
          .filter(e -> CAPABILITY_NAMES.contains(e.getKey()))
          .filter(e -> e.getValue() != null)
          .forEach( e -> {
            setCapability((String) e.getKey(), value);
          });
    }
  }


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

  public PlumaOptions setPageLoadStrategy(PageLoadStrategy strategy) {
    amend(PAGE_LOAD_STRATEGY, strategy);
    return this;
  }

  // unhandled prompt behaviour should be set by default, therefore use amend instead of setCapability
  public PlumaOptions setUnhandledPromptBehaviour(UnexpectedAlertBehaviour behaviour) {
    amend(UNHANDLED_PROMPT_BEHAVIOUR, behaviour);
    return this;
  }


  public PlumaOptions setAcceptInsecureCerts(boolean acceptInsecureCerts) {
    amend(ACCEPT_INSECURE_CERTS, acceptInsecureCerts);
    return this;
  }


  /**
   * sets capability for pluma remote endpoint to run scripts
   * @param runScripts
   * @return
   */
  public PlumaOptions setRunScripts(Boolean runScripts) {
    amend(RUN_SCRIPTS, runScripts);
    return this;
  }

  private PlumaOptions amend(String optionName, Object value) {
    setCapability(optionName,value);
    return this;
  }
}
