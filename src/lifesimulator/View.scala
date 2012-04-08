package lifesimulator

import javax.swing.JPanel
import java.awt.{Color, Graphics2D, Graphics}

object View {
  val RedHue = 0.0f
  val BlueHue = 2.0f / 3.0f
}

class View extends JPanel with Observer {

  import View._

  private var _model: Option[Model] = None

  def model = _model

  def model_=(value: Model) {
    if (_model.isDefined) _model.get.removeObserver(this)
    value.addObserver(this)
    _model = Some(value)
  }

  override def paintComponent(g: Graphics) {
    super.paintComponent(g)
    val g2 = g.asInstanceOf[Graphics2D]
    _model match {
      case Some(m) => {
        val minimumFitness = m.getMinimumFitness
        val maximumFitness = m.getMaximumFitness
        val averageFitness = m.getAverageFitness
        for (x <- 0 until getWidth) {
          for (y <- 0 until getHeight) {
            val cell = m(getCellX(x), getCellY(y))
            cell match {
              case EmptyCell => g2.setPaint(Color.gray)
              case i: ImmortalCell => g2.setPaint(getColor(i.fitness, minimumFitness, maximumFitness, averageFitness, RedHue))
              case m: MortalCell => g2.setPaint(getColor(m.fitness, minimumFitness, maximumFitness, averageFitness, BlueHue))
            }
            g2.drawRect(x, y, 1, 1)
          }
        }
      }
      case None =>
    }
  }

  def getColor(fitness: Double, min: Double, max: Double, average: Double, hue: Float): Color = {
    if (fitness < average) {
      // use a lighter color
      val saturation = 0.2 + 0.8 * (fitness - min) / (average - min)
      new Color(Color.HSBtoRGB(hue, saturation.toFloat, 1.0f))
    } else {
      val brightness = 0.2 + 0.8 * (max - fitness) / (max - average)
      new Color(Color.HSBtoRGB(hue, 1.0f, brightness.toFloat))
    }
  }

  def getCellX(x: Int): Int = (x.asInstanceOf[Double] / getWidth.asInstanceOf[Double] * Config.Size.asInstanceOf[Double]).toInt

  def getCellY(y: Int): Int = (y.asInstanceOf[Double] / getHeight.asInstanceOf[Double] * Config.Size.asInstanceOf[Double]).toInt

  def updated() {
    repaint()
  }
}
