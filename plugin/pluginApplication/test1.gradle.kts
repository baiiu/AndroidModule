class GreetingPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.task("hello1") {
            doLast {
                println("Hello from the GreetingPlugin")
            }
        }
    }
}

// Apply the plugin
apply<GreetingPlugin>()