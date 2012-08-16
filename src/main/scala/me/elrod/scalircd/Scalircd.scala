package me.elrod.scalircd
import akka.actor._
import java.net.InetSocketAddress

class Scalircd extends Actor {
  override def preStart {
    IOManager(context.system) listen new InetSocketAddress(6667)
  }

  def receive = {
    case IO.NewClient(server) => server.accept()
    case IO.Read(rHandle, bytes) => rHandle.asSocket write bytes.compact
  }
}

object Scalircd extends App {
  ActorSystem().actorOf(Props(new Scalircd()))
}
