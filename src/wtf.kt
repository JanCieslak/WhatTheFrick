// Example programs
const val HELLO_WORLD = "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++."
const val MULTIPLY = """++++++++++++++++++++     (20)
                        >+++++<                  (5)
                        [>[->+>+<<]>[-<+>]<<-]   multiply
                        >[-]>>[-<<<+>>>]<<<      cleansing
                        .                        print 20x5 = 100 ('d' in ASCII)"""


fun main() {
    val program = WtfProgram()
    val options = WtfOptions()

    options.help()

    while (true) {
        print(">>> ")
        val line = readLine()

        if (line.isNullOrEmpty())
            continue

        if (line.toLowerCase().contains("#exit"))
            break
        else if (line.toLowerCase().contains("#show")) {
            program.printMemory(if (options.memN > 0) options.memN else DEFAULT_MEM_N)
            program.printPointer()
            println()
            continue
        }

        // if options haven't changed
        if (!options.parse(line)) {

            // parse the line and if it contains . print the result as well
            if (line.contains("."))
                print("result: ")

            for (result in program.parse(line))
                if (options.asChar) print(result.toChar()) else print("$result ")

            if (line.contains("."))
                println()

            // print memory blocks
            if (options.memN > 0)
                program.printMemory(options.memN)

            // print pointer position
            if (options.shouldPrintPointer)
                program.printPointer()

            println()
        }
    }
}