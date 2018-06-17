import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.TableName;

import org.apache.hadoop.conf.Configuration;

public class HbaseCreateTableTest {

    public static void main(String[] args)  throws IOException{
        Configuration config = HBaseConfiguration.create();
        config.addResource("/usr/local/hbase/conf/hbase-site.xml");
        Connection connection = ConnectionFactory.createConnection(config);

        Admin admin = connection.getAdmin();

        ArrayList<ColumnFamilyDescriptor> columnFamilies = new ArrayList<>();
        columnFamilies.add(ColumnFamilyDescriptorBuilder.of("personal"));
        columnFamilies.add(ColumnFamilyDescriptorBuilder.of("professional"));

        TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(TableName.valueOf("emp")).setColumnFamilies(columnFamilies).build();

        admin.createTable(tableDescriptor);
        System.out.println(" Table created ");
    }
}


