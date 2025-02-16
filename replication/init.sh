- docker-compose up -d

- docker ps

- docker exec -it [container name] bash

- mysql -u root -p

# MASTER DB
- docker inspect <컨테이너_이름_또는_ID> | grep "IPAddress"
- SHOW MASTER STATUS; # file 복사
- GRANT REPLICATION SLAVE ON *.* TO '유저'@'%' IDENTIFIED BY '비번';
- FLUSH PRIVILEGES;

# SLAVE DB
- CHANGE MASTER TO MASTER_HOST='host 주소', MASTER_USER='root 이름', MASTER_PASSWORD='root 비번', MASTER_LOG_FILE='mysql-bin.xxxx', MASTER_LOG_POS=0, GET_MASTER_PUBLIC_KEY=1;
- start slave;
- show slave status\G;