pipeline {
    agent any
    tools{
        maven 'maven_3_8_1'
    }
    stages{
        stage('Build maven'){
            steps{
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/agustinsaavedra2/PEP1_Tingeso']])
                bat 'mvn clean package'
            }
        }

        stage('Unit Tests') {
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
                   withCredentials([string(credentialsId: 'laucesbkn2001id', variable: 'laucesbkn2001')]) {
                        bat 'docker login -u agustinsaavedra -p %laucesbkn2001%'
                   }
                   bat 'docker push agustinsaavedra056/pep1_tingeso_backend:latest'
                }
            }
        }
    }
}