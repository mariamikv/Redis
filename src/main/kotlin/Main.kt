import java.net.ServerSocket

fun main(args: Array<String>) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.err.println("Logs from your program will appear here!")

    var serverSocket = ServerSocket(6379)
    val client = serverSocket.accept()

    println("accepted new connection")

    val inputStream = client.getInputStream()
    val outputStream = client.getOutputStream()

    val buffer = ByteArray(1024)
    val command = StringBuilder()

    while (true) {
        val data = inputStream.read()

        if (data == -1) {
            break
        }

        command.append(String(buffer, 0, data))

       if (command.toString().contains("+PONG\r\n")) {
           outputStream.write("+PONG\r\n".toByteArray())
           outputStream.flush()
           command.clear()
       }
    }
}
