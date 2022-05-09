package com.supg.snakes;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseSFSExtension;
import java.util.ArrayList;
import java.util.Collection;
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
  public void handleClientRequest(String cmd, User user, ISFSObject isfsObject) throws SFSException {
    System.out.println("Snakes ????");
    isfsObject.putInt("_suid", user.getId());
    relayDataToAllUsers(cmd, isfsObject);
  }

  @Override
  public void handleServerEvent(ISFSEvent event) throws Exception {
    super.handleServerEvent(event);
    System.out.println("<<<>>>>>>" + event.getType());
    if (event.getType() == SFSEventType.USER_JOIN_ZONE) {

    }
  }

  public void relayDataToAllUsers(String cmd, ISFSObject data) {
    List<User> userList = new ArrayList<>();
    userList.addAll(this.getParentZone().getUserList());
    this.getApi().sendExtensionResponse(cmd, data, userList, null, false);
  }
}
