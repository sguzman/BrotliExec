package com.github.sguzman.brotli

import java.io.{DataInputStream, DataOutputStream, EOFException}

import scala.collection.mutable
import scala.sys.process._
import scala.util.{Failure, Success}

// Thanks to some stackoverflow answer i found
// will put it here when i find it
object Brotli {
  def compress(s: String): Array[Byte] = {
    locally {
      scribe.info("Compressing text...")

      var output = mutable.ArrayBuffer[Byte]()
      val cmd = "brotli"
      val proc = cmd.run(new ProcessIO(
        in => {
          val writer = new java.io.PrintWriter(in)
          writer.write(s)
          writer.close()
        },
        out => {
          val src = new DataInputStream(out)
          def readUntilExhaust: Unit = {
            util.Try(src.readByte) match {
              case Success(v) =>
                output.append(v)
                readUntilExhaust
              case Failure(e) => e match {
                case _: EOFException => src.close()
              }
            }
          }

          readUntilExhaust
        },
        _.close()
      ))

      val code = proc.exitValue()
      if (code != 0) {
        throw new Exception(s"Subprocess exited with code $code.")
      }

      scribe.info("Compressed text")
      output.toArray
    }
  }

  def decompress(s: Array[Byte]): String = {
    locally {
      scribe.info("Decompressing byte array...")

      var output: String = ""
      val cmd = "brotli -d"
      val proc = cmd.run(new ProcessIO(
        in => {
          val writer = new DataOutputStream(in)
          writer.write(s)
          writer.close()
        },
        out => {
          val src = scala.io.Source.fromInputStream(out)
          output = src.getLines.mkString
          src.close()
        },
        _.close()
      ))

      val code = proc.exitValue()
      if (code != 0) {
        throw new Exception(s"Subprocess exited with code $code.")
      }

      scribe.info("Decompressed byte array")
      output
    }
  }

}