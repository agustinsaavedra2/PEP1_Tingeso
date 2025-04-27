pipeline{
    agent any
    tools{
        maven "maven"

    }
    stages{
        stage("Build JAR File"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/agustinsaavedra2/PEP1_Tingeso']])
                dir("pep1_tingeso_backend"){
                    bat "mvn clean install"
                }
            }
        }
        stage("Test"){
            steps{
                dir("pep1_tingeso_backend"){
                    bat "mvn test"
                }
            }
        }        
        stage("Build and Push Docker Image"){
            steps{
                dir("pep1_tingeso_backend"){
                    script{
                         withDockerRegistry(credentialsId: 'docker-credentials'){
                            bat "docker build -t agustinsaavedra056/pep1_tingeso_backend:latest ."
                            bat "docker push agustinsaavedra056/pep1_tingeso_backend:latest"
                        }
                    }                    
                }
            }
        }
    }
}