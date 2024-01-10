import java.io.*
import java.net.*
import java.nio.charset.Charset
import java.util.*

fun main(){
    try{
        println("client started")
        val serverIp: String = "127.0.0.1"
        var socket: Socket= Socket(serverIp, 40067)
        println("client is connected to $serverIp")
        //input stream
        var ins: InputStream = socket.getInputStream()
        var dis: DataInputStream = DataInputStream(ins)
        var outs: OutputStream = socket.getOutputStream()
        var dos: DataOutputStream = DataOutputStream(outs)

        while(true){
            print("(Q)uit, (L)ist, (G)et > ")
            val reader = Scanner(System.`in`)
            val input:String = reader.nextLine().toLowerCase()
            when(input) {
                "q" -> break
                "i" -> {
                    dos.writeInt(10)
                    val param1length = dis.readInt()
                    println("received param1Length:${param1length}")
                    if(param1length > 0) {
                        val buff: ByteArray = ByteArray(param1length)
                        dis.readFully(buff, 0, param1length)
                        val fileList : String = String(buff, 0, param1length)
                        println(fileList)
                    }
                }
                "g" -> {
                    dos.writeInt(20)
                    val fileName: String="aa.txt"
                    val arr = fileName.toByteArray(Charset.defaultCharset())
                    println("sent param1Length:${arr.size}")
                    dos.writeInt(arr.size)
                    dos.write(arr)

                    val param1Length = dis.readInt()
                    println("received param1Length:${param1Length}")
                    if(param1Length > 0) {
                        val buff: ByteArray = ByteArray(param1Length)
                        dis.readFully(buff, 0, param1Length)
                        println("received flie content")
                        val content: String = String(buff, 0, param1Length)
                        println(content)
                    }
                }
                else -> {
                    println("unknown command:${input}")
                }
            }
        }
        dis.close()
    }catch(e:IOException){
        e.printStackTrace()
    }
}