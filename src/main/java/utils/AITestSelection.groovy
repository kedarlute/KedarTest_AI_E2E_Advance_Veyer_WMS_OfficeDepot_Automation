stage('AI Test Selection') {
    steps {
        script {
            def files = sh(
                script: "git diff --name-only HEAD~1",
                returnStdout: true
            ).trim().replace("\n", " ")

            def tests = sh(
                script: "java TestSelector \"${files}\"",
                returnStdout: true
            ).trim()

            env.TESTS = tests.replace("\n", ",")
            echo "Selected Tests: ${env.TESTS}"
        }
    }
}
