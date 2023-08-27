class Task {
  //put your task here
}
object Applicant:
def unapply(applicant: Applicant): (String, Int, VehicleType) =
  (applicant.name, applicant.age, applicant.vehicleType)

val applicants: Seq[Applicant] = ???
val names = applicants.collect {
  case Applicant(name, age, VehicleType.Car) if age >= 18 => name
  case Applicant(name, age, VehicleType.SmallMotorcycle) if age >= 16 => name
  case Applicant(name, age, VehicleType.Moped) if age >= 15 => name
}

object Applicant:
def unapply(applicant: Applicant): Option[String] = applicant.vehicleType match
  case VehicleType.Car if age >= 18 => Some(applicant.name)
  case VehicleType.SmallMotorcycle if age >= 16 => Some(applicant.name)
  case VehicleType.Moped if age >= 15 => Some(applicant.name)
  case _ => None

val applicants: Seq[Applicant] = ???
val names = applicants.collect {
  case Applicant(name) => name
}