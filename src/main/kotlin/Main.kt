import kotlinx.coroutines.*
import java.io.BufferedReader
import java.net.ServerSocket
import java.net.Socket
import java.util.*

fun main() = runBlocking {
    System.err.println("Logs from your program will appear here!")
    val serverSocket = ServerSocket(6379)

    while (true) {
        try {
            val client = serverSocket.accept()
            println("Accepted new connection")

            launch(Dispatchers.IO) {
                handleClient(client)
            }
        } catch (e: Exception) {
            break
        }
    }
    serverSocket.close()
}

suspend fun handleClient(client: Socket) {
    withContext(Dispatchers.IO) {
        val input = client.getInputStream()
        val output = client.getOutputStream()

        while (true) {
            val request = input.bufferedReader()
            val commandArgs = parseRedisCommand(request)
            if (commandArgs.isEmpty()) break

            println("Parsed command: $commandArgs")

            val command = commandArgs[0].uppercase(Locale.getDefault())
            when {
                command == CommandTypes.PING.name -> {
                    output.write("+PONG\r\n".toByteArray())
                }
                (command == CommandTypes.ECHO.name && commandArgs.size > 1) -> {
                    output.write(encodeBulkString(commandArgs[1]))
                }
                else -> {
                    output.write("-ERR unknown command\r\n".toByteArray())
                }
            }
            output.flush()
        }
    }
}

fun parseRedisCommand(reader: BufferedReader): List<String> {
    val lines = mutableListOf<String>()
    var line = reader.readLine() ?: return emptyList()

    if (line.startsWith("*")) {
        val numberOfArgs = line.drop(1).toIntOrNull() ?: return emptyList()
        for (i in 0 until numberOfArgs) {
            line = reader.readLine() ?: return emptyList()
            line = reader.readLine() ?: return emptyList()
            lines.add(line)
        }
    }
    return lines
}

fun encodeBulkString(data: String): ByteArray {
    return "\$${data.length}\r\n$data\r\n".toByteArray()
}
