- docker-compose up -d

- docker ps

- docker exec -it [container name] bash

- mysql -u root -p

# MASTER DB
- docker inspect <컨테이너_이름_또는_ID> | grep "IPAddress"
- SHOW MASTER STATUS; # file 복사
- GRANT REPLICATION SLAVE ON *.* TO '유저'@'%';
- FLUSH PRIVILEGES;

# SLAVE DB
- STOP REPLICA IO_THREAD;
- CHANGE MASTER TO MASTER_HOST='host 주소', MASTER_USER='root 이름', MASTER_PASSWORD='root 비번', MASTER_LOG_FILE='mysql-bin.xxxx', MASTER_LOG_POS=0, GET_MASTER_PUBLIC_KEY=1;
- START REPLICA IO_THREAD;
- start slave;
- show slave status\G;


# 복붙
mysql-bin : mysql-bin.000015
master-ip : 172.27.0.3
command : CHANGE MASTER TO MASTER_HOST='172.27.0.3', MASTER_USER='mung', MASTER_PASSWORD='hhs2684021@', MASTER_LOG_FILE='mysql-bin.000015', MASTER_LOG_POS=0, GET_MASTER_PUBLIC_KEY=1;