package lifesimulator

import collection.mutable.{WeakHashMap, ArrayBuffer}


trait Observable {
  val observers = new WeakHashMap[Observer, Any]
  def addObserver(observer: Observer) {
    observers.put(observer, null)
  }

  def removeObserver(observer: Observer) {
    observers.remove(observer)
  }

  def notifyObservers() {
    for (observer <- observers.keys) observer.updated
  }
}
