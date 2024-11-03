import java.net.ServerSocket

fun main(args: Array<String>) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.err.println("Logs from your program will appear here!")

    var serverSocket = ServerSocket(6379)

    while (true) {
        val client = serverSocket.accept()
        println("accepted new connection")

        val outputStream = client.getOutputStream()
        outputStream.write("+PONG\r\n".toByteArray())
        outputStream.flush()

        client.close()
    }
}
