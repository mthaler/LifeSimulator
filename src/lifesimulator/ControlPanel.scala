package lifesimulator

import javax.swing.JPanel
import java.awt.GridLayout
import Utils._

class ControlPanel(view: View, statusPanel: StatusPanel) extends JPanel {
  setLayout(new GridLayout(1, 2))
  add(createButton("Start", () => {
    val model = new Model
    view.model = model
    statusPanel.model = model
  }))
  add(createButton("Stop", () => println("Stop")))
}
