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


  def updated() {
    _model match {
      case Some(m) => {
        labelNumberOfImmortalCells.setText(m.getNumerOfImmortalCells.toString)
        labelNumberOfMortalCells.setText(m.getNumberOfMortalCells.toString)
        labelAverageFitnessOfImmortalCells.setText(m.getAverageFitnessOfImmortalCells.toString)
        labelAverageFitnessOfMortalCells.setText(m.getAverageFitnessOfMortalCells.toString)
      }
      case None =>
    }
  }

}
