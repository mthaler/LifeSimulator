package lifesimulator

import Utils._
import javax.swing.{JLabel, BoxLayout, Box}

class StatusPanel extends Box(BoxLayout.Y_AXIS) with Observer {

  private var _model: Option[Model] = None

  def model = _model

  def model_=(value: Model) {
    if (_model.isDefined) _model.get.removeObserver(this)
    value.addObserver(this)
    _model = Some(value)
  }

  val boxNumberOfImmortalCells = new Box(BoxLayout.X_AXIS)
  add(boxNumberOfImmortalCells)

  boxNumberOfImmortalCells.add(createLabel("Number of immortal cells: "))
  val labelNumberOfImmortalCells = new JLabel()
  boxNumberOfImmortalCells.add(labelNumberOfImmortalCells)
  boxNumberOfImmortalCells.add(Box.createGlue())

  val boxNumberOfMortalCells = new Box(BoxLayout.X_AXIS)
  add(boxNumberOfMortalCells)

  boxNumberOfMortalCells.add(createLabel("Number of mortal cells: "))
  val labelNumberOfMortalCells = new JLabel()
  boxNumberOfMortalCells.add(labelNumberOfMortalCells)
  boxNumberOfMortalCells.add(Box.createGlue())

  val boxAverageFitnessOfImmortalCells = new Box(BoxLayout.X_AXIS)
  add(boxAverageFitnessOfImmortalCells)

  boxAverageFitnessOfImmortalCells.add(createLabel("Average fitness of immortal cells: "))
  val labelAverageFitnessOfImmortalCells = new JLabel()
  boxAverageFitnessOfImmortalCells.add(labelAverageFitnessOfImmortalCells)
  boxAverageFitnessOfImmortalCells.add(Box.createGlue())

  val boxAverageFitnessOfMortalCells = new Box(BoxLayout.X_AXIS)
  add(boxAverageFitnessOfMortalCells)

  boxAverageFitnessOfMortalCells.add(createLabel("Average fitness of mortal cells: "))
  val labelAverageFitnessOfMortalCells = new JLabel()
  boxAverageFitnessOfMortalCells.add(labelAverageFitnessOfMortalCells)
  boxAverageFitnessOfMortalCells.add(Box.createGlue())

  val boxMinimumFitness = new Box(BoxLayout.X_AXIS)
  add(boxMinimumFitness)

  boxMinimumFitness.add(createLabel("Minimum fitness: "))
  val labelMinimumFitness = new JLabel()
  boxMinimumFitness.add(labelMinimumFitness)
  boxMinimumFitness.add(Box.createGlue())

  val boxMaximumFitness = new Box(BoxLayout.X_AXIS)
  add(boxMaximumFitness)

  boxMaximumFitness.add(createLabel("Maximum fitness: "))
  val labelMaximumFitness = new JLabel()
  boxMaximumFitness.add(labelMaximumFitness)
  boxMaximumFitness.add(Box.createGlue())

  val boxMinimumFitnessOfImmortalCells = new Box(BoxLayout.X_AXIS)
  add(boxMinimumFitnessOfImmortalCells)

  boxMinimumFitnessOfImmortalCells.add(createLabel("Minimum fitness immortal cells: "))
  val labelMinimumFitnessOfImmortalCells = new JLabel()
  boxMinimumFitnessOfImmortalCells.add(labelMinimumFitnessOfImmortalCells)
  boxMinimumFitnessOfImmortalCells.add(Box.createGlue())

  val boxMaximumFitnessOfImmortalCells = new Box(BoxLayout.X_AXIS)
  add(boxMaximumFitnessOfImmortalCells)

  boxMaximumFitnessOfImmortalCells.add(createLabel("Maximum fitness immortal cells: "))
  val labelMaximumFitnessOfImmortalCells = new JLabel()
  boxMaximumFitnessOfImmortalCells.add(labelMaximumFitnessOfImmortalCells)
  boxMaximumFitnessOfImmortalCells.add(Box.createGlue())

  val boxMinimumFitnessOfMortalCells = new Box(BoxLayout.X_AXIS)
  add(boxMinimumFitnessOfMortalCells)

  boxMinimumFitnessOfMortalCells.add(createLabel("Minimum fitness mortal cells: "))
  val labelMinimumFitnessOfMortalCells = new JLabel()
  boxMinimumFitnessOfMortalCells.add(labelMinimumFitnessOfMortalCells)
  boxMinimumFitnessOfMortalCells.add(Box.createGlue())

  val boxMaximumFitnessOfMortalCells = new Box(BoxLayout.X_AXIS)
  add(boxMaximumFitnessOfMortalCells)

  boxMaximumFitnessOfMortalCells.add(createLabel("Maximum fitness mortal cells: "))
  val labelMaximumFitnessOfMortalCells = new JLabel()
  boxMaximumFitnessOfMortalCells.add(labelMaximumFitnessOfMortalCells)
  boxMaximumFitnessOfMortalCells.add(Box.createGlue())

  def updated() {
    _model match {
      case Some(m) => {
        labelNumberOfImmortalCells.setText(m.getNumerOfImmortalCells.toString)
        labelNumberOfMortalCells.setText(m.getNumberOfMortalCells.toString)
        labelAverageFitnessOfImmortalCells.setText(m.getAverageFitnessOfImmortalCells.toString)
        labelAverageFitnessOfMortalCells.setText(m.getAverageFitnessOfMortalCells.toString)
        labelMinimumFitness.setText(m.getMinimumFitness.toString)
        labelMaximumFitness.setText(m.getMaximumFitness.toString)
        labelMinimumFitnessOfImmortalCells.setText(m.getMinimumFitnessOfImmortalCells.toString)
        labelMaximumFitnessOfImmortalCells.setText(m.getMaximumFitnessOfImmortalCells.toString)
        labelMinimumFitnessOfMortalCells.setText(m.getMinimumFitnessOfMortalCells.toString)
        labelMaximumFitnessOfMortalCells.setText(m.getMaximumFitnessOfMortalCells.toString)
      }
      case None =>
    }
  }

}
