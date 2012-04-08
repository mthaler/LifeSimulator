package lifesimulator

import util.Random

trait Cell {
  def fitness: Double
}

case class ImmortalCell(fitness: Double) extends Cell {
  def makeChild = {
    val r = Random.nextInt(3)
    val childFitness = if (r == 0) fitness - Config.MutationFactor else if (r == 1) fitness else fitness + Config.MutationFactor
    if (childFitness > 0) ImmortalCell(childFitness) else EmptyCell
  }
}

case class MortalCell(fitness: Double, age: Int) extends Cell {
  def makeChild = {
    val r = Random.nextInt(3)
    val childFitness = if (r == 0) fitness - Config.MutationFactor else if (r == 1) fitness else fitness + Config.MutationFactor
    if (childFitness > 0) MortalCell(childFitness, 0) else EmptyCell
  }
}

case object EmptyCell extends Cell {
  val fitness = 1.0
}
