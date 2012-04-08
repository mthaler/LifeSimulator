package lifesimulator

import util.Random
import javax.swing.Timer
import java.awt.event.{ActionEvent, ActionListener}

class Model extends Observable {

  import Config._

  ModelCache.model = this

  val cells = Array.ofDim[Cell](Size,Size)
  initCells()

  val timer = new Timer(1000, new ActionListener {
    def actionPerformed(e: ActionEvent) {
      updatedCells()
      notifyObservers()
    }
  })
  timer.start()

  def initCells() {
    for(x <- 0 until Size) {
      for( y <- 0 until Size) {
        cells(x)(y) = EmptyCell
      }
    }
    for(i <- 0 until InitialImmortalCells) {
      val x = Random.nextInt(Size)
      val y = Random.nextInt(Size)
      cells(x)(y) = ImmortalCell(InitalFitness)
    }
    for (m <- 0 until InitialMortalCells) {
      // todo: make sure we always have the same number of initial immortal and mortal cells
      val x = Random.nextInt(Size)
      val y = Random.nextInt(Size)
      cells(x)(y) = MortalCell(InitalFitness, 0)
    }
  }

  def updatedCells() {
    // increase the age of the mortal cells and let them die if they are older then the maximum age
    for (x <- 0 until Size) {
      for (y <- 0 until Size) {
        val cell = cells(x)(y)
        cell match {
          case m: MortalCell => {
            // increase the age by one year
            val newCell = m.copy(age = m.age + 1)
            cells(x)(y) = if (newCell.age <= MaxAge) newCell else EmptyCell
          }
          case _ =>
        }
      }
    }
    // decrease the fitness of all cells and remove the ones that have fitness smaller than zero
    for(x <- 0 until Size) {
      for( y <- 0 until Size) {
        cells(x)(y) match {
          case EmptyCell =>
          case i: ImmortalCell => {
            val newFitness = i.fitness - Config.FitnessDecrease
            cells(x)(y) = if (newFitness > 0) i.copy(fitness = newFitness) else EmptyCell
          }
          case m: MortalCell => {
            val newFitness = m.fitness - Config.FitnessDecrease
            cells(x)(y) = if (newFitness > 0) m.copy(fitness = newFitness) else EmptyCell
          }
        }
      }
    }

    // create children for each child
    for(x <- 0 until Size) {
      for( y <- 0 until Size) {
        val cell = cells(x)(y)
        cell match {
          case i: ImmortalCell => {
            val child = i.makeChild
            val (childX, childY) = getChildPosition(x, y)
            setChild(child, childX, childY)
          }
          case m: MortalCell => {
            val child = m.makeChild
            val (childX, childY) = getChildPosition(x, y)
            setChild(child, childX, childY)
          }
          case EmptyCell =>
        }
      }
    }
  }

  def getChildPosition(x: Int, y: Int): (Int, Int) = {
    val index = Random.nextInt(9)
    // place the new child child next to the parent child
    val birthDistance = BirthDistance.toInt
    val (newX, newY) = index match {
      case 0 => (x - birthDistance, y - birthDistance)
      case 1 => (x - birthDistance, y)
      case 2 => (x - birthDistance, y + birthDistance)
      case 3 => (x, y - birthDistance)
      case 4 => (x, y)
      case 5 => (x, y + birthDistance)
      case 6 => (x + birthDistance, y - birthDistance)
      case 7 => (x + birthDistance, y)
      case 8 => (x + birthDistance, y + birthDistance)
    }
    // periodic boundary conditions
    val resultX = if(newX < 0) newX + Size else if (newX >= Size) newX - Size else newX
    val resultY = if(newY < 0) newY + Size else if (newY >= Size) newY - Size else newY
    (resultX, resultY)
  }

  def setChild(child: Cell, x: Int, y: Int) {
    child match {
      case EmptyCell =>
      case _ => {
        // get the child at his position
        val oldCell = cells(x)(y)
        oldCell match {
          case EmptyCell => cells(x)(y) = child
          case c => {
            if (c.fitness + child.fitness > 0.0) {
              val oldCellFitnessNormalized = c.fitness / (c.fitness + child.fitness)
              val childCellFitnessNormalized = child.fitness / (c.fitness + child.fitness)
              val r = Random.nextDouble()
              cells(x)(y) = if (r <= oldCellFitnessNormalized) c else child
            } else {
              val r = Random.nextDouble()
              cells(x)(y) = if (r <= 0.5) c else child
            }
          }
        }
      }
    }
  }

