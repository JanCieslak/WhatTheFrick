const val DEFAULT_MEM_N = 10

class WtfOptions(
    var asChar: Boolean = true,
    var memN: Int = 8,
    var shouldPrintPointer: Boolean = true
) {
    fun help() {
        println("""
            #help               - prints this message
            #show               - show current memory and pointer (if #mem N == 0)
                                    then it shows you 10 first cells and show you
                                    position of the pointer even if (#pointer == N)
            #mem N              - after every command or sequence of commands
                                    print N first memory blocks (default 8 cells)
                                    if N == 0 memory blocks won't be printed 
            #pointer Y/N        - after command or sequence of commands
                                    print info about pointer position (default Y)
            #char Y/N           - print result as char ? Otherwise print as number (Byte)
                                    (default Y)
            #record filename    - record and save session to a given path 
            #save filename      - save whole session to a given path
            #exit               - close the program
            
        """.trimIndent())
    }

    fun parse(line: String): Boolean {
        val line = line.toLowerCase()

        try {
            when {
                line.contains("#help")      -> help()
                line.contains("#mem")       -> memN = line.split(" ")[1].toInt()
                line.contains("#pointer")   -> shouldPrintPointer = line.split(" ")[1].contains("y")
                line.contains("#char")      -> asChar = line.split(" ")[1].contains("y")
                line.contains("#record")    -> TODO()
                line.contains("#save")      -> TODO()
                else -> return false
            }
            return true

        } catch (e: Exception) {
            println("Wrong ")
            return false
        }
    }
}