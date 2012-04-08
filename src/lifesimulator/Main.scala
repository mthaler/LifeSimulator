package lifesimulator

import java.awt.EventQueue

object Main {
  def main(args: Array[String]) {
    EventQueue.invokeLater(new Runnable {
      def run() {
        (new MainWindow).setVisible(true)
      }
    })
  }
}
