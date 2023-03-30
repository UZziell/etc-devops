# etc-devops

## What is this?

This repo contains anything possibly related to devops tooling (Dockerfile, Jenkinsfile, docker-compose, playbooks, terraform.tf)

## What are there?

- Example docker-compose (**mysql**, **mongo**, **redis**) databases and **rabbitmq** using [swarm secrets](https://docs.docker.com/engine/swarm/secrets/) + **healthcheck**'s [[database-compose]](/docker/database-compose/)

- Minimal docker-compose based CI/CD setup including **jenkins**, **gitlab**, **nexus** and **nginx** as load balancer [[minimal.cicd.setup-compose]](/docker/minimal.cicd.setup-compose)

- A jenkins shared library that fetches docker image names and tags from a given registry by using docker registry API and returns the result as jenkins build parameters [[jenkins-shared-library]](/jenkins-shared-library/)

- Template files:
  - **jenkins pilpeline** | [`Jenkinsfile`](/templates/Jenkinsfile)
  - **GNU's Makefile** | [`Makefile`](/templates/Makefile)
  - **ansible playbook** | [`playbook.yml`](/templates/ansible-playbook)