package lifesimulator

import javax.swing.JPanel
import Utils._
import java.awt.{Dimension, GridLayout}

class ConfigPanel extends JPanel {

  setLayout(new GridLayout(5, 2))

  add(createLabel("Size:"))
  add(createTextField(Config.Size.toString, (text: String) => Config.Size = text.toInt))

  add(createLabel("Max age:"))
  add(createTextField(Config.MaxAge.toString, (text: String) => Config.MaxAge = text.toInt))

  add(createLabel("Fitness:"))
  add(createTextField(Config.InitalFitness.toString, (text: String) => Config.InitalFitness = text.toDouble))

  add(createLabel("Mutation:"))
  add(createTextField(Config.MutationFactor.toString, (text: String) => Config.MutationFactor = text.toDouble))

  add(createLabel("Fitness Decrease:"))
  add(createTextField(Config.FitnessDecrease.toString, (text: String) => Config.FitnessDecrease = text.toDouble))


  setPreferredSize(new Dimension(200, 0))
}
