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
                    junit checksName: 'Testss', '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
    }
}