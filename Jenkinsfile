pipeline{
    agent any
    tools{
        maven "maven"

    }
    stages{
        stage("Build JAR File"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/agustinsaavedra2/PEP1_Tingeso']])
                dir("PEP1_Tingeso_Backend"){
                    bat "mvn clean install"
                }
            }
        }
        stage("Test"){
            steps{
                dir("PEP1_Tingeso_Backend"){
                    bat "mvn test"
                }
            }
        }        
        stage("Build and Push Docker Image"){
            steps{
                dir("pep1_tingeso_backend"){
                    script {
                        withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'agustinsaavedra056', passwordVariable: 'laucesbkn2001')]) {
                            bat "docker login -u $DOCKER_USER -p $DOCKER_PASS"
                            bat "docker build -t agustinsaavedra056/pep1_tingeso_frontend:latest ."
                            bat "docker push agustinsaavedra056/pep1_tingeso_frontend:latest"
                        }
                    }              
                }
            }
        }
    }
}