package org.openqa.selenium.chrome;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableList;
import com.google.common.io.ByteStreams;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.net.PortProber;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.service.DriverService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class PlumaDriverService extends DriverService {
  /**
   * @param executable The PlumaDriver executable.
   * @param port Which port to start the PlumaDriver on.
   * @param args The arguments to the launched server.
   * @param environment The environment for the launched server.
   * @throws IOException If an I/O error occurs.
   */
  public PlumaDriverService(
      File executable,
      int port,
      ImmutableList<String> args,
      ImmutableMap<String, String> environment) throws IOException {
    super(executable, port, args, environment);
  }

//  public static abstract class Builder<DS extends PlumaDriverService, B extends PlumaDriverService.Builder<?, ?>>
//      extends DriverService.Builder<DS,B> {
//
//    protected abstract Builder withOptions(PlumaOptions options);
//  }

  /**
   //   * System property that defines the location of the plumadriver executable that will be used by
   //   * the {@link #createDefaultService() default service}.
   //   */
  public static final String PLUMA_DRIVER_EXE_PROPERTY = "webdriver.pluma.driver";

  /**
   * System property that defines the location of the log that will be written by
   * the {@link #createDefaultService() default service}.
   */
  public static final String PLUMA_DRIVER_LOG_PROPERTY = "webdriver.pluma.logfile";

  public static PlumaDriverService createDefaultService() { return new Builder().build(); }

  @Override
  protected  void waitUntilAvailable()throws MalformedURLException {
      PortProber.waitForPortUp(getUrl().getPort(), 20, TimeUnit.SECONDS);
  }

  @Override
  protected boolean hasShutdownEndpoint() { return false;}

  /**
   * builder used to configure new {@link PlumaDriverService} instances
   */
  @AutoService(DriverService.Builder.class)
  public static class Builder extends DriverService.Builder<
      PlumaDriverService, PlumaDriverService.Builder> {
    public Builder(){
    }

    @Override
    public int score(Capabilities capabilities) {
      int score = 0;

      if(BrowserType.PLUMA.equals(capabilities.getBrowserName())) {
        score++;
      }
      return score;
    }

    @Override
    protected File findDefaultExecutable() {
      return findExecutable(
          "plumadriver", PLUMA_DRIVER_EXE_PROPERTY,
          "add link to pluma driver here",
          "add link to plumadriver executable here"
      );
    }

    @Override
    protected ImmutableList<String> createArgs() {
      if (getLogFile() == null) {
        String logFilePath = System.getProperty(PLUMA_DRIVER_LOG_PROPERTY);
        if (logFilePath != null) {
          withLogFile(new File(logFilePath));
        }
      }
      ImmutableList.Builder<String> argsBuilder = ImmutableList.builder();
      argsBuilder.add(String.format("--port=%d", getPort()));

      return argsBuilder.build();
    }

    @Override
    protected PlumaDriverService createDriverService(File exe, int port,
                                                     ImmutableList<String> args,
                                                     ImmutableMap<String, String> environment) {
      try {
        PlumaDriverService service = new PlumaDriverService(exe, port, args, environment);
        String plumaLogFile = System.getProperty("webdriver.pluma.logfile");
        if (plumaLogFile != null) {
          if ("/dev/stdout".equals(plumaLogFile)) {
            service.sendOutputTo(System.out);
            service.sendOutputTo(System.out);
          } else if ("/dev/stderr".equals(plumaLogFile)) {
            service.sendOutputTo(System.err);
          } else if ("/dev/null".equals(plumaLogFile)) {
            service.sendOutputTo(ByteStreams.nullOutputStream());
          } else {
            service.sendOutputTo(new FileOutputStream(plumaLogFile));
          }
        }
        return service;
      } catch(IOException e) {
        throw new WebDriverException(e);
      }
    }
  }


}

//
//import com.google.auto.service.AutoService;
//import com.google.common.collect.ImmutableList;
//import com.google.common.collect.ImmutableMap;
//
//import org.openqa.selenium.Capabilities;
//import org.openqa.selenium.WebDriverException;
//import org.openqa.selenium.remote.BrowserType;
//import org.openqa.selenium.remote.service.DriverService;
//
//import java.io.File;
//import java.io.IOException;
//
///**
// * Manages the life and death of a PlumaDriver server
// */
//public class PlumaDriverService extends DriverService{
//  /**
//   * System property that defines the location of the plumadriver executable that will be used by
//   * the {@link #createDefaultService() default service}.
//   */
//  public static final String PLUMA_DRIVER_EXE_PROPERTY = "webdriver.pluma.driver";
//
//  /**
//   * System property that defines the location of the log that will be written by
//   * the {@link #createDefaultService() default service}.
//   */
//  public static final String PLUMA_DRIVER_LOG_PROPERTY = "webdriver.pluma.logfile";
//
//
//
//  /**
//   * @param executable  The plumadriver executable.
//   * @param port        Which port to start the PlumaDriver on.
//   * @param args        The arguments to the launched server.
//   * @param environment The environment for the launched server.
//   * @throws IOException If an I/O error occurs.
//   */
//  public PlumaDriverService(
//      File executable,
//      int port,
//      ImmutableList<String> args,
//      ImmutableMap<String, String> environment) throws IOException {
//    super(executable,port,args,environment);
//  }
//
//  /**
//   * Configures and returns a new {@link PlumaDriverService} using the default configuration. In
//   * this configuration, the service will use the plumadriver executable identified by the
//   * {@link #PLUMA_DRIVER_EXE_PROPERTY} system property. Each service created by this method will
//   * be configured to use a free port on the current system.
//   *
//   * @return A new PlumaDriverService using the default configuration.
//   */
//  public static PlumaDriverService createDefaultService() {
//    return new Builder().build();
//  }
//
//  /**
//   * Builder used to configure new {@link PlumaDriverService} instances
//   */
//  @AutoService(DriverService.Builder.class)
//  public static class Builder extends DriverService.Builder<
//      PlumaDriverService, PlumaDriverService.Builder> {
//
//    // TODO: add verbose logging option for plumadriver
//    // TODO: add silent output property for plumadriver
//
//
//
//    @Override
//    public int score(Capabilities capabilities) {
//      int score = 0;
//
//      if(BrowserType.PLUMA.equals(capabilities.getBrowserName())) {
//        score++;
//      }
//      return score;
//    }
//
//
//    @Override
//    protected File findDefaultExecutable() {
//      return findExecutable(
//          "plumadriver", PLUMA_DRIVER_EXE_PROPERTY,
//          "link to plumadriver selenium exe docs should be inserted here:",
//          "insert link to download executable file for pluma driver");
//    }
//
//    @Override
//    protected ImmutableList<String> createArgs() {
//      if(getLogFile() == null) {
//        String logFilePath = System.getProperty(PLUMA_DRIVER_LOG_PROPERTY);
//        if(logFilePath != null) {
//          withLogFile(new File(logFilePath));
//        }
//      }
//
//      ImmutableList.Builder<String> argsBuilder = ImmutableList.builder();
//      argsBuilder.add(String.format("--port=%d", getPort()));
//
//      return argsBuilder.build();
//    }
//
//    @Override
//    protected PlumaDriverService createDriverService(
//        File exe,
//        int port,
//        ImmutableList<String> args,
//        ImmutableMap<String, String> environment) {
//      try {
//        return new PlumaDriverService(exe,port,args,environment);
//
//      } catch (IOException exception) {
//        throw new WebDriverException(exception);
//      }
//
//    }
//  }
//}
