import java.net.ServerSocket

fun main(args: Array<String>) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.err.println("Logs from your program will appear here!")

    val serverSocket = ServerSocket(6379)
    val client = serverSocket.accept()

    val input = client.getInputStream()
    val output = client.getOutputStream()

    println("accepted new connection")

    while (true) {
        val request = input.bufferedReader()
        val requestBody = request.readLine().orEmpty()
        if(requestBody.isEmpty()) break

        output.write("+PONG\r\n".toByteArray())
        output.flush()
    }
}
