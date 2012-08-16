package me.elrod.scalircd
import akka.actor._
import akka.util.ByteString
import java.net.InetSocketAddress

class Scalircd extends Actor {
  override def preStart {
    IOManager(context.system) listen new InetSocketAddress(6667)
  }

  def receive = {
    case IO.NewClient(server) => server.accept()
    case IO.Read(rHandle, bytes) =>
      rHandle.asSocket write ByteString(bytes.decodeString("UTF-8").toUpperCase)
  }
}

object Scalircd extends App {
  ActorSystem().actorOf(Props[Scalircd])
}
