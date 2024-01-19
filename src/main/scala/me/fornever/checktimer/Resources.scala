package me.fornever.checktimer

import org.girod.javafx.svgimage.{SVGImage, SVGLoader}

object Resources {

  val pinIcon: SVGImage = SVGLoader.load(getClass.getClassLoader.getResource("icons/pin.svg"))
}
