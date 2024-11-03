import java.net.ServerSocket

fun main(args: Array<String>) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.err.println("Logs from your program will appear here!")

    var serverSocket = ServerSocket(6379)
    val client = serverSocket.accept()

    client.getOutputStream().write("+PONG\\r\\n".toByteArray())
    println("accepted new connection")
}
