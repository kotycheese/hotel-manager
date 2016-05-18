package cz.muni.fi.pv168;

import cz.muni.fi.pv168.swing.Tabs;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.swing.JFrame;
import org.apache.derby.jdbc.EmbeddedDataSource;

/**
 *
 * @author Pavel Kotala, 437164
 */
public class Main {
    
    public static void main(String[] args) throws SQLException {
        org.apache.log4j.BasicConfigurator.configure(); // to log into standart output
        try {
            setUp();
        } catch(SQLException ex) {
            System.exit(-1);
        }
        
        JFrame tabs = new Tabs();
        tabs.setVisible(true);
    }
    
    private static DataSource prepareDataSource() {
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:hotelmngr");
        dataSource.setCreateDatabase("create");

        return dataSource;
    }
    
    private static void setUp() throws SQLException {
        DataSource dataSource = prepareDataSource();
        DBUtils.executeSqlScript(dataSource, GuestManagerImpl.class.getResource("createTables.sql"));
        GuestManagerImpl.setDataSource(dataSource);
        RoomManagerImpl.setDataSource(dataSource);
        RentManagerImpl.setDataSource(dataSource);
    }
}
