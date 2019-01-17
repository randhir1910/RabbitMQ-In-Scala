import com.rabbitmq.client._
import java.io.{ByteArrayInputStream, ObjectInputStream}

object Recv {

  private val QUEUE_NAME = "hello"

  def main(argv: Array[String]) {
    val factory = new ConnectionFactory()
    factory.setHost("127.0.0.1")
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.queueDeclare(QUEUE_NAME, false, false, false, null)
    println(" [*] Waiting for messages. To exit press CTRL+C")
    val deliverCallback: DeliverCallback = (_, delivery) => {
     val output = deserialize(delivery.getBody)
      println(" [1] Received '" + output+ "'")
    }
    channel.basicConsume(QUEUE_NAME, true, deliverCallback, _ => { })
  }

  def deserialize(bytes: Array[Byte]): Person = {
    val ois = new ObjectInputStream(new ByteArrayInputStream(bytes))
    val value = ois.readObject
    ois.close
    value.asInstanceOf[Person]
  }

}