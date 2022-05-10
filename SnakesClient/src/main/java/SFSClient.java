import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import data.World;
import java.util.function.Consumer;
import org.apache.commons.lang.math.RandomUtils;
import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.requests.ExtensionRequest;
import sfs2x.client.requests.LoginRequest;
import sfs2x.client.util.ConfigData;

public class SFSClient implements IEventListener {

  private ConfigData cfg;
  private SmartFox sfs;
  private Consumer<World> onWorldUpdate;

  private String myId;

  public void setOnWorldUpdate(Consumer<World> onWorldUpdate) {
    this.onWorldUpdate = onWorldUpdate;
  }

  public void connect(String name) {
    this.myId = name;
    // Configure client connection settings
    this.cfg = new ConfigData();
    this.cfg.setHost("localhost");
    this.cfg.setPort(9933);
    this.cfg.setZone("BasicExamples");
    this.cfg.setDebug(false);


    // Set up event handlers
    this.sfs = new SmartFox();
    this.sfs.addEventListener(SFSEvent.CONNECTION, this);
    this.sfs.addEventListener(SFSEvent.CONNECTION_LOST, this);
    this.sfs.addEventListener(SFSEvent.LOGIN, this);
    this.sfs.addEventListener(SFSEvent.LOGIN_ERROR, this);
    this.sfs.addEventListener(SFSEvent.ROOM_JOIN, this);
    this.sfs.addEventListener(SFSEvent.EXTENSION_RESPONSE, this);

    // Connect to server
    this.sfs.connect(this.cfg);

  }

  public void sendDiceRoll() {
    SFSObject sfsObject = new SFSObject();
    this.sfs.send(new ExtensionRequest("droll", sfsObject));
  }


  @Override
  public void dispatch(BaseEvent baseEvent) throws SFSException {
    if (baseEvent.getType().equals(SFSEvent.CONNECTION)) {
      System.out.println("Connected");
      this.sfs.send(new LoginRequest(this.myId, "", "BasicExamples"));
    }

    if (baseEvent.getType().equals(SFSEvent.EXTENSION_RESPONSE)) {
      System.out.println("Got " + baseEvent.getArguments());

      ISFSObject params = (ISFSObject) baseEvent.getArguments().get("params");
      if (onWorldUpdate != null) {
        onWorldUpdate.accept(new World().withSFS(params));
      }

    }
  }
}
