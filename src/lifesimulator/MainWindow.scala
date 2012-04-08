package lifesimulator

import java.awt.{Dimension, BorderLayout}
import javax.swing._

class MainWindow extends JFrame {
  setTitle("Life Simulator")
  setLayout(new BorderLayout)

  val view = new View
  add(view, BorderLayout.CENTER)

  val box = new Box(BoxLayout.Y_AXIS)
  box.add(new ConfigPanel)
  box.add(new ControlPanel(view))
  box.add(Box.createVerticalStrut(Int.MaxValue))
  add(box, BorderLayout.EAST)

  setPreferredSize(new Dimension(1000, 800))
  setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  pack()
}
