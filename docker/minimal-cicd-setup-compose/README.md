<!-- # database-compose -->

## About

Minimal CI/CD setup (**jenkins**, **gitlab**, **nexus**) + (**nginx** as load balancer) using docker compose

## Use

1. change dir to database-compose

   ```sh
   cd devops-journey/docker/minimal-cicd-setup-compose/
   ```

2. Set `SERVER_NAME` and `UPSTREAM_NAME` variables in `.env` file. They will be used in nginx template files that will eventually make nginx configs. Default values are:

   ```sh
   # Nginx
   NGINX_CI_SERVER_NAME=ci.local
   NGINX_CI_UPSTREAM_NAME=jenkins

   NGINX_GITLAB_SERVER_NAME=gitlab.local
   NGINX_GITLAB_UPSTREAM_NAME=gitlab

   NGINX_REPO_SERVER_NAME=repo.local
   NGINX_REPO_UPSTREAM_NAME=nexus

   # gitlab
   GITLAB_EXTERNAL_URL=http://gitlab.local
   ```

3. Put the right wildcard certificate of the given domain in the previous step in `./nginx/certs/` which is used by nginx.

4. Bring up the services

   ```sh
   docker compose up -d
   ```

5. [optianl] Check out the logs
   ```
   docker compose logs -f
   ```

**Notes**

- Nginx service is exposed on ports `80` and `443`. Also all HTTP traffic is forwarded to HTTPS.
- If a certificate is not provided the default self-signed wildcard certificate for `*.local` domain is used.
