import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseTest {

    public static void main(String[] args) throws Exception {
        Configuration config = HBaseConfiguration.create();
        config.addResource("/usr/local/hbase/conf/hbase-site.xml");
        Connection connection = ConnectionFactory.createConnection(config);

        byte[] row1 = Bytes.toBytes("row2");
        byte[] persFamily = Bytes.toBytes("personal");
        byte[] profFamily = Bytes.toBytes("professional");
        byte[] city = Bytes.toBytes("city");
        byte[] name = Bytes.toBytes("name");
        byte[] designation = Bytes.toBytes("designation");
        byte[] salary = Bytes.toBytes("salary");

        Table hTable = connection.getTable(TableName.valueOf("emp"));


        Put p = new Put(Bytes.toBytes("row2"));

        CellBuilder cellBuilder = CellBuilderFactory.create(CellBuilderType.DEEP_COPY);
        Cell cell1 = cellBuilder.setFamily(Bytes.toBytes("personal")).setQualifier(Bytes.toBytes("name")).setValue(Bytes.toBytes("raju")).setRow(Bytes.toBytes("row2")).setType(Cell.Type.Put).build();
        Cell cell2 = cellBuilder.setFamily(Bytes.toBytes("personal")).setQualifier(Bytes.toBytes("city")).setValue(Bytes.toBytes("hyderabad")).setRow(Bytes.toBytes("row2")).setType(Cell.Type.Put).build();
        Cell cell3 = cellBuilder.setFamily(Bytes.toBytes("professional")).setQualifier(Bytes.toBytes("designation")).setValue(Bytes.toBytes("manager")).setRow(Bytes.toBytes("row2")).setType(Cell.Type.Put).build();
        Cell cell4 = cellBuilder.setFamily(Bytes.toBytes("professional")).setQualifier(Bytes.toBytes("salary")).setValue(Bytes.toBytes("50000")).setRow(Bytes.toBytes("row2")).setType(Cell.Type.Put).build();

        p.add(cell1);

        p.add(cell2);

        p.add(cell3);

        p.add(cell4);

        long start = System.currentTimeMillis();

        hTable.put(p);

        System.out.println("Time to insert: " + (System.currentTimeMillis() - start));

        System.out.println("Data inserted");
        System.out.println();
        System.out.println("Before update");


        SingleColumnValueFilter filterA = new SingleColumnValueFilter(persFamily, city, CompareFilter.CompareOp.EQUAL, Bytes.toBytes("hyderabad"));

        Scan scan = new Scan();
        scan.setFilter(filterA);

        start = System.currentTimeMillis();
        ResultScanner results = hTable.getScanner(scan);

        for (Result result : results ) {
            System.out.print("City: "         + new String(result.getValue(persFamily, city)));
            System.out.print(" Name: "         + new String(result.getValue(persFamily, name)));
            System.out.print(" Designation: "  + new String(result.getValue(profFamily, designation)));
            System.out.println(" Salary: "       + new String(result.getValue(profFamily, salary)));
        }
        System.out.println("Time to scan: " + (System.currentTimeMillis() - start));


        Cell cell11 = cellBuilder.setFamily(Bytes.toBytes("personal")).setQualifier(Bytes.toBytes("name")).setValue(Bytes.toBytes("raju")).setRow(Bytes.toBytes("row2")).setType(Cell.Type.Put).build();
        Cell cell22 = cellBuilder.setFamily(Bytes.toBytes("personal")).setQualifier(Bytes.toBytes("city")).setValue(Bytes.toBytes("hyderabad")).setRow(Bytes.toBytes("row2")).setType(Cell.Type.Put).build();
        Cell cell33 = cellBuilder.setFamily(Bytes.toBytes("professional")).setQualifier(Bytes.toBytes("designation")).setValue(Bytes.toBytes("boss")).setRow(Bytes.toBytes("row2")).setType(Cell.Type.Put).build();
        Cell cell44 = cellBuilder.setFamily(Bytes.toBytes("professional")).setQualifier(Bytes.toBytes("salary")).setValue(Bytes.toBytes("70000")).setRow(Bytes.toBytes("row2")).setType(Cell.Type.Put).build();

        Put p1 = new Put(Bytes.toBytes("row2"));

        p1.add(cell11);

        p1.add(cell22);

        p1.add(cell33);

        p1.add(cell44);

        start = System.currentTimeMillis();
        hTable.put(p1);
        System.out.println("Time to insert: " + (System.currentTimeMillis() - start));
        System.out.println("Data updated");


        start = System.currentTimeMillis();
        ResultScanner results1 = hTable.getScanner(scan);

        for (Result result : results1) {
            System.out.print("City: "         + new String(result.getValue(persFamily, city)));
            System.out.print(" Name: "         + new String(result.getValue(persFamily, name)));
            System.out.print(" Designation: "  + new String(result.getValue(profFamily, designation)));
            System.out.println(" Salary: "       + new String(result.getValue(profFamily, salary)));
        }
        System.out.println("Time to scan: " + (System.currentTimeMillis() - start));

        hTable.close();

    }
}
