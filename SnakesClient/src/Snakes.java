import com.smartfoxserver.v2.exceptions.SFSException;
import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.requests.LoginRequest;
import sfs2x.client.util.ConfigData;

public class Snakes implements IEventListener {

  private ConfigData cfg;
  private SmartFox sfs;

  public static void main(String[] args) {

    Snakes snakes = new Snakes();
    // Configure client connection settings
    snakes.cfg = new ConfigData();
    snakes.cfg.setHost("localhost");
    snakes.cfg.setPort(9933);
    snakes.cfg.setZone("BasicExamples");
    snakes.cfg.setDebug(false);


    // Set up event handlers
    snakes.sfs = new SmartFox();
    snakes.sfs.addEventListener(SFSEvent.CONNECTION, snakes);
    snakes.sfs.addEventListener(SFSEvent.CONNECTION_LOST, snakes);
    snakes.sfs.addEventListener(SFSEvent.LOGIN, snakes);
    snakes.sfs.addEventListener(SFSEvent.LOGIN_ERROR, snakes);
    snakes.sfs.addEventListener(SFSEvent.ROOM_JOIN, snakes);

    // Connect to server
    snakes.sfs.connect(snakes.cfg);

  }

  @Override
  public void dispatch(BaseEvent baseEvent) throws SFSException {
    if (baseEvent.getType().equals(SFSEvent.CONNECTION)) {
      System.out.println("Connected");
      this.sfs.send(new LoginRequest("dugrug", "", "BasicExamples"));
    }

    if (baseEvent.getType().equals(SFSEvent.LOGIN)) {
      System.out.println("Logged in");
    }
  }
}
