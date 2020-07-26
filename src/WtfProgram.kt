const val DEFAULT_MEMORY = 30_000

class WtfProgram {
    private val memory = ByteArray(DEFAULT_MEMORY)
    private var pointer = 0

    fun printMemory(n: Int) {
        print("memory: ")
        for (index in 0..n)
            print("${memory[index]} ")
        println()
    }

    fun printPointer() {
        println("pointer: $pointer")
    }

    fun parse(code: String): Iterable<Byte> = sequence {
        var index = 0

        while (index < code.length)
        {
            when (code[index++]) {
                '+' -> memory[pointer]++
                '-' -> memory[pointer]--
                '<' -> pointer--
                '>' -> pointer++
                ',' -> {
                    print("Input: ")
                    memory[pointer] = readLine()!!.toByte()
                }
                '.' -> yield(memory[pointer])
                '[' -> {
                    var openLoops = 1
                    var offset = 1
                    var subCode = ""

                    // find body of the loop
                    while (true) {
                        if (index + offset >= code.length)
                            throw RuntimeException("Error: forgot to close the loop with \']\'")

                        val peekChar = code[index + offset]

                        if (peekChar == '[')
                            openLoops++
                        else if (peekChar == ']') {
                            if (openLoops == 1) {
                                subCode = code.substring(index until index + offset)
                                break
                            }
                            openLoops--
                        }

                        offset++
                    }

                    while (memory[pointer] != 0.toByte())
                        parse(subCode)

                    // point after the loop -> ]...
                    index += offset
                }
            }
        }
    }.toList()
}