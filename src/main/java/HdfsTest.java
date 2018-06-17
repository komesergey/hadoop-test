import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import java.io.File;

public class HdfsTest {
    public static void main(String... args) throws Exception{

        System.setProperty("hadoop.home.dir", "/usr/local/hadoop");

        File localFile = new File("big.txt");

        Configuration conf = new Configuration();
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        FileSystem fs = FileSystem.get(conf);
        fs.copyFromLocalFile(false, new Path(localFile.toURI()), new Path("/big.txt"));


    }
}
