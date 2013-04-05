Hadoop cluster install guide
============

1. Prepare environment on all nodes
    * Install Java and specify ```JAVA_HOME```
    * Install Hadoop and specify ```HADOOP_HOME```
2. Create user for hadoop and use it on all nodes
    * run next commands (as root):
        * ```/usr/sbin/adduser hadoop```  
        * ```/usr/bin/passwd hadoop``` 
        * ```chown -R hadoop:users $HADOOP_HOME```
        * ```su hadoop```
3. Generate ssh key for namenode and jobtracker
    * run next commands on namenode and jobtracker node (as hadoop user): 
        * ```ssh-keygen -t rsa```
        * ```cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys```
        * ```chmod 644 ~/.ssh/authorized_keys```
4. Copy ssh pub keys to all datanodes (skip this step for single node setup):
    * run next command from namenode for each datanode (as hadoop user):
        * ```scp ~/.ssh/id_rsa.pub hadoop@<datanodeIP>:/home/hadoop```
        * ```ssh hadoop@<datanodeIP>```
        * ```cat ~/id_rsa.pub >> ~/.ssh/authorized_keys```
        * ```chmod 755 ~/.ssh```
        * ```chmod 644 ~/.ssh/authorized_keys```
    * run the same command from jobtracker to add jobtracker node public key to datanodes autorized keys
5. Copy xmls from app conf folder into $HADOOP_HOME/conf folder (replace all exist files)
6. Configure namenode
    * add the hostname or IP address of all datanodes into $HADOOP_HOME/conf/slaves file. One adress per line.
    * run next commands (as hadoop user):
        * ```mkdir /home/hadoop/hdfs```
        * ```mkdir /home/hadoop/hdfs/namesecondary```
    * run next commands (as root user):
        * ```mkdir /var/log/hadoop```
        * ```chown -R hadoop:users /var/log/hadoop```
    * open conf/hadoop-env.sh file and specify ```HADOOP_MASTER``` (this line already exists but commented):
        * ```export HADOOP_MASTER=<namenode_ip>:${HADOOP_HOME}```
    * run command (as hadoop user):
        *```$HADOOP_HOME/bin/hadoop namenode -format```
7. Configure datanode
    * run next commands (as root):
        * ```mkdir /hadoop/hdfs/data```
        * ```chown -R hadoop:users /hadoop```
    * run next commands (as hadoop user):
        * ```chmod 755 /hadoop/hdfs/data```
        * ```mkdir /home/hadoop/hadoop_local```
        * ```mkdir /home/hadoop/hadoop_system```
8. Increase limits of used resources in OS (for all nodes)
    * Increase limit of opened files per user (do it for all nodes):
        * add next line at the end of /etc/sysctl.conf
            * ```fs.epoll.max_user_instances = 2048```
        * add  next line at the end of /etc/security/limits.conf
            * ```* soft nofile 2048```
    * Increase limit of new processes per user
        * add next lines at the end of /etc/security/limits.conf
            * ``` hadoop hard nproc 32000```
            * ``` hadoop soft nproc 32000```
9. To startup Hadoop
    * run from namenode (run from hadoop user. It will start namenode and all datanodes):
        * ```$HADOOP_HOME/bin/start-dfs.sh``` 
    * run from jobtracker (run from hadoop user. It will start jobtracker and tasktrackers):
        * ```$HADOOP_HOME/bin/start-mapred.sh```  
    * run from namenode for single node setup (run from hadoop user)
        * ```$HADOOP_HOME/bin/start-all.sh```
