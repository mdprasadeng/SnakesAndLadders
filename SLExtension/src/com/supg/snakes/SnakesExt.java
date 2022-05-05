package com.supg.snakes;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseSFSExtension;
import java.util.ArrayList;
import java.util.List;

public class SnakesExt extends BaseSFSExtension {

  private List<String> userInZone = new ArrayList<>();

  @Override
  public void init() {
    System.out.println("Snakes are created");
    this.addEventListener(SFSEventType.USER_JOIN_ZONE, this);
  }

  @Override
  public void destroy() {
    System.out.println("Snakes are destroyed");
  }

  @Override
  public void handleClientRequest(String s, User user, ISFSObject isfsObject) throws SFSException {
    System.out.println("Snakes ????");
  }

  @Override
  public void handleServerEvent(ISFSEvent event) throws Exception {
    super.handleServerEvent(event);
    System.out.println("<<<>>>>>>" + event.getType());
    if (event.getType() == SFSEventType.USER_JOIN_ZONE) {
      User user = (User) event.getParameter(SFSEventParam.USER);
      String name = user.getName();
      userInZone.add(name);

      String message = "Hello " + name;
      message += ", meet ";
      for (String userName : userInZone) {
        message += userName;
      }

      this.getApi().sendExtensionResponse(message, null, user, null, false);

      System.out.println("User " + name + " has come to the party");

    }
  }
}
