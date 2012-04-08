package lifesimulator

import collection.mutable.ArrayBuffer

trait Observable {
  val observers = ArrayBuffer.empty[Observer]

  def addObserver(observer: Observer) {
    observers += observer
  }

  def removeObserver(observer: Observer) {
    observers -= observer
  }

  def notifyObservers() {
    for (observer <- observers) observer.updated
  }
}
