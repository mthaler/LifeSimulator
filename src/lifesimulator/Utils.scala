package lifesimulator

import javax.swing.{JButton, JTextField, JLabel}
import java.awt.event.{FocusEvent, FocusListener, ActionEvent, ActionListener}


object Utils {
  def createLabel(text: String) = new JLabel(text)

  def createTextField(text: String, action: (String) => Unit) = {
    val textField = new JTextField(text)
    textField.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        textField.transferFocus()
      }
    })
    textField.addFocusListener(new FocusListener {
      def focusGained(e: FocusEvent) {}

      def focusLost(e: FocusEvent) {
        action(textField.getText)
      }
    })
    textField
  }

  def createButton(text: String, action: () => Unit) = {
    val button = new JButton(text)
    button.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        action()
      }
    })
    button
  }
}
