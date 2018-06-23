### How to install Hive




1. ```bash wget https://archive.apache.org/dist/hive/hive-2.3.3/apache-hive-2.3.3-bin.tar.gz```
2. ```bash tar -xzf apache-hive-2.3.3-bin.tar.gz```
3. ```bash sudo mv  apache-hive-2.3.3-bin /usr/local/hive```
4. ```bash sudo vim .bash_profile```
5.  Add following variables:
    ```bash
        export PATH="/usr/local/hadoop/sbin:/usr/local/hadoop/bin:$PATH"
        export PATH="/usr/loca/spark/bin:/usr/local/spark/sbin:$PATH"
        export HADOOP_CONF_DIR=/usr/local/hadoop/etc/hadoop
        export HIVE_HOME=/usr/local/hive
        export PATH=$PATH:/usr/local/hive/bin
        export HADOOP_HOME=/usr/local/hadoop
        export HADOOP_CONF_DIR=/usr/local/hadoop/etc/hadoop
        export HADOOP_MAPRED_HOME=/usr/local/hadoop
        export HADOOP_COMMON_HOME=/usr/local/hadoop
        export HADOOP_HDFS_HOME=/usr/local/hadoop
        export YARN_HOME=/usr/local/hadoop
        export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
        export HADOOP_OPTS="-Djava.library.path=$HADOOP_HOME/lib"
        export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_171.jdk/Contents/Home
        export PATH=$PATH:$HADOOP_HOME/bin
        export HIVE_CONF_DIR=/usr/local/hive/conf
    ```
6. ```bash cd /usr/local/hive```
7. ```bash sudo vim conf/hive-site.xml```
8. Add following text to hive-site.xml
    ```xml
    <configuration>
        <property>
          <name>javax.jdo.option.ConnectionURL</name>
          <value>jdbc:mysql://localhost:3306/metastore?useSSL=false</value>
        </property>
        
        <property>
          <name>javax.jdo.option.ConnectionDriverName</name>
          <value>com.mysql.jdbc.Driver</value>
        </property>
        
        <property>
          <name>hive.server2.enable.doAs</name>
          <value>false</value>
        </property>
        
        <property>
          <name>javax.jdo.option.ConnectionUserName</name>
          <value>hiveuser</value>
        </property>
        
        <property>
          <name>hive.metastore.warehouse.dir</name>
          <value>/user/hive/warehouse</value>
          <description>location of default database for the warehouse</description>
        </property>
        
        <property>
          <name>javax.jdo.option.ConnectionPassword</name>
          <value>hiveuser</value>
        </property>
        
        <property>
          <name>datanucleus.autoCreateSchema</name>
          <value>false</value>
        </property>
        
        <property>
          <name>datanucleus.fixedDatastore</name>
          <value>true</value>
        </property>
        
        <property>
          <name>datanucleus.autoStartMechanism</name> 
          <value>SchemaTable</value>
        </property> 
        
        <property>
          <name>hive.metastore.uris</name>
          <value>thrift://localhost:9083</value>
          <description>IP address (or fully-qualified domain name) and port of the metastore host</description>
        </property>
        
        <property>
          <name>hive.metastore.schema.verification</name>
          <value>true</value>
        </property>
    </configuration>
    
    ```
9.  ```bash start-dfs.sh```
10. ```bash start-yarn.sh```
11. ```bash hdfs dfs -mkdir /tmp```
12. ```bash hdfs dfs -mkdir -p /user/hive/warehouse```
13. Add user **hiveuser** **hiveuser** to MySQL instance.
14. Add schema **metastore** to MySQL instance.
15. Grant all privileges to **hiveuser**.
16. ```bash bin/schematool -initSchema -dbType mysql```
17. ```bash hadoop fs -chmod -R 777 /user/hive/```
18. Add to **core-site.xml** following lines
    ```xml
        <property>
          <name>hadoop.proxyuser.root.groups</name>
          <value>*</value>
        </property>
        <property>
          <name>hadoop.proxyuser.root.hosts</name>
          <value>*</value>
        </property>
    ```
    
19. Start metastore ```bash hive --service metastore```
20. Start hiveserver2 ```bash hive --service metastore```