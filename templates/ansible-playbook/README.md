# playbook.yml

This is an exmaple playbook on how to perform different tasks in ansbile playbooks.

Here's the list of all plays in this playbook:
* buit-in debug module
* processes (shell, command and `async`)
* file operations (create, remove, chmod)
* user operations
* `community.docker` module
* `kubernetes.core.k8s` module
* Others:
    * `wait_for`: Wait for some condition then continue. For example, wait for a port to become available
    * conditions
    * `register`: registering output of a task for later use
    * `set_fact`
    * `lookup`: running inline commands
    * `community.general.random_string` generating strings