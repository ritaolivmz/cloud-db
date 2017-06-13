package aws;

import aws.cloudwatch.CloudWatchManager;
import aws.db.ConnectionManager;
import aws.db.DBConnectionProperties;

public class Main {

    public static void main(String [ ] args)
    {
        ConnectionManager connectionManager = new ConnectionManager();

        //connect to AuroraDB
        connectionManager.getRemoteConnection(DBConnectionProperties.dbNameAurora, DBConnectionProperties.userNameAurora,
                DBConnectionProperties.passwordAurora, DBConnectionProperties.hostnameAurora, DBConnectionProperties.portAurora);
        //insert new person record into AuroraDB
        connectionManager.insertNewPerson();
        connectionManager.closeConnection();

        //connect to MariaDB
        connectionManager.getRemoteConnection(DBConnectionProperties.dbNameMaria, DBConnectionProperties.userNameMaria,
                DBConnectionProperties.passwordMaria, DBConnectionProperties.hostnameMaria, DBConnectionProperties.portMaria);
        //insert new person record into MariaDB
        connectionManager.insertNewPerson();
        connectionManager.closeConnection();

        //cloudwatch
        CloudWatchManager cloudWatchManager = new CloudWatchManager();
        cloudWatchManager.changeChronExpression();
    }
}
