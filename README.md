### How to install Hive




```shell
wget https://archive.apache.org/dist/hive/hive-2.3.3/apache-hive-2.3.3-bin.tar.gz
```
```shell
tar -xzf apache-hive-2.3.3-bin.tar.gz
```
```shell
sudo mv  apache-hive-2.3.3-bin /usr/local/hive
```
```shell 
sudo vim .bash_profile
```
Add following variables:
```shell
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
```shell
cd /usr/local/hive
```
```shell
sudo vim conf/hive-site.xml
```
Add following text to hive-site.xml
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
```shell
start-dfs.sh
```
```shell
start-yarn.sh
```
```shell
hdfs dfs -mkdir /tmp
```
```shell
hdfs dfs -mkdir -p /user/hive/warehouse
```
Add user **hiveuser** **hiveuser** to MySQL instance.
Add schema **metastore** to MySQL instance.
Grant all privileges to **hiveuser**.
```shell
bin/schematool -initSchema -dbType mysql
```
```shell
hadoop fs -chmod -R 777 /user/hive/
```
Add to **core-site.xml** following lines
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
    
Start metastore:
```shell
hive --service metastore
```
Start hiveserver2:
```shell
hive --service metastore
```