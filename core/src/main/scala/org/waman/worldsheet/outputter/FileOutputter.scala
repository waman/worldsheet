package org.waman.worldsheet.outputter

import java.io.BufferedWriter
import java.nio.charset.Charset
import java.nio.file.StandardOpenOption._
import java.nio.file.{Files, Path}
import org.waman.worldsheet.SimulationUtil.defaultFormatter

import org.waman.worldsheet.{SimulationUtil, DataOutputter}

class FileOutputter[D](val path:Path,
                       val charset:Charset = Charset.defaultCharset,
                       val isOverride:Boolean = false,
                       val formatter:D => String = defaultFormatter) extends DataOutputter[D]{

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
    this.writer.write(SimulationUtil.sep)
  }

  override def dispose(): Unit = {
    try{
      this.writer.flush()
    }finally
      if(this.writer != null)
        this.writer.close()
  }
}