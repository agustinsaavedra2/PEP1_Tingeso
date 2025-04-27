pipeline {
    agent any
    tools {
        maven "maven" // Asegúrate de que esta herramienta esté configurada en Jenkins
    }
    stages {
        stage("Build JAR File") {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/agustinsaavedra2/PEP1_Tingeso']])
                dir("pep1_tingeso_backend") {
                    sh "mvn clean install"
                }
            }
        }
        stage("Test") {
            steps {
                dir("pep1_tingeso_backend") {
                    sh "mvn test"
                }
            }
        }
        stage("Verify Lombok Processing") {
            steps {
                dir("pep1_tingeso_backend") {
                    sh "mvn clean compile" // Esto asegura que Lombok sea procesado
                }
            }
        }
        stage("Build and Push Docker Image") {
            steps {
                dir("pep1_tingeso_backend") {
                    script {
                        withDockerRegistry(credentialsId: 'docker-credentials') {
                            sh "docker build -t agustinsaavedra056/pep1_tingeso_backend:latest ."
                            sh "docker push agustinsaavedra056/pep1_tingeso_backend:latest"
                        }
                    }
                }
            }
        }
    }
}
