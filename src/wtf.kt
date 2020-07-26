const val DEFAULT_MEMORY = 30_000

// Example programs
const val HELLO_WORLD = "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++."
const val MULTIPLY = """++++++++++++++++++++     (20)
                        >+++++<                  (5)
                        [>[->+>+<<]>[-<+>]<<-]   multiply
                        >[-]>>[-<<<+>>>]<<<      cleansing
                        .                        print 20x5 = 100 ('d' in ASCII)"""



var shouldRun = true
var asChar = true
var memN = 8
var printPointer = true

fun main() {
    println("""
        #show               - show current memory and pointer
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

    val program = WtfProgram()

    while (shouldRun) {
        print(">>> ")
        val line = readLine()

        if (line.isNullOrEmpty())
            continue

        if (!checkOptions(program, line.toLowerCase())) {
            // parse the line and if it contains . print the result as well
            if (line.contains("."))
                print("result: ")

            for (result in program.parse(line))
                if (asChar) print(result.toChar()) else print(result)

            if (line.contains("."))
                println()

            // print memory blocks
            if (memN > 0)
                program.printMemory(memN)

            // print pointer position
            if (printPointer)
                program.printPointer()

            println()
        }
    }
}

// todo: cleaner solution and separate to another file
fun checkOptions(program: WtfProgram, line: String): Boolean = when {
    line.contains("#exit") -> {
        shouldRun = false
        true
    }
    line.contains("#show") -> {
        program.printMemory(memN)
        program.printPointer()
        println()
        true
    }
    line.contains("#mem") -> {
        try {
            memN = line.split(" ")[1].toInt()
        } catch (e: Exception) {
            println("Wrong format! Correct one: e.g. mem 5")
            false
        }

        true
    }
    line.contains("#pointer") -> {
        try {
            printPointer = line.split(" ")[1].toUpperCase().contains("Y")
        } catch (e: Exception) {
            println("Wrong format! Correct one: e.g. char Y")
            false
        }

        true
    }
    line.contains("#char") -> {
        try {
            asChar = line.split(" ")[1].toUpperCase().contains("Y")
        } catch (e: Exception) {
            println("Wrong format! Correct one: e.g. char Y")
            false
        }

        true
    }
    else -> false
}


