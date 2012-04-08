package lifesimulator

import javax.swing.JPanel
import java.awt.GridLayout
import Utils._

class ControlPanel(view: View) extends JPanel {
  setLayout(new GridLayout(1, 2))
  add(createButton("Start", () => view.model = new Model))
  add(createButton("Stop", () => println("Stop")))
}
