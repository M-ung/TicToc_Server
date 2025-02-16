1. docker-compose up -d

2. docker ps

3. docker exec -it [container name] bash

4. mysql -u root -p

# MASTER DB
5. docker inspect <컨테이너_이름_또는_ID> | grep "IPAddress"
6. SHOW MASTER STATUS; # file 복사

# SLAVE DB
7. CHANGE MASTER TO MASTER_HOST='host 주소', MASTER_USER='root 이름', MASTER_PASSWORD='root 비번', MASTER_LOG_FILE='mysql-bin.xxxx', MASTER_LOG_POS=0, GET_MASTER_PUBLIC_KEY=1;
8. start slave;
9. show slave status\G;