package lifesimulator

import javax.swing.JPanel
import java.awt.{Color, Graphics2D, Graphics}

class View extends JPanel with Observer {
  private var _model: Option[Model] = None

  def model = _model

  def model_=(value: Model) {
    if (_model.isDefined) _model.get.removeObserver(this)
    value.addObserver(this)
    _model = Some(value)
  }

  override def paintComponent(g: Graphics) {
    super.paintComponent(g)
    _model match {
      case Some(m) => {
        val g2 = g.asInstanceOf[Graphics2D]
        for (x <- 0 until getWidth) {
          for (y <- 0 until getHeight) {
            val cell = m(getCellX(x), getCellY(y))
            cell match {
              case EmptyCell => g2.setPaint(Color.gray)
              case i: ImmortalCell => g2.setPaint(Color.red)
              case m: MortalCell => g2.setPaint(Color.blue)
            }
            g2.drawRect(x, y, 1, 1)
          }
        }
      }
      case None =>
    }
  }

  def getCellX(x: Int): Int = (x.asInstanceOf[Double] / getWidth.asInstanceOf[Double] * Config.Size.asInstanceOf[Double]).toInt

  def getCellY(y: Int): Int = (y.asInstanceOf[Double] / getHeight.asInstanceOf[Double] * Config.Size.asInstanceOf[Double]).toInt

  def updated() {
    repaint()
  }
}
