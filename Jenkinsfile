pipeline {
    agent {
        label 'Worker&&Containers'
    }
    tools {
        jdk 'OpenJDK 17 Latest'
        maven 'Apache Maven 3.9'
    }
    options {
        disableConcurrentBuilds()
    }
    environment {
        TESTCONTAINERS_RYUK_DISABLED = 'true'
    }
    stages {
        stage('Build Hibernate Search Demos') {
            steps {
                sh "mvn -f hibernate-search -B -q clean package -DskipTests=true -Dtest.containers.run.skip=true"
            }
        }
        stage('Test Hibernate Search Demos') {
            steps {
                sh "mvn -f hibernate-search -B verify -Dstart-containers"
            }
        }
    }
}
