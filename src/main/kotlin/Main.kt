import kotlinx.coroutines.*
import java.net.ServerSocket
import java.net.Socket

fun main() = runBlocking  {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.err.println("Logs from your program will appear here!")

    val serverSocket = ServerSocket(6379)

    while (true) {
        try {
            val client = serverSocket.accept()

            println("accepted new connection")

            launch(Dispatchers.IO) {
                handleClient(client = client)
            }
        }
        catch (e : Exception) {
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
            val requestBody = request.readLine().orEmpty()
            if (requestBody.isEmpty()) break
            println("request received: $requestBody")

            output.write("+PONG\r\n".toByteArray())
            output.flush()
        }
    }
}
