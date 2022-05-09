import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
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
  private Consumer<Integer> onDiceRoll;
  private String myId;

  public SFSClient(String myId) {

    this.myId = myId;
  }

  public void setOnDiceRoll(Consumer<Integer> onDiceRoll) {
    this.onDiceRoll = onDiceRoll;
  }

  public void connect() {

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

  public void sendDiceRoll(int diceRoll) {
    SFSObject sfsObject = new SFSObject();
    sfsObject.putInt("diceRoll", diceRoll);
    this.sfs.send(new ExtensionRequest("mydata", sfsObject));
  }

  public static void main(String[] args) {
    SFSClient sfsClient = new SFSClient("sfd");
    sfsClient.connect();
    System.out.println("Hello world");
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
      Integer diceRoll = params.getInt("diceRoll");
      if (onDiceRoll != null) {
        onDiceRoll.accept(diceRoll);
      }
    }
  }
}
