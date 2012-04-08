package lifesimulator

object ModelCache extends Observer {
  var numberOfNonEmptyCells: Option[Int] = None
  var numberOfImmortalCells: Option[Int] = None
  var numberOfMortalCells: Option[Int] = None
  var averageFitness: Option[Double] = None
  var averageFitnessOfImmortalCells: Option[Double] = None
  var averageFitnessOfMortalCells: Option[Double] = None

  private var _model: Option[Model] = None

  def model = _model

  def model_=(value: Model) {
    if (_model.isDefined) _model.get.removeObserver(this)
    value.addObserver(this)
    _model = Some(value)
  }

  private def invalidateCache() {
    numberOfNonEmptyCells = None
    numberOfImmortalCells = None
    numberOfMortalCells = None
    averageFitness = None
    averageFitnessOfImmortalCells = None
    averageFitnessOfMortalCells = None
  }

  def setNumberOfCells(numberOfNonEmptyCells: Int, numberOfImmortalCells: Int, numberOfMortalCells: Int) {
    this.numberOfNonEmptyCells = Some(numberOfNonEmptyCells)
    this.numberOfImmortalCells = Some(numberOfImmortalCells)
    this.numberOfMortalCells = Some(numberOfMortalCells)
  }

  def setAverageFitness(averageFitness: Double, averageFitnessOfImmortalCells: Double, averageFitnessOfMortalCells: Double) {
    this.averageFitness = Some(averageFitness)
    this.averageFitnessOfImmortalCells = Some(averageFitnessOfImmortalCells)
    this.averageFitnessOfMortalCells = Some(averageFitnessOfMortalCells)
  }

  def updated() {
    invalidateCache()
  }
}
