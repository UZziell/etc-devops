<!-- # database-compose -->

## About

Example docker-compose (**mysql**, **mongo**, **redis**) databases  and **rabbitmq** using [swarm secrets](https://docs.docker.com/engine/swarm/secrets/)  + **healthcheck**'s

## Use

**NOTE**: At the moment, secrets are only available in swarm mode.

1. change dir to database-compose
    ```sh
    cd devops-journey/docker/database-compose/
    ```
2. the default password for all services is `passwd`, fill the password files with randomly generated secrets like below or fill them with your own passwords
    ```sh
    for f in $(ls -1 ./*passwd.txt);
    do openssl rand -base64 20 > $f; done
    ```
2. deploy the stack
    ```sh
    docker stack deploy --compose-file docker-compose.yaml database-stack
    ```