  def apply(x: Int, y: Int): Cell = cells(x % Size)(y % Size)

  private def calculateNumberOfCells: (Int, Int, Int) = {
    var numberOfImmortalCells = 0
    var numberOfMortalCells = 0
    for (x <- 0 until Size) {
      for( y <- 0 until Size) {
        cells(x)(y) match {
          case i: ImmortalCell => numberOfImmortalCells += 1
          case m: MortalCell => numberOfMortalCells += 1
          case _ =>
        }
      }
    }
    (numberOfImmortalCells + numberOfMortalCells, numberOfImmortalCells, numberOfMortalCells)
  }

  private def calculateAverageFitnesses: (Double, Double, Double) = {
    val numberOfImmortalCells = getNumerOfImmortalCells
    val numberOfMortalCells = getNumberOfMortalCells
    var fitnessImmortalCells = 0.0
    var fitnessMortalCells = 0.0
    for (x <- 0 until Size) {
      for( y <- 0 until Size) {
        cells(x)(y) match {
          case i: ImmortalCell => fitnessImmortalCells += i.fitness
          case m: MortalCell => fitnessMortalCells += m.fitness
          case _ =>
        }
      }
    }
    val averageFitness = if (numberOfImmortalCells + numberOfMortalCells > 0) (fitnessImmortalCells + fitnessMortalCells) / (numberOfImmortalCells + numberOfMortalCells) else 0
    val averageFitnessImmortalCells = if (numberOfImmortalCells > 0) fitnessImmortalCells / numberOfImmortalCells else 0
    val averageFitnessMortalCells = if (numberOfMortalCells > 0) fitnessMortalCells / numberOfMortalCells else 0
    (averageFitness, averageFitnessImmortalCells, averageFitnessMortalCells)
  }

  def getNumerOfImmortalCells: Int = {
    ModelCache.numberOfImmortalCells.getOrElse({
      val (numberOfNonEmptyCells, numberOfImmortalCells, numberOfMortalCells) = calculateNumberOfCells
      ModelCache.setNumberOfCells(numberOfNonEmptyCells, numberOfImmortalCells, numberOfMortalCells)
      numberOfImmortalCells
    })
  }

  def getNumberOfMortalCells: Int = {
    ModelCache.numberOfMortalCells.getOrElse({
      val (numberOfNonEmptyCells, numberOfImmortalCells, numberOfMortalCells) = calculateNumberOfCells
      ModelCache.setNumberOfCells(numberOfNonEmptyCells, numberOfImmortalCells, numberOfMortalCells)
      numberOfMortalCells
    })
  }

  def getNumberOfNonEmptyCells: Int = {
    ModelCache.numberOfNonEmptyCells.getOrElse({
      val (numberOfNonEmptyCells, numberOfImmortalCells, numberOfMortalCells) = calculateNumberOfCells
      ModelCache.setNumberOfCells(numberOfNonEmptyCells, numberOfImmortalCells, numberOfMortalCells)
      numberOfNonEmptyCells
    })
  }

  def getAverageFitness: Double = {
    ModelCache.averageFitness.getOrElse({
      val (averageFitness, averageFitnessOfImmortalCells, averageFitnessOfMortalCells) = calculateAverageFitnesses
      ModelCache.setAverageFitness(averageFitness, averageFitnessOfImmortalCells, averageFitnessOfMortalCells)
      averageFitness
    })
  }

  def getAverageFitnessOfImmortalCells: Double = {
    ModelCache.averageFitnessOfImmortalCells.getOrElse({
      val (averageFitness, averageFitnessOfImmortalCells, averageFitnessOfMortalCells) = calculateAverageFitnesses
      ModelCache.setAverageFitness(averageFitness, averageFitnessOfImmortalCells, averageFitnessOfMortalCells)
      averageFitnessOfImmortalCells
    })
  }

  def getAverageFitnessOfMortalCells: Double = {
    ModelCache.averageFitnessOfMortalCells.getOrElse({
      val (averageFitness, averageFitnessOfImmortalCells, averageFitnessOfMortalCells) = calculateAverageFitnesses
      ModelCache.setAverageFitness(averageFitness, averageFitnessOfImmortalCells, averageFitnessOfMortalCells)
      averageFitnessOfMortalCells
    })
  }
}
