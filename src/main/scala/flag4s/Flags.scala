package flag4s

import scala.util.{Failure, Success, Try}

/**
  * Created by anton on 25.07.16.
  */
trait Flags {
  this: App =>
  private val scanned: Map[String, String] = scan(args)

  val flagPrefix = "-"

  private def scan(arguments: Array[String]): Map[String, String] = {
    val start = Array[(String, String)]()
    arguments.foldLeft(start)((accumulator, current) => {
      if (current.startsWith(flagPrefix)) {
        accumulator :+ (current.stripPrefix(flagPrefix), "")
      } else {
        accumulator.updated(accumulator.length-1, (accumulator.last._1, accumulator.last._2 + "" + current))
      }
    })
      .toMap
  }

  def string(flag: String): Option[String] = scanned.get(flag)

  def int(flag: String): Option[Int] = Try(scanned.get(flag).map(_.toInt)) match {
    case Failure(exception) => throw FlagParseException(flag, exception.getMessage)
    case Success(value)     => value
  }

  def double(flag: String): Option[Double] = Try(scanned.get(flag).map(_.toDouble)) match {
    case Failure(exception) => throw FlagParseException(flag, exception.getMessage)
    case Success(value)     => value
  }

  def boolean(flag: String): Option[Boolean] = {
    Try(scanned.get(flag).map(element => if (element == "") true else element.toBoolean)) match {
      case Failure(exception) => throw FlagParseException(flag, exception.getMessage)
      case Success(value)     => value
    }
  }

  def arrayOfStrings(flag: String, delim: String): Option[Array[String]] = scanned.get(flag).map(_.split(delim))

  def arrayOfInts(flag: String, delim: String): Option[Array[Int]] = {
    Try(scanned.get(flag).map(_.split(delim).map(_.toInt))) match {
      case Failure(exception) => throw FlagParseException(flag, exception.getMessage)
      case Success(value)     => value
    }
  }

  def arrayOfDoubles(flag: String, delim: String): Option[Array[Double]] = {
    Try(scanned.get(flag).map(_.split(delim).map(_.toDouble))) match {
      case Failure(exception) => throw FlagParseException(flag, exception.getMessage)
      case Success(value)     => value
    }
  }
}

case class FlagParseException(flag: String, message: String) extends Exception(s"$flag: $message")