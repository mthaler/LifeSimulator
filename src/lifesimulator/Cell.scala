package lifesimulator

trait Cell {
  def fitness: Double
}

case class ImmortalCell(fitness: Double) extends Cell {
  def makeChild = {
    ImmortalCell(fitness)
  }
}

case class MortalCell(fitness: Double, age: Int) extends Cell {
  def makeChild = {
    MortalCell(fitness, 0)
  }
}

case object EmptyCell extends Cell {
  val fitness = 1.0
}
