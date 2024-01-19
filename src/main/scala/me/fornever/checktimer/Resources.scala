package me.fornever.checktimer

import org.girod.javafx.svgimage.{SVGImage, SVGLoader}
import scalafx.scene.image.Image

object Resources {

  val pinIcon: SVGImage = SVGLoader.load(getClass.getClassLoader.getResource("icons/pin.svg"))
  val checktimerIcon = new Image("icons/checktimer_1024x1024.png")
}
