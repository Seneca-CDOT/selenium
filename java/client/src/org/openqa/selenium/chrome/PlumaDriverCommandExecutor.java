package org.openqa.selenium.chrome;

import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.service.DriverCommandExecutor;
import org.openqa.selenium.remote.service.DriverService;

/**
 * {@link DriverCommandExecutor} that understands PlumaDriver specific commands.
 */
class PlumaDriverCommandExecutor extends DriverCommandExecutor {

  private static final ImmutableMap<String, CommandInfo> PLUMA_COMMAND_NAME_TO_URL = ImmutableMap.of(
    // TODO: add plumadriver specific commands here in the future
  );

  public PlumaDriverCommandExecutor(DriverService service) {
    super(service, PLUMA_COMMAND_NAME_TO_URL);
  }

}
