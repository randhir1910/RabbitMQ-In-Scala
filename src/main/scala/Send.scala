import com.rabbitmq.client.ConnectionFactory
import java.io.{ByteArrayOutputStream, ObjectOutputStream}

object Send {

  private val QUEUE_NAME = "hello"

  def main(argv: Array[String]) {
    val factory = new ConnectionFactory()
    factory.setHost("127.0.0.1")
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.queueDeclare(QUEUE_NAME, false, false, false, null)
    val message1 = Person("Randhir", 25)
    channel.basicPublish("", QUEUE_NAME, null, serialize(message1))
    println(" [x] Sent '" + message1 + "'")
    channel.close()
    connection.close()
  }
  def serialize(value: Any): Array[Byte] = {
    val stream: ByteArrayOutputStream = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(stream)
    oos.writeObject(value)
    oos.close
    stream.toByteArray()
  }
}
