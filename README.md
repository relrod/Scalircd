# Scalircd

An experimental IRCD writen in Scala using Akka Actors.

# The idea

The [actor model](https://en.wikipedia.org/wiki/Actor_model) is a form of
concurrency which allows you to pass "messages" to actors, and allows actors
to "act" on them.

See the link above for a more in-depth explanation of how they work.

The idea here is that we can use actors for many of the core jobs that an IRCD
would need to do.

We can use them for encapsulating actions performed by users (PRIVMSG, JOIN,
NICK, and so on...), for things like stable linking between servers (using
remote actors).

We simply encode each of these actions into an object, for example, something
like:

```scala
object Channel {
  case class PRIVMSG(
    author: User,
    destination: String, // or we could encapsulate into a Channel/User object.
    text: String
  )
}
```

This allows us to send the `Channel.PRIVMSG` object to a particular Actor
System, to be dealt with. The Actor System might do something like:

```scala
def receive {
  case Channel.PRIVMSG(author, destination, string) => {
    Channel.find(destination) match {
      case channel => channel.send(author.toString, string)
      case None => author.sendError("No such channel exists.")
    }
  }
}
```

The above is a very simplified and contrived example.

# Configuration

(not yet implemented)

Scalircd (will be) configured using the Typesafe Config library, found
[here](https://github.com/typesafehub/config/).

```shell
cp src/main/resources/application.conf.dist src/main/resources/application.conf
$EDITOR src/main/resources/application.conf
```

The config file will be fairly self-explanatory, and well commented.

# License

This is experimental, and I want people to be able to use all or part of it in
their own projects. As such, I'm not going to use a copyleft license here.

I'm going to release Scalircd under a 3-clause BSD license, the same as Scala
itself.

```
SCALA LICENSE

Copyright (c) 2012 Ricky Elrod, unless otherwise specified.
All rights reserved.

Permission to use, copy, modify, and distribute this software in source
or binary form for any purpose with or without fee is hereby granted,
provided that the following conditions are met:

   1. Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.

   3. Neither the name of Scalircd nor the names of its contributors
      may be used to endorse or promote products derived from this
      software without specific prior written permission.


THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.
```

# Hacking

We will be using ScalaTest, and we assume that you have a working SBT
environment.

Please ensure all tests pass and read the above (3-clause BSD) license, before
sending pull requests.

We love contributions, so please don't hesitate to send patches.
