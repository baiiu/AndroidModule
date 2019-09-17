open class GreetingPluginExtension {
    var message = "Hello from GreetingPlugin"
}

class GreetingPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Add the 'greeting' extension object
        val extension = project.extensions.create<GreetingPluginExtension>("greeting2")
        // Add a task that uses configuration from the extension object
        project.task("hello2") {
            doLast {
                println(extension.message)
            }
        }
    }
}

apply<GreetingPlugin>()

// Configure the extension
the<GreetingPluginExtension>().message = "Hi from Gradle"