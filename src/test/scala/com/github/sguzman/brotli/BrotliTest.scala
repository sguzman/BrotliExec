package com.github.sguzman.brotli

import org.scalatest.FlatSpec

class BrotliTest extends FlatSpec {

  "A computer" should "have brotli installed" in {
    Brotli.compress("{}")
  }
}