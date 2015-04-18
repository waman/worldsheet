package org.waman.worldsheet.outputter

import java.io.BufferedWriter
import java.nio.charset.Charset
import java.nio.file.{Paths, Files, Path}
import java.nio.file.StandardOpenOption._

import org.waman.worldsheet.DataOutputter

class FileOutputter[D](path:Path, charset:Charset, isOverride:Boolean, formatter:D => String)
    extends DataOutputter[D]{

  private var writer:BufferedWriter = null

  override def prepare(): Unit = {
    this.writer =
      if(isOverride)
        Files.newBufferedWriter(path, charset, CREATE, TRUNCATE_EXISTING, WRITE)
      else
        Files.newBufferedWriter(path, charset, CREATE_NEW, WRITE)
  }

  override def output(data: D): Unit = {
    this.writer.write(formatter(data))
    this.writer.write(FileOutputter.sep)
  }

  override def dispose(): Unit = {
    try
      this.writer.flush()
    catch {
      case _:Throwable => // ignore
    }finally
      this.writer.close()
  }
}

object FileOutputter{

  val sep = System.getProperty("line.separator")

  def apply[D](path:Path,
               charset:Charset,
               isOverride:Boolean,
               formatter:(D => String)
                ):FileOutputter[D] =
    new FileOutputter[D](path, charset, isOverride, formatter)

  def apply[D](path:String,
               charset:Charset = Charset.defaultCharset(),
               isOverride:Boolean = false,
               formatter:(D => String) = (data:D) => data.toString
                ):FileOutputter[D] =
    apply(Paths.get(path), charset, isOverride, formatter)
}