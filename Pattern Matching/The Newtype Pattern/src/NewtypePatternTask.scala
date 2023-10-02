object NewtypePatternTask:
  enum MeasurementSystem:
    case Metric
    case Imperial

  import MeasurementSystem.*

  class FitnessTracker(val measurementSystem: MeasurementSystem):
    // Always store the distance in meters
    private var distanceWalked: Meters = Meters(0)

    // Assumes the distance is in the preferred units, converts it when necessary
    def trackDistanceInPreferredUnits(distance: Double) =
      val distanceInMeters =
        measurementSystem match
          case Metric => Meters(distance)
          case Imperial => Feet.toMeters(Feet(distance))
      distanceWalked = distanceWalked + distanceInMeters

    // Assumes the distance is in Feet, converts to Meters
    def trackDistanceInFeet(distance: Feet) =
      distanceWalked = distanceWalked + Feet.toMeters(distance)

    // Assumes the distance is in Meters
    def trackDistanceInMeters(distance: Meters) =
      distanceWalked = distanceWalked + distance

    def show() =
      val distanceWalkedAsString =
        measurementSystem match
          case Metric => s"${distanceWalked}m"
          case Imperial => s"${Meters.toFeet(distanceWalked)}ft"
      s"Total distance walked: $distanceWalkedAsString"

  // Define an opaque type for meters
  opaque type Meters = Double

  object Meters:
    def apply(value: Double): Meters = value
    def toFeet(meters: Meters): Feet = Feet(meters / 0.3048)

  // Define an opaque type for feet
  opaque type Feet = Double

  object Feet:
    def apply(value: Double): Feet = value
    def toMeters(feet: Feet): Meters = Meters(feet * 0.3048)