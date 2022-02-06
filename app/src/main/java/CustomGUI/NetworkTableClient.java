package CustomGUI;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NetworkTableClient {
    private NetworkTableInstance inst;
    private NetworkTable table;
    NetworkTableClient(){
        inst = NetworkTableInstance.getDefault();
        table = inst.getTable("SmartDashboard");
    }

    public void startClient(int teamNumber){
        inst.startClientTeam(teamNumber);
    }

    public void startDSClient(){
        inst.startDSClient();
    }

    public NetworkTableEntry getEntry(String entry){
        return table.getEntry(entry);
    }

    public NetworkTable getTable(){
        return table;
    }
    
}
