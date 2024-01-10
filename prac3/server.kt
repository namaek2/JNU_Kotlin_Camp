import java.io.*
import java.net.*
import java.nio.charset.Charset

fun main() {
    try {
        var serverSocket: ServerSocket = ServerSocket(40067)
        println("server started")
        while (true) {
            println("wait connection")
            try{
                var socket: Socket = serverSocket.accept()
                println("connected from ${socket.inetAddress}")

                var ins: InputStream = socket.getInputStream()
                var dis : DataInputStream = DataInputStream(ins)
                var outs: OutputStream = socket.getOutputStream()
                var dos: DataOutputStream = DataOutputStream(outs)

                while(true) {
                    when (val command: Int = dis.readInt()){
                        10 ->{
                            println("requested file list")
                            val result: String = "aa.txt\nbb.txt\ndd.txt"
                            val arr = result.toByteArray(Charset.defaultCharset())
                            println("sent param1Length)${arr.size}")
                            dos.writeInt(arr.size)
                            dos.write(arr)
                        }
                        20 -> {
                            val param1Length=dis.readInt()
                            println("received param1Length:${param1Length}")
                            if(param1Length > 0) {
                                val buff: ByteArray = ByteArray(param1Length)
                                dis.readFully(buff, 0, param1Length)
                                val fileName:String = String(buff, 0, param1Length)
                                println("requested fileName:${fileName}")
                                val result = "This is contents of $fileName"
                                val arr = result.toByteArray(Charset.defaultCharset())
                                println("sent param1Length)${arr.size}")
                                dos.writeInt(arr.size)
                                dos.write(arr)
                            }
                        }
                        else -> {
                            println("unknown command:${command}")
                        }
                    }
                }

            }
            catch(e:Exception) {
                println("error:${e}")
            }
        }
    }
    catch(e:IOException) {
        e.printStackTrace()
    }
}