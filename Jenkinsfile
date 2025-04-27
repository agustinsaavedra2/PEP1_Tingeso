pipeline {
    agent any
    tools{
        maven 'maven'
    }
    stages{
        stage("Build JAR File"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/agustinsaavedra2/PEP1_Tingeso']])
                bat 'mvn clean package'
            }
        }

        stage('Tests') {
            steps {
                bat 'mvn test' 
            }
        }

        stage('Build docker image'){
            steps{
                script{
                    bat 'docker build -t agustinsaavedra056/pep1_tingeso_backend:latest .'
                }
            }
        }
        stage('Push image to Docker Hub'){
            steps{
                script{
                   withCredentials([string(credentialsId: 'dhpswid', variable: 'dhpsw')]) {
                        bat 'docker login -u agustinsaavedra056 -p %dhpsw%'
                   }
                   bat 'docker push agustinsaavedra056/pep1_tingeso_backend:latest'
                }
            }
        }
    }
}