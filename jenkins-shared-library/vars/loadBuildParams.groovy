#!/usr/bin/env groovy

// Import the JsonSlurper class to parse Dockerhub API response


def call(String serviceName, String dockerRegistryURL, String dockerCredentialsID) {
    properties([
        parameters([
            [$class: 'ChoiceParameter', 
                choiceType: 'PT_SINGLE_SELECT', 
                description: 'Choose the image to be deployed', 
                filterLength: 1, 
                filterable: false, 
                name: 'IMAGE_NAME', 
                randomName: 'choice-parameter-2631314439613978', 
                script: [
                    $class: 'GroovyScript', 
                    fallbackScript: [
                        classpath: [], 
                        sandbox: true, 
                        script: 
                            'return ["Could not load image"]'
                    ], 
                    script: [
                        classpath: [], 
                        sandbox: true, 
                        script: 
                            """
                            import groovy.json.JsonSlurper
                            import jenkins.*
                            import jenkins.model.* 
                            import hudson.*
                            import hudson.model.*
                            // Set the URL we want to read from and service base name
                            docker_registry_url = "${dockerRegistryURL}/v2/"
                            try {
                                // Read registry credentials from jenkins
                                def creds = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
                                    com.cloudbees.plugins.credentials.Credentials.class,
                                    Jenkins.instance, null, null );
                                String userCredentials = ''
                                for (c in creds) {
                                    if(c.id == "${dockerCredentialsID}"){
                                        if (c.password){
                                            userCredentials = new String(c.username.toString()) + ":" + new String(c.password.toString())
                                        }
                                    }
                                }
                                // Set requirements for the HTTP GET request, you can add Content-Type headers and so on...
                                def http_client = new URL(docker_registry_url+"_catalog").openConnection() as HttpURLConnection
                                // Authentication
                                String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
                                http_client.setRequestProperty ("Authorization", basicAuth);
                                http_client.setRequestProperty("User-Agent", "docker/20.10.8 go/go1.16.6 git-commit/75249d8 kernel/4.19.0-8-amd64 os/linux arch/amd64")
                                http_client.setRequestMethod('GET')
                                // Run the HTTP request
                                http_client.connect()
                                // Prepare a variable where we save parsed JSON as a HashMap
                                def dockerhub_response = [:]    
                                // Check if we got HTTP 200, otherwise exit
                                if (http_client.responseCode == 200) {
                                    dockerhub_response = new JsonSlurper().parseText(http_client.inputStream.getText('UTF-8'))
                                } else {
                                    println("HTTP response error")
                                    println(http_client.responseCode)
                                    System.exit(0)
                                }
                                // Prepare a List to collect the image names into
                                def image_list = []
                                // Iterate the HashMap of all images and grab only their "names" into our List
                                dockerhub_response.repositories.each { image ->
                                    if (image.matches(".*/${serviceName}")){
                                        image_list.add(image)
                                    }
                                }
                                // The returned value MUST be a Groovy type of List or a related type (inherited from List)
                                // It is necessary for the Active Choice plugin to display results in a combo-box
                                // println(image_list.reverse())
                                return image_list.sort().reverse()
                            } catch (Exception e) {
                                    // handle exceptions like timeout, connection errors, etc.
                                    println(e)
                            }
                            """
                    ]
                ]
            ],
            [$class: 'CascadeChoiceParameter', 
                choiceType: 'PT_SINGLE_SELECT', 
                description: 'Choose the image Tag/Version', 
                filterLength: 1, 
                filterable: false, 
                name: 'IMAGE_TAG', 
                randomName: 'choice-parameter-3631314456178619', 
                referencedParameters: 'IMAGE_NAME', 
                script: [
                    $class: 'GroovyScript', 
                    fallbackScript: [
                        classpath: [], 
                        sandbox: true, 
                        script: 
                            'return ["Could not fetch image tags from nexus docker registry"]'
                    ], 
                    script: [
                        classpath: [], 
                        sandbox: true, 
                        script: 
                            """
                            import groovy.json.JsonSlurper
                            import jenkins.*
                            import jenkins.model.* 
                            import hudson.*
                            import hudson.model.*
                            // Set the URL we want to read from and service base name
                            docker_registry_url = "${dockerRegistryURL}/v2/"
                            String userCredentials = ''
                            try {
                                // Read registry credentials from jenkins
                                def creds = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
                                    com.cloudbees.plugins.credentials.Credentials.class,
                                    Jenkins.instance, null, null );
                                for (c in creds) {
                                    if(c.id == "${dockerCredentialsID}"){
                                        if (c.password){
                                            userCredentials = new String(c.username.toString()) + ":" + new String(c.password.toString())
                                        }
                                    }
                                }
                                // Set requirements for the HTTP GET request, you can add Content-Type headers and so on...
                                def http_client = new URL(docker_registry_url+"/\$IMAGE_NAME/tags/list").openConnection() as HttpURLConnection
                                // Authentication
                                String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
                                http_client.setRequestProperty ("Authorization", basicAuth);
                                
                                http_client.setRequestProperty("User-Agent", "docker/20.10.8 go/go1.16.6 git-commit/75249d8 kernel/4.19.0-8-amd64 os/linux arch/amd64")
                                http_client.setRequestMethod('GET')
                                http_client.connect()

                                def dockerhub_response = [:]    
                                // Check if we got HTTP 200, otherwise exit
                                if (http_client.responseCode == 200) {
                                    dockerhub_response = new JsonSlurper().parseText(http_client.inputStream.getText('UTF-8'))
                                } else {
                                    println("HTTP response error")
                                    println(http_client.responseCode)
                                    System.exit(0)
                                }
                                def image_tag_list = []
                                dockerhub_response.tags.each { tag ->
                                        image_tag_list.add(tag)
                                }

                                // println(image_tag_list.reverse())  
                                return image_tag_list.sort().reverse()
                            } catch (Exception e) {
                                    // handle exceptions like timeout, connection errors, etc.
                                    println(e)
                            }
                            """
                    ]
                ]
            ]
        ])
    ])
}