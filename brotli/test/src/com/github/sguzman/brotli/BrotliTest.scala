package com.github.sguzman.brotli

import org.scalatest.FlatSpec

class BrotliTest extends FlatSpec {

  "A computer" should "have brotli installed" in {
    Brotli.compress("some string here")
  }

  "Brotli" should "be able to undo compression" in {
    val str = "Another string that can be uncompressed"
    val compressed = Brotli.compress("Another string that can be uncompressed")
    val decompressed = Brotli.decompress(compressed)
    assert(decompressed == str, s"$str =?= $decompressed")
  }
}