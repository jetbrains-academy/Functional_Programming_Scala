import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.Assertion
import NewtypePatternTask._

class NewtypePatternTaskSpec extends AnyFunSuite {
  test("The tracked distance should display correctly") {
    val stream = new java.io.ByteArrayOutputStream()
    Console.withOut(stream) {
      val metricTracker = FitnessTracker(MeasurementSystem.Metric)
      val imperialTracker = FitnessTracker(MeasurementSystem.Imperial)

      metricTracker.trackDistanceInPreferredUnits(1000)
      metricTracker.trackDistanceInMeters(Meters(500))
      metricTracker.trackDistanceInFeet(Feet(1500))
      metricTracker.trackDistanceInPreferredUnits(500)

      imperialTracker.trackDistanceInPreferredUnits(3000)
      imperialTracker.trackDistanceInPreferredUnits(1000)

      println(metricTracker.show())
      println(imperialTracker.show())

      assert(stream.toString() == "Total distance walked: 2457.2m\nTotal distance walked: 4000.0ft\n")
    }
  }
}











