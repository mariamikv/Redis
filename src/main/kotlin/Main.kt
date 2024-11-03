import java.net.ServerSocket

fun main(args: Array<String>) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.err.println("Logs from your program will appear here!")

    var serverSocket = ServerSocket(6379)
    val client = serverSocket.accept()

    println("accepted new connection")

    val inputStream = client.getInputStream()
    val outputStream = client.getOutputStream()

    while (true) {
        val data = inputStream.read()

        if (data == -1) {
            break
        }

        outputStream.write("+PONG\r\n".toByteArray())

    }
}
