stage('Self Healing') {
    when {
        expression { currentBuild.currentResult == 'FAILURE' }
    }
    steps {
        script {
            def action = sh(
                script: "java SelfHealingEngine prediction.txt",
                returnStdout: true
            ).trim()

            if (action.contains("RETRY")) {
                echo "Retrying build automatically"
                build job: env.JOB_NAME, wait: false
            }
            else if (action.contains("RESTART_SERVICE")) {
                sh "docker restart backend-service"
                build job: env.JOB_NAME, wait: false
            }
            else {
                error "Manual intervention required"
            }
        }
    }
}
