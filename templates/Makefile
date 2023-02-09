# Just like the way 'Dockerfile' tells docker daemon how to make docker images, A Makefile tells GNU's make program how to make file(s)
# Makefile consists of a couple of rules that could depend on other rules
# The format of a rule is like is:

# target [target ...]: [component	 ...]
#[tab]m[recipe]

# variable
foo := bar
# variable with default value (if the variable 'version' did not exist in the Environment Variables use te value 'latest')
version ?= latest
SHELL:= /usr/bin/bash

hello: world
	touch hello
	echo "this command will be echoed"
	@echo "this command will not be echoed"
world:
	touch world

saved.variables: 
	echo ${foo} > saved.variables
	echo ${version} >> saved.variables

conditions:
ifeq ($(version),latest)
	@echo "version == latest"
else
	@echo "version == ${version}"
endif

clean:
	rm -f hello world
	rm -f saved.variables


# Using make file for common docker tasks

.DEFAULT_GOAL := help # Default goal is the rule that will be executed by default when running `make` command with no argument

.PHONY: help dockerall build run
help: ## display this help message
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m<target>\033[0m\n\nTargets:\n"} /^[a-zA-Z_-]+:.*?##/ { printf "  \033[36m%-10s\033[0m %s\n", $$1, $$2 }' $(MAKEFILE_LIST)

dockerall: build stop run ## execute all targets build stop run
build: ## build docker image
	@docker build -t image-name . --build-arg ENV_VAR=foo

stop: ## stop running container
	@docker stop container-name || true && docker rm container-name || true

run: ## run docker container
	@docker run -d -e ANOTHER_VAR=bar--name container-name image-name