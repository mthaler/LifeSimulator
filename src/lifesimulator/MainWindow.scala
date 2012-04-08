package lifesimulator

import java.awt.{Dimension, BorderLayout}
import javax.swing._
import java.awt.event.{WindowEvent, WindowListener}

class MainWindow extends JFrame {
  setTitle("Life Simulator")
  setLayout(new BorderLayout)

  addWindowListener(new WindowListener {
    def windowClosed(e: WindowEvent) {
      view.model match {
        case Some(m) => m.timer.stop()
        case None =>
      }
    }

    def windowDeactivated(e: WindowEvent) {}

    def windowDeiconified(e: WindowEvent) {}

    def windowClosing(e: WindowEvent) {}

    def windowActivated(e: WindowEvent) {}

    def windowIconified(e: WindowEvent) {}

    def windowOpened(e: WindowEvent) {}
  })

  val view = new View
  add(view, BorderLayout.CENTER)

  val box = new Box(BoxLayout.Y_AXIS)
  box.setPreferredSize(new Dimension(300, 800))
  box.add(new ConfigPanel)
  val statusPanel = new StatusPanel
  box.add(statusPanel)
  box.add(new ControlPanel(view, statusPanel))
  box.add(Box.createVerticalStrut(Int.MaxValue))
  add(box, BorderLayout.EAST)

  setPreferredSize(new Dimension(1100, 800))
  setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  pack()
}
