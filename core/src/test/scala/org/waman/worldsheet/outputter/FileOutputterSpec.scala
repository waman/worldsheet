package org.waman.worldsheet.outputter

import java.nio.charset.Charset
import java.nio.file.{FileAlreadyExistsException, Files}
import scala.collection.JavaConversions._
import org.scalatest.{FlatSpec, Matchers}

class FileOutputterSpec extends FlatSpec with Matchers {

  val dir = Files.createTempDirectory("FileOutputterTest-")

  def withFileOutputter[D](outputter:FileOutputter[D])(exe:FileOutputter[D] => Unit) = {
    outputter.prepare()
    try{
      exe.apply(outputter)
    }finally{
      outputter.dispose()
    }
  }

  def javaList[E](e:E*) = seqAsJavaList(List(e:_*))

  "A FileOutputter" should "write down data when output file does not exist" in {
    val path = dir.resolve("noFile.txt")
    assume(Files.notExists(path))

    withFileOutputter(new FileOutputter[String](path)){ out =>
      out.output("first line.")
      out.output("second line.")
      out.output("third line.")
    }

    Files.readAllLines(path) shouldBe javaList("first line.", "second line.", "third line.")
  }

  // isOverride property
  it should "throw a FileAlreadyExistsException at prepare() method when output file exists and isOverride property of FileOutputter is false (default)" in {
    val path = dir.resolve("existsFile-notOverride.txt")
    Files.createFile(path)
    assume(Files.exists(path))

    val out = new FileOutputter[String](path/*, isOverride = false*/)
    an [FileAlreadyExistsException] should be thrownBy{
      out.prepare()
    }
  }

  it should "write down data when output file exists and isOverride property of FileOutputter is true" in {
    val path = dir.resolve("existsFile-override.txt")
    Files.write(path, javaList("first line.", "second line."))
    assume(Files.exists(path))
    assume(Files.readAllLines(path) == javaList("first line.", "second line."))

    withFileOutputter(new FileOutputter[String](path, isOverride = true)){ out =>
      out.output("1st LINE.")
      out.output("2nd LINE.")
    }

    Files.readAllLines(path) shouldBe javaList("1st LINE.", "2nd LINE.")
  }

  // Charset property
  it should "write down data with default Charset" in {
    val path = dir.resolve("defaultCharset.txt")
    assume(Files.notExists(path))

    withFileOutputter(new FileOutputter[String](path/*, charset = Charset.defaultCharset*/)){ out =>
      out.output("あいうえお")
      out.output("かきくけこ")
    }

    Files.readAllLines(path) shouldBe javaList("あいうえお", "かきくけこ")
  }

  it should "write down data with the specified Charset" in {
    val path = dir.resolve("specifiedCharset.txt")
    assume(Files.notExists(path))

    withFileOutputter(new FileOutputter[String](path, charset = Charset.forName("EUC_JP"))){ out =>
      out.output("あいうえお")
      out.output("かきくけこ")
    }

    Files.readAllLines(path, Charset.forName("EUC_JP")) shouldBe javaList("あいうえお", "かきくけこ")
  }

  // Formatter property
  it should "write down data with default data formatter" in {
    val path = dir.resolve("defaultDataFormatter.txt")
    assume(Files.notExists(path))

    withFileOutputter(new FileOutputter[Map[String,Any]](path/*, formatter = SimulationUtil.defaultFormatter*/)){ out =>
      out.output(Map("current" -> 0, "next" -> 1))
      out.output(Map("current" -> 1, "next" -> 2))
    }

    Files.readAllLines(path) shouldBe javaList(
      "Map(current -> 0, next -> 1)",
      "Map(current -> 1, next -> 2)"
    )
  }

  it should "write down data with the specified data formatter" in {
    val path = dir.resolve("specifiedDataFormatter.txt")
    assume(Files.notExists(path))

    val formatter = (map:Map[String,Any]) => map.values.mkString(", ")

    withFileOutputter(new FileOutputter[Map[String,Any]](path, formatter = formatter)){ out =>
      out.output(Map("current" -> 0, "next" -> 1))
      out.output(Map("current" -> 1, "next" -> 2))
    }

    Files.readAllLines(path) shouldBe javaList("0, 1", "1, 2")
  }
}