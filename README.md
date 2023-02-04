## WHat is this?
This repo contains everything possibly related to devops (Dockerfile, Jenkinsfile, docker-compose, terraform.tf)

## What are there?

- Example docker-compose (**mysql**, **mongo**, **redis**) databases  and **rabbitmq** using [swarm secrets](https://docs.docker.com/engine/swarm/secrets/)  + **healthcheck**'s [[Click]](/docker/database-compose/)

- Minimal CI/CD setup including **jenkins**, **gitlab**, **nexus** and **nginx** as load balancer using docker compose [[Click]](/docker/minimal-cicd-setup-compose/)

- A jenkins shared library that fetches docker image names and tags from a given registry by using docker registry API and returns the result as jenkins build parameters [[Click]](/jenkins-shared-library/)

