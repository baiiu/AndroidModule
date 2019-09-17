open class GreetingToFileTask : DefaultTask() {

    var destination: Any? = null

    fun getDestination(): File {
        return project.file(destination!!)
    }

    @TaskAction
    fun greet() {
        val file = getDestination()
        file.parentFile.mkdirs()
        file.writeText("Hello!")
    }
}

tasks.register<GreetingToFileTask>("greet") {
    destination = { project.extra["greetingFile"]!! }
}

tasks.register("sayGreeting") {
    dependsOn("greet")
    doLast {
        println(file(project.extra["greetingFile"]!!).readText())
    }
}

println("buildDir: $buildDir")

// 赋值greetingFile
extra["greetingFile"] = "$buildDir/hello.txt"


//class GreetingToFileTask extends DefaultTask {
//
//    def destination
//
//            File getDestination() {
//        project.file(destination)
//    }
//
//    @TaskAction
//    def greet() {
//        def file = getDestination()
//        file.parentFile.mkdirs()
//        file.write 'Hello!'
//    }
//}
//
//task greet(type: GreetingToFileTask) {
//    destination = { project.greetingFile }
//}
//
//task sayGreeting(dependsOn: greet) {
//    doLast {
//        println file(greetingFile).text
//    }
//}
//
//ext.greetingFile = "$buildDir/hello.txt"