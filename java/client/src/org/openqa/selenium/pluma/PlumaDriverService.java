package org.openqa.selenium.pluma;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.service.DriverService;

import java.io.File;
import java.io.IOException;
import java.sql.Driver;

/**
 * Manages the life and death of a PlumaDriver server
 */
public class PlumaDriverService extends DriverService{
  /**
   * System property that defines the location of the chromedriver executable that will be used by
   * the {@link #createDefaultService() default service}.
   */
  public static final String PLUMA_DRIVER_EXE_PROPERTY = "webdriver.pluma.driver";

  /**
   * System property that defines the location of the log that will be written by
   * the {@link #createDefaultService() default service}.
   */
  public static final String PLUMA_DRIVER_LOG_PROPERTY = "webdriver.pluma.driver";


  /**
   * @param executable  The plumadriver executable.
   * @param port        Which port to start the PlumaDriver on.
   * @param args        The arguments to the launched server.
   * @param environment The environment for the launched server.
   * @throws IOException If an I/O error occurs.
   */
  public PlumaDriverService(
      File executable,
      int port,
      ImmutableList<String> args,
      ImmutableMap<String, String> environment) throws IOException {
    super(executable,port,args,environment);
  }

  /**
   * Configures and returns a new {@link PlumaDriverService} using the default configuration. In
   * this configuration, the service will use the plumadriver executable identified by the
   * {@link #PLUMA_DRIVER_EXE_PROPERTY} system property. Each service created by this method will
   * be configured to use a free port on the current system.
   *
   * @return A new PlumaDriverService using the default configuration.
   */
  public static PlumaDriverService createDefaultService() {
    return new Builder().build();
  }

  /**
   * Builder used to configure new {@link PlumaDriverService} instances
   */
  @AutoService(DriverService.Builder.class)
  public static class Builder extends DriverService.Builder<
      PlumaDriverService, PlumaDriverService.Builder> {

    @Override
    public int score(Capabilities capabilities) {
      int score = 0;

      if(BrowserType.PLUMA.equals(capabilities.getBrowserName())) {
        score++;
      }

//      if (capabilities.getCapability(PlumaOptions.CAPABILITY) != null) {
//        score++;
//      }
      return score;
    }


    @Override
    protected File findDefaultExecutable() {
      return findExecutable(
          "plumadriver", PLUMA_DRIVER_EXE_PROPERTY,
          "link to plumadriver selenium exe docs should be inserted here:",
          "insert link to download executable file for pluma driver");
    }

    @Override
    protected ImmutableList<String> createArgs() {
      if(getLogFile() == null) {
        String logFilePath = System.getProperty(PLUMA_DRIVER_LOG_PROPERTY);
        if(logFilePath != null) {
          withLogFile(new File(logFilePath));
        }
      }

      ImmutableList.Builder<String> argsBuilder = ImmutableList.builder();
      argsBuilder.add(String.format("--port=%d", getPort()));

     return argsBuilder.build();
    }

    @Override
    protected PlumaDriverService createDriverService(
        File exe,
        int port,
        ImmutableList<String> args,
        ImmutableMap<String, String> environment) {
      try {
        return new PlumaDriverService(exe,port,args,environment);

      } catch (IOException exception) {
        throw new WebDriverException(exception);
      }

    }


  }





}
