package com.github.sguzman.brotli

import java.io.{DataInputStream, DataOutputStream}

import scala.sys.process._

// Thanks to some stackoverflow answer i found
// will put it here when i find it
object Brotli {
  def compress(s: String): Array[Byte] = {

    locally {
      var output: Array[Byte] = null
      val cmd = "brotli"
      val proc = cmd.run(new ProcessIO(
        in => {
          val writer = new java.io.PrintWriter(in)
          writer.write(s)
          writer.close()
        },
        out => {
          val src = new DataInputStream(out)
          output = src.readAllBytes
          src.close()
        },
        _.close()
      ))

      val code = proc.exitValue()
      if (code != 0) {
        throw new Exception(s"Subprocess exited with code $code.")
      }

      output
    }
  }

  def decompress(s: Array[Byte]): String = {
    locally {
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

      output
    }
  }

}