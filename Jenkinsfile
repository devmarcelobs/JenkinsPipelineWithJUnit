pipeline{
    agent any

    tools{
        maven 'mvn 3.6.3'
    }

    stages{
        stage('Checkout'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '/*master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '', url: 'https://github.com/marcelim122/JenkinsPipelineWithJUnit.git']]])
            }
        }
        stage('Build') {
            steps {
                //sh "mvn -Dmaven.test.failure.ignore=true clean package"
                sh "mvn -Dmaven.test.failure.ignore=true clean verify"
            }

            post {
                success {
                    junit checksName: 'Testss', testResults: '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        stage('SonarQube Analysis and Quality Gate'){
            steps{
                withSonarQubeEnv(credentialsId: 'admintoken-sonarqube', installationName: 'sonarqubescanner') { 
                    sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
                }
                
                script{
                    def qualitygate = waitForQualityGate()
                    if (qualitygate.status != "OK") {
                        error "Pipeline aborted due to quality gate coverage failure: ${qualitygate.status}"
                    }
                }  
            }
        }
        /*stage('Deploy'){
            steps{
                sh 'sshpass -p root scp -v -o StrictHostKeyChecking=no /var/jenkins_home/workspace/lojaVirtualTest/target/*.jar root@<ip_destino>:/var'
                sh 'sshpass -p root ssh -t -t root@<ip_destino> \
                    /usr/local/openjdk-8/bin/java -jar /var/lojavirtual-0.0.1-SNAPSHOT.jar'
            }
        }*/
    }
}